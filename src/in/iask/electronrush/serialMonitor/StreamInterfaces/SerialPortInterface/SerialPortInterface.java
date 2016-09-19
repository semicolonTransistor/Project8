package in.iask.electronrush.serialMonitor.StreamInterfaces.SerialPortInterface;

import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import in.iask.electronrush.serialMonitor.StreamInterface;

public class SerialPortInterface implements StreamInterface, ActionListener {

	protected class UpdatePort extends TimerTask{

		public void run() {
			synchronized(this){
				if(isConnected() == false){
					avaliablePorts = SerialPort.getCommPorts();
					String[] portLables = new String[avaliablePorts.length];
					for(int index = 0; index < avaliablePorts.length; index++){
						portLables[index] = avaliablePorts[index].getDescriptivePortName();
					}
					controlPanel.portSelector.setModel(new DefaultComboBoxModel<String>(portLables));
				}else{
					byte[] data = "TEST\n".getBytes();
					port.writeBytes(data, data.length);
				}
			}
		}
		
	}
	
	protected class DataListener implements SerialPortDataListener{

		@Override
		public int getListeningEvents() {
			return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
		}

		@Override
		public void serialEvent(SerialPortEvent event) {
			byte[] data = new byte[event.getSerialPort().bytesAvailable()];
			event.getSerialPort().readBytes(data,data.length);
			System.out.print(new String(data));
		}
		
	}
	
	protected ControlPanel controlPanel;
	protected SerialPort port;
	protected SerialPort[] avaliablePorts;
	protected DataListener dataListener = new DataListener();

	
	
	
	Timer timer = new Timer();
	@Override
	public JPanel getControlPanel() {
		return controlPanel;
	}

	@Override
	public boolean isConnected() {
		if(port == null){
			return false;
		}else{
			return port.isOpen();
		}
	}

	@Override
	public int bytesAvaliable() {
		if(isConnected()){
			return port.bytesAvailable();
		}else{
			return 0;
		}
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public SerialPortInterface() {
		controlPanel = new ControlPanel();
		timer.schedule(new UpdatePort(), 0, 5000);
		
		controlPanel.connectButton.setActionCommand("connect");
		controlPanel.connectButton.addActionListener(this);
		
		controlPanel.disconnectButton.setActionCommand("disconnect");
		controlPanel.disconnectButton.addActionListener(this);
	}

	@Override
	public void disconnect() {
		if(isConnected()){
			port.removeDataListener();
			port.closePort();
			controlPanel.portSelector.setEnabled(true);
			controlPanel.baudrateSelector.setEnabled(true);
			controlPanel.stopBitsSelector.setEnabled(true);
			controlPanel.connectButton.setEnabled(true);
			controlPanel.disconnectButton.setEnabled(false);
			System.out.println(port.isOpen());
		}
		
	}

	@Override
	public void connect() {
		if(!isConnected()){
			System.out.println("connecting");
			synchronized(this){
				port = avaliablePorts[controlPanel.portSelector.getSelectedIndex()];
				controlPanel.portSelector.setEnabled(false);
				port.setBaudRate((Integer) controlPanel.baudrateSelector.getSelectedItem());
				controlPanel.baudrateSelector.setEnabled(false);
				String selectedStopBit = (String) controlPanel.stopBitsSelector.getSelectedItem();
				if(selectedStopBit.equals("1")){
					port.setNumDataBits(SerialPort.ONE_STOP_BIT);
				}else if(selectedStopBit.equals("1.5")){
					port.setNumStopBits(SerialPort.ONE_POINT_FIVE_STOP_BITS);
				}else{
					port.setNumStopBits(SerialPort.TWO_STOP_BITS);
				}
				controlPanel.stopBitsSelector.setEnabled(false);
				port.setNumDataBits((Integer)controlPanel.dataBitsSelector.getSelectedItem());
				controlPanel.dataBitsSelector.setEnabled(false);
				controlPanel.connectButton.setEnabled(false);
				controlPanel.disconnectButton.setEnabled(true);
				port.openPort();
				port.addDataListener(dataListener);
				System.out.println(port.isOpen());
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("connect".equals(e.getActionCommand())){
			connect();
		}else if("disconnect".equals(e.getActionCommand())){
			disconnect();
		}
		
	}
}

	

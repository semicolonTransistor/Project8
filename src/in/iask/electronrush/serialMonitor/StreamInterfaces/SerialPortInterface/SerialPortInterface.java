package in.iask.electronrush.serialMonitor.StreamInterfaces.SerialPortInterface;

import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

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
			port.readBytes(data, data.length);
			for(int index = 0; index < data.length; index++){
				outputQueue.offer(new Byte(data[index]));
			}
		}
		
	}
	
	protected ControlPanel controlPanel;
	protected SerialPort port;
	protected SerialPort[] avaliablePorts;
	protected DataListener dataListener = new DataListener();
	protected Queue<Byte> outputQueue = new ConcurrentLinkedQueue<Byte>();

	
	
	
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
	public Byte getNextByte() {
		return outputQueue.poll();
	}

	@Override
	public void writeNextByte(byte nextByte){
		// TODO Auto-generated method stub
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
			if(!port.isOpen()){
				controlPanel.portSelector.setEnabled(true);
				controlPanel.baudrateSelector.setEnabled(true);
				controlPanel.stopBitsSelector.setEnabled(true);
				controlPanel.connectButton.setEnabled(true);
				controlPanel.disconnectButton.setEnabled(false);
			}
			System.out.println(port.isOpen());
		}
		
	}

	@Override
	public void connect() {
		if(!isConnected()){
			System.out.println("connecting");
			synchronized(this){
				port = avaliablePorts[controlPanel.portSelector.getSelectedIndex()];
				port.setBaudRate((Integer) controlPanel.baudrateSelector.getSelectedItem());
				String selectedStopBit = (String) controlPanel.stopBitsSelector.getSelectedItem();
				if(selectedStopBit.equals("1")){
					port.setNumDataBits(SerialPort.ONE_STOP_BIT);
				}else if(selectedStopBit.equals("1.5")){
					port.setNumStopBits(SerialPort.ONE_POINT_FIVE_STOP_BITS);
				}else{
					port.setNumStopBits(SerialPort.TWO_STOP_BITS);
				}
				port.setNumDataBits((Integer)controlPanel.dataBitsSelector.getSelectedItem());
				port.openPort();
				if(port.isOpen()){
					controlPanel.baudrateSelector.setEnabled(false);
					controlPanel.portSelector.setEnabled(false);
					controlPanel.stopBitsSelector.setEnabled(false);
					controlPanel.dataBitsSelector.setEnabled(false);
					controlPanel.connectButton.setEnabled(false);
					controlPanel.disconnectButton.setEnabled(true);
					port.addDataListener(dataListener);
				}
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

	

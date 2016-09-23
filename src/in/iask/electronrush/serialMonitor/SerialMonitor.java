package in.iask.electronrush.serialMonitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import in.iask.electronrush.serialMonitor.StreamInterfaces.SerialPortInterface.SerialPortInterface;
import in.iask.electronrush.serialMonitor.streamDisplays.basicAscii.BasicAsciiDisplay;

import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;;

public class SerialMonitor {
	
	public class IncommingMessageHandler extends TimerTask{
		
		@Override
		public void run(){
			Byte thisByte;
			while((thisByte = streamInterface.getNextByte())!=null){
				for(StreamDisplayWrapper display:displays){
					display.streamDisplay.reviceData(thisByte);
				}
			}
			for(StreamDisplayWrapper display:displays){
				display.streamDisplay.updateDisplay();
			}
		}
	}

	private JFrame frame;
	
	protected ConcurrentLinkedQueue<Byte> displaydata = new ConcurrentLinkedQueue<Byte>();
	protected Timer screenUpdateTimer = new Timer();
	
	protected Collection<StreamDisplayWrapper> displays= new HashSet<StreamDisplayWrapper>();

	/**
	 * Launch the application.
	 */
	
	StreamInterface streamInterface;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialMonitor window = new SerialMonitor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SerialMonitor() {
		streamInterface = new SerialPortInterface();
		displays.add(new StreamDisplayWrapper(new BasicAsciiDisplay(),"BasicAscii",null,"This provids a basic display based on ascii"));
		initialize();
		screenUpdateTimer.schedule(new IncommingMessageHandler(), 0, 20);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 870);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel controls = new JPanel();
		frame.getContentPane().add(controls, BorderLayout.WEST);
		controls.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel streamControlPanel = streamInterface.getControlPanel();
		controls.add(streamControlPanel);
		
		JTabbedPane dataArea = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(dataArea, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		for(StreamDisplayWrapper display:displays){
			dataArea.addTab(display.name, display.icon, display.streamDisplay.getDisplayPlanel(), display.tip);
		}
	}
	
}

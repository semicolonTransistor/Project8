package in.iask.electronrush.serialMonitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import in.iask.electronrush.serialMonitor.StreamInterfaces.SerialPortInterface.SerialPortInterface;

import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;;

public class SerialMonitor {
	
	public class IncommingMessageHandler extends Thread{
		protected AtomicBoolean run = new AtomicBoolean(true);
		
		@Override
		public void run(){
			while(run.get()){
				Byte thisByte = streamInterface.getNextByte();
				if(thisByte != null){
					displaydata.offer(thisByte);
					Byte[] dataObj = displaydata.toArray(new Byte[1]);
					byte[] data = new byte[dataObj.length];
					for(int index = 0; index < dataObj.length; index++){
						data[index] = dataObj[index].byteValue();
					}
					editor.setText(new String(data));
				}
			}
		}
		
		public void end(){
			run.set(false);
		}
	}

	private JFrame frame;
	protected JEditorPane editor;
	
	protected ConcurrentLinkedQueue<Byte> displaydata = new ConcurrentLinkedQueue<Byte>();

	/**
	 * Launch the application.
	 */
	
	StreamInterface streamInterface;
	private JTextField textField;
	
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
		initialize();
		new IncommingMessageHandler().start();
		
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
		
		JPanel basicAscii = new JPanel();
		dataArea.addTab("Basic ASCII", null, basicAscii, null);
		GridBagLayout gbl_basicAscii = new GridBagLayout();
		gbl_basicAscii.columnWidths = new int[]{0, 0, 0};
		gbl_basicAscii.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_basicAscii.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_basicAscii.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		basicAscii.setLayout(gbl_basicAscii);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		basicAscii.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		basicAscii.add(btnNewButton, gbc_btnNewButton);
		
		editor = new JEditorPane();
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.gridwidth = 2;
		gbc_editorPane.insets = new Insets(0, 0, 5, 0);
		gbc_editorPane.fill = GridBagConstraints.BOTH;
		gbc_editorPane.gridx = 0;
		gbc_editorPane.gridy = 1;
		basicAscii.add(editor, gbc_editorPane);
		
		JButton btnClear = new JButton("Clear");
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 1;
		gbc_btnClear.gridy = 2;
		basicAscii.add(btnClear, gbc_btnClear);
	}
	
}

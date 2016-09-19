package in.iask.electronrush.serialMonitor.StreamInterfaces.SerialPortInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class ControlPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 837238235352206141L;


	public static final Integer[] baudRates = {110, 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 256000};
	public static final Integer[] dataBitsOptions = {5,7,8}; 
	
	
	public final JComboBox<String> portSelector;
	public final JComboBox<Integer> baudrateSelector;
	public final JComboBox<String> paritySelector;
	public final JComboBox<String> stopBitsSelector;
	public final JComboBox<Integer> dataBitsSelector;
	
	public final JButton connectButton;
	public final JButton disconnectButton;

	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[] {40, 40, 40, 40, 40, 0, 40};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Ports");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		portSelector = new JComboBox<String>();
		GridBagConstraints gbc_PortSelector = new GridBagConstraints();
		gbc_PortSelector.insets = new Insets(0, 0, 5, 0);
		gbc_PortSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_PortSelector.gridx = 1;
		gbc_PortSelector.gridy = 0;
		add(portSelector, gbc_PortSelector);
		
		JLabel lblBaudrate = new JLabel("Baudrate");
		GridBagConstraints gbc_lblBaudrate = new GridBagConstraints();
		gbc_lblBaudrate.anchor = GridBagConstraints.EAST;
		gbc_lblBaudrate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBaudrate.gridx = 0;
		gbc_lblBaudrate.gridy = 1;
		add(lblBaudrate, gbc_lblBaudrate);
		
		baudrateSelector = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(baudRates));
		baudrateSelector.setEditable(true);
		GridBagConstraints gbc_baudrateSelector = new GridBagConstraints();
		gbc_baudrateSelector.insets = new Insets(0, 0, 5, 0);
		gbc_baudrateSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_baudrateSelector.gridx = 1;
		gbc_baudrateSelector.gridy = 1;
		add(baudrateSelector, gbc_baudrateSelector);
		
		JLabel lblParity = new JLabel("Parity");
		GridBagConstraints gbc_lblParity = new GridBagConstraints();
		gbc_lblParity.anchor = GridBagConstraints.EAST;
		gbc_lblParity.insets = new Insets(0, 0, 5, 5);
		gbc_lblParity.gridx = 0;
		gbc_lblParity.gridy = 2;
		add(lblParity, gbc_lblParity);
		
		paritySelector = new JComboBox();
		paritySelector.setModel(new DefaultComboBoxModel(new String[] {"odd", "even"}));
		GridBagConstraints gbc_paritySelector = new GridBagConstraints();
		gbc_paritySelector.insets = new Insets(0, 0, 5, 0);
		gbc_paritySelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_paritySelector.gridx = 1;
		gbc_paritySelector.gridy = 2;
		add(paritySelector, gbc_paritySelector);
		
		JLabel lblStopBits = new JLabel("Stop Bits");
		GridBagConstraints gbc_lblStopBits = new GridBagConstraints();
		gbc_lblStopBits.anchor = GridBagConstraints.EAST;
		gbc_lblStopBits.insets = new Insets(0, 0, 5, 5);
		gbc_lblStopBits.gridx = 0;
		gbc_lblStopBits.gridy = 3;
		add(lblStopBits, gbc_lblStopBits);
		
		stopBitsSelector = new JComboBox();
		stopBitsSelector.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
		GridBagConstraints gbc_stopBitsSelector = new GridBagConstraints();
		gbc_stopBitsSelector.insets = new Insets(0, 0, 5, 0);
		gbc_stopBitsSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_stopBitsSelector.gridx = 1;
		gbc_stopBitsSelector.gridy = 3;
		add(stopBitsSelector, gbc_stopBitsSelector);
		
		JLabel lblDatabits = new JLabel("Data Bits");
		GridBagConstraints gbc_lblDatabits = new GridBagConstraints();
		gbc_lblDatabits.anchor = GridBagConstraints.EAST;
		gbc_lblDatabits.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabits.gridx = 0;
		gbc_lblDatabits.gridy = 4;
		add(lblDatabits, gbc_lblDatabits);
		
		dataBitsSelector = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(dataBitsOptions));
		dataBitsSelector.setEditable(true);
		GridBagConstraints gbc_dataBitsSelector = new GridBagConstraints();
		gbc_dataBitsSelector.insets = new Insets(0, 0, 5, 0);
		gbc_dataBitsSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataBitsSelector.gridx = 1;
		gbc_dataBitsSelector.gridy = 4;
		add(dataBitsSelector, gbc_dataBitsSelector);
		
		connectButton = new JButton("Connect");
		GridBagConstraints gbc_btnConect = new GridBagConstraints();
		gbc_btnConect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConect.gridx = 0;
		gbc_btnConect.gridy = 5;
		add(connectButton, gbc_btnConect);
		
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.gridx = 1;
		gbc_btnDisconnect.gridy = 5;
		add(disconnectButton, gbc_btnDisconnect);
	}

}

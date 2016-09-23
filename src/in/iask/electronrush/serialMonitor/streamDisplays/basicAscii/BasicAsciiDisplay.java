package in.iask.electronrush.serialMonitor.streamDisplays.basicAscii;

import javax.swing.JPanel;

import in.iask.electronrush.serialMonitor.StreamDisplay;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BasicAsciiDisplay extends JPanel implements StreamDisplay{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2083140876514216030L;

	protected JTextPane textDisplay;
	protected Queue<Byte> displayData = new ConcurrentLinkedQueue<Byte>();
	
	/**
	 * Create the panel.
	 */
	public BasicAsciiDisplay() {
		setLayout(new BorderLayout(0, 0));
		
		textDisplay = new JTextPane();
		add(textDisplay);

	}

	@Override
	public void reviceData(Byte data) {
		displayData.offer(data);
	}

	@Override
	public void updateDisplay() {
		Byte[] dataObj = displayData.toArray(new Byte[0]);
		byte[] data = new byte[dataObj.length];
		for(int index = 0; index < dataObj.length; index ++){
			data[index] = dataObj[index].byteValue();
		}
		textDisplay.setText(new String(data));
		
	}

	@Override
	public JPanel getDisplayPlanel() {
		return this;
	}

}

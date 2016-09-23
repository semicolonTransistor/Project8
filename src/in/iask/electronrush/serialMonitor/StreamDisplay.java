package in.iask.electronrush.serialMonitor;

import javax.swing.JPanel;

public interface StreamDisplay {
	public void reviceData(Byte data);
	
	public void updateDisplay();
	
	public JPanel getDisplayPlanel();
}

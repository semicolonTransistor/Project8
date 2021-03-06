package in.iask.electronrush.serialMonitor;

import javax.swing.JPanel;

public interface StreamInterface {
	/**
	 * This JPanel will be displayed on the top left of the application.
	 * Please implement any configurations and controls needed for the interface here.
	 * @return
	 */
	public JPanel getControlPanel();
	
	public boolean isConnected();
	
	public int bytesAvaliable();
	
	public Byte getNextByte();
	
	public void writeNextByte(byte nextByte);
	
	public void disconnect();
	
	public void connect();
}

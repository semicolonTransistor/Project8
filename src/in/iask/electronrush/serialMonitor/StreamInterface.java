package in.iask.electronrush.serialMonitor;

import java.io.InputStream;
import java.io.OutputStream;

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
	
	public InputStream getInputStream();
	
	public OutputStream getOutputStream();
	
	public void disconnect();
	
	public void connect();
}

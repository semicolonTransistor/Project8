package in.iask.electronrush.serialMonitor;

import javax.swing.Icon;

public class StreamDisplayWrapper {
	StreamDisplay streamDisplay = null;
	String name = null;
	Icon icon = null;
	String tip = null;
	
	StreamDisplayWrapper(StreamDisplay streamDisplay,String name,Icon icon, String tip ){
		this.streamDisplay = streamDisplay;
		this.name = name;
		this.icon = icon;
		this.tip = tip;
	}
}

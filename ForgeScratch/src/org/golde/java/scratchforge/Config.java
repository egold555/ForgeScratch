package org.golde.java.scratchforge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.golde.java.scratchforge.helpers.PLog;

public class Config {

	private Properties prop = new Properties();
	private OutputStream output = null;
	private InputStream input = null;
	private final String CONFIG_NAME = "config.properties";
	
	public Config() {
		try {
			//Make config file if it does not exist already
			new File("config.properties").createNewFile();
		}
		catch(Exception e) {
			PLog.error(e, "Could not create Config!");
		}
	}
	
	public enum ConfigProperty {
		MCRAM, 
		CLIENT_MULTIPLAYER_ENABLED, 
		CLIENT_MULTIPLAYER_LIMITED
	}
	
	public void set(ConfigProperty setting, String to) {
		try {
			output = new FileOutputStream(CONFIG_NAME);
			prop.setProperty(setting.name(), to);
			prop.store(output, null);
			output.close();
		}
		catch(Exception e) {
			PLog.errorPopup(e, "Failed to set " + setting.name() + " to " + to + "!");
		}
	}
	
	public String get(ConfigProperty setting) {
		try {
			input = new FileInputStream(CONFIG_NAME);
			prop.load(input);
			String result = prop.getProperty(setting.name());
			input.close();
			return result;
		}
		catch(Exception e) {
			PLog.error(e, "Failed to read setting " + setting.name() + "!");
			return null;
		}
	}
	
}

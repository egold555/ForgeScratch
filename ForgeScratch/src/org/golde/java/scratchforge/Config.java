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
			if(!new File(CONFIG_NAME).exists()) {
				//Make config file if it does not exist already
				new File(CONFIG_NAME).createNewFile();
				setInt(ConfigProperty.MCRAM, 8);
				setBoolean(ConfigProperty.CLIENT_MULTIPLAYER_ENABLED, true);
				setBoolean(ConfigProperty.CLIENT_MULTIPLAYER_LIMITED, false);
				//setBoolean(ConfigProperty.GRADLEW_ALWAYS_OFFLINE, false);
			}
		}
		catch(Exception e) {
			PLog.error(e, "Could not create Config!");
		}
	}

	public enum ConfigProperty {
		MCRAM, 
		CLIENT_MULTIPLAYER_ENABLED, 
		CLIENT_MULTIPLAYER_LIMITED,
		//GRADLEW_ALWAYS_OFFLINE,
	}
	
	public void setBoolean(ConfigProperty setting, boolean to) {
		setString(setting, Boolean.toString(to));
	}
	
	public void setInt(ConfigProperty setting, int to) {
		setString(setting, ""+to);
	}

	public void setString(ConfigProperty setting, String to) {
		try {
			output = new FileOutputStream(CONFIG_NAME);
			prop.setProperty(setting.name(), to);
			prop.store(output, null);
			output.close();
		}
		catch(Exception e) {
			PLog.error(e, "Failed to set " + setting.name() + " to " + to + "!");
		}
	}

	public boolean getBoolean(ConfigProperty setting) {
		return Boolean.valueOf(getString(setting));
	}
	
	public int getInt(ConfigProperty setting) {
		return Integer.parseInt(getString(setting));
	}
	
	public String getString(ConfigProperty setting) {
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

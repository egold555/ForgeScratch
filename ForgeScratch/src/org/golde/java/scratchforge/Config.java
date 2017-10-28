package org.golde.java.scratchforge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import org.golde.java.scratchforge.Config.ConfigKeys.ConfigKey;
import org.golde.java.scratchforge.helpers.PLog;

public class Config {

	private static Properties prop = new Properties();
	private static final String CONFIG_NAME = "ScratchForge.properties";

	public static final class ConfigKeys {
		
		static class ConfigKey {
			public final String key;
			public final Object defaultValue;
			public ConfigKey(String key, Object defaultValue) {
				this.key = key;
				this.defaultValue = defaultValue;
			}
		}
		
		public static final ConfigKey MINECRAFT_RAM = new ConfigKey("minecraft.ram", 8);
		public static final ConfigKey MINECRAFT_MP_ENABLED = new ConfigKey("minecraft.multiplayer.enabled", true);
		public static final ConfigKey MINECRAFT_MP_RESTRICTED = new ConfigKey("minecraft.multiplayer.restricted", false);
		public static final ConfigKey TUTORIAL_ENABLED = new ConfigKey("scratchforge.tutorial.enabled", false);
		public static final ConfigKey TUTORIAL_PLACE = new ConfigKey("scratchforge.tutorial.place", 1);
	}

	public static boolean getBoolean(ConfigKey key) {
		load();
		String got = prop.getProperty(key.key);
		if(got == null) {
			return (boolean)key.defaultValue;
		}
		return Boolean.parseBoolean(got);
	}

	public static int getInt(ConfigKey key) {
		load();
		String got = prop.getProperty(key.key);
		if(got == null) {
			return (int)key.defaultValue;
		}
		return Integer.parseInt(got);
	}

	public static String getString(ConfigKey key) {
		load();
		String got = prop.getProperty(key.key);
		if(got == null) {
			return (String)key.defaultValue;
		}
		return got;
	}

	public static void setBoolean(ConfigKey key, boolean value) {
		load();
		prop.setProperty(key.key, "" + value);
		save();
	}
	
	public static void setInt(ConfigKey key, int value) {
		load();
		prop.setProperty(key.key, "" + value);
		save();
	}
	
	public static void setString(ConfigKey key, String value) {
		load();
		prop.setProperty(key.key, value);
		save();
	}

	private static void load() {
		try {
			FileInputStream in = new FileInputStream(CONFIG_NAME);
			prop.load(in);
			in.close();
		}
		catch(FileNotFoundException e) {
			prop.clear();
			return;
		}
		catch(Exception e) {
			PLog.error(e, "Failed to load config!");
		}
	}

	private static void save() {
		try {
			FileOutputStream out = new FileOutputStream(CONFIG_NAME);
			prop.store(out, "Only modify this if you know what your doing. Incorrect values WILL cause problems!");
			out.close();
		}
		catch(Exception e) {
			PLog.error(e, "Failed to save config!");
		}
	}

}

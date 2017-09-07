package org.golde.forge.scratchforge.mainmod;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.golde.forge.scratchforge.basemodfiles.PLog;

public class Config {

	private static boolean multiplayer = true;
	private static boolean multiplayerLimited = false;
	
	private static Properties prop = new Properties();
	private static InputStream input = null;
	private static File configDir;
	
	public static void load(File theconfigDir) {
		configDir = theconfigDir;
		try {
			multiplayer = Boolean.parseBoolean(get("CLIENT_MULTIPLAYER_ENABLED"));
			multiplayerLimited = Boolean.parseBoolean(get("CLIENT_MULTIPLAYER_LIMITED"));
		}
		catch(Exception e) {
			PLog.error(e, "Failed to load config!");
		}
	}
	
	private static String get(String setting) {
		try {
			input = new FileInputStream(new File(configDir, "config.properties"));
			prop.load(input);
			String result = prop.getProperty(setting);
			input.close();
			return result;
		}
		catch(Exception e) {
			PLog.error(e, "Failed to read setting " + setting + "!");
			return null;
		}
	}
	
	public static boolean isMultiplayerButtonEnabled() {
		return multiplayer;
	}
	
	public static boolean isMultiplayerLimitedToLan() {
		return multiplayerLimited;
	}
	
}

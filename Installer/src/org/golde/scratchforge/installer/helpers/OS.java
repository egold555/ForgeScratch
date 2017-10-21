package org.golde.scratchforge.installer.helpers;

public class OS {

	public enum Platform {
		Windows,
		Mac,
		Unix,
		Solaris,
		unsupported
	}

	private static Platform m_os = null;

	public static Platform getOS() {
		if(m_os == null) {
			String os = System.getProperty("os.name").toLowerCase();

			m_os = Platform.unsupported;
			if(os.indexOf("win")   >= 0) m_os = Platform.Windows;		// Windows
			if(os.indexOf("mac")   >= 0) m_os = Platform.Mac;			// Mac
			if(os.indexOf("nux")   >= 0) m_os = Platform.Unix;			// Linux
			if(os.indexOf("nix")   >= 0) m_os = Platform.Unix;			// Unix
			if(os.indexOf("sunos") >= 0) m_os = Platform.Solaris;		// Solaris
		}

		return m_os;
	}

	public static boolean isWindows() {
		return (getOS() == Platform.Windows);
	}
	
	public static boolean isMac() {
		return (getOS() == Platform.Mac);
	}
	
	public static boolean isUnix() {
		return (getOS() == Platform.Unix);
	}
	
	public static boolean isSolaris() {
		return (getOS() == Platform.Solaris);
	}

}

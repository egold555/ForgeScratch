package org.golde.java.scratchforge;

import org.aeonbits.owner.Config;

public interface SFConfig extends Config {

	@DefaultValue("8")
	int ram();
	
	@DefaultValue("true")
	boolean multiplayerEnabled();
	
	@DefaultValue("false")
	boolean multiplayerLimited();
	
	@DefaultValue("false")
	boolean tutorialEnabled();
	
	@DefaultValue("1")
	int tutorialPlace();
}

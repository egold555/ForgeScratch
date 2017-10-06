package org.golde.java.repetivemdgenerator;

public class Main {

	static final String elementType = "events";
	
	static final String[][] elements = { 
			new String[] {
					"Cancel Event", 
					"Tries to cancel the given event."
					},
			new String[] {
					"Item Pickup Event", 
					"Fired when any item is about to be picked up by a player."
					},
			new String[] {
					"Item Craft Event", 
					"Fired when a player is about to craft an item."
					},
			new String[] {
					"Item Smelt Event", 
					"Fired when a player is about to smelt an item."
					},
			new String[] {
					"Player Join Event", 
					"Fired when a new player is about to connect to the server."
					},
			new String[] {
					"Player Leave Event", 
					"Fired when a player is about to disconnect from the server."
					},
			new String[] {
					"Player Respawn Event", 
					"Fired when a player died and is now to be re-spawned."
					},
			new String[] {
					"Player Arrow Shoot Event", 
					"Fired when the player is about to shoot an arrow."
					},
			new String[] {
					"Player Attack Entity Event", 
					"Fired when the player is about to attack an Entity."
					},
			new String[] {
					"Player Bonemeal Event", 
					"Fired when the player attempts to use bonemeal on a block"
					},
			new String[] {
					"Player Fill Bucket Event", 
					"Fired when the player is about to use an empty bucket"
					},
			new String[] {
					"Player Item Break Event", 
					"Fired when an item is about to be broken by the player, e.g a sword."
					},
			new String[] {
					"Player Open Container Event", 
					"Fired when the player is about to interact with a container, e.g a chest."
					},
			new String[] {
					"Player Pickup XP Event", 
					"Fired when a player collides with an XPOrb on the ground."
					},
			new String[] {
					"Player Sleep Event", 
					"Fired when a player attempts to sleep in a bed"
					},
			new String[] {
					"Player Hoe Dirt Event", 
					"Fired when a player attempts to use a hoe on a block."
					},
			new String[] {
					"Sapling Grow Event", 
					"Fired when a sapling grows a tree"
					},
			new String[] {
					"Note Block Play Event", 
					"Fired when a Noteblock plays its note"
					},
			new String[] {
					"Note Block Change Event", 
					"Fired when a Noteblock is changed, e.g the pitch altered"
					}
	};
	
	public static void main(String[] args) {
		for(int i = 0; i < elements.length; i++) {
			String[] element = elements[i];
			p("#### " + element[0]);
			p("![](" + elementType + "-" + element[0].toLowerCase().replace(" ", "").replace("event", "") + ".png)<br>");
			p(element[1]);
			p("");
			p("<br>");
			p("");
		}
	}
	
	static void p(String msg) {
		System.out.println(msg);
	}

}

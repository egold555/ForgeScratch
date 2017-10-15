package org.golde.java.schematic2xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.golde.java.schematic2xml.converter.CodeWriter;
import org.golde.java.schematic2xml.converter.Converter;

/*
 * Based on my Gui Schematic 2 java, but optmised for ScratchForge
 */
public class Main {

	public static Map<Integer, String> map = new HashMap<Integer, String>();
	
	public static void main(String[] args) {
		if(args.length < 2) {
			PLog.error("Schematic2XML [schematic] [ignore air] <java optput>");
			return;
		}
		
		putMap();
		
		boolean ignoreAir = Boolean.valueOf(args[1]);
		File output;
		File input = new File(args[0]);
		
		if(args.length != 3) {
			output = new File("output.xml");
		}else {
			output = new File(args[2]);
		}
		
		if(!input.exists() || input.isDirectory() || getFileExtension(input).equalsIgnoreCase(".schematic")) {
			PLog.error("Not a valid input file!");
			return;
		}
		
		Converter.loadSchematic(input);
		CodeWriter.writeToFile(ignoreAir, output);
	}
	
	private static String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        PLog.error(e, "Failed to get file extention!");
	        return "";
	    }
	}
	
	private static void putMap() {
		map.put(150, "powered_comparator");
		map.put(118, "cauldron");
		map.put(148, "heavy_weighted_pressure_plate");
		map.put(81, "cactus");
		map.put(21, "lapis_ore");
		map.put(152, "redstone_block");
		map.put(104, "pumpkin_stem");
		map.put(128, "sandstone_stairs");
		map.put(133, "emerald_block");
		map.put(135, "birch_stairs");
		map.put(103, "melon_block");
		map.put(42, "iron_block");
		map.put(132, "tripwire");
		map.put(163, "acacia_stairs");
		map.put(6, "sapling");
		map.put(1, "stone");
		map.put(10, "flowing_lava");
		map.put(84, "jukebox");
		map.put(108, "brick_stairs");
		map.put(151, "daylight_detector");
		map.put(92, "cake");
		map.put(33, "piston");
		map.put(78, "snow_layer");
		map.put(40, "red_mushroom");
		map.put(115, "nether_wart");
		map.put(8, "flowing_water");
		map.put(54, "chest");
		map.put(35, "wool");
		map.put(16, "coal_ore");
		map.put(29, "sticky_piston");
		map.put(7, "bedrock");
		map.put(13, "gravel");
		map.put(58, "crafting_table");
		map.put(59, "wheat");
		map.put(72, "wooden_pressure_plate");
		map.put(87, "netherrack");
		map.put(162, "log2");
		map.put(50, "torch");
		map.put(61, "furnace");
		map.put(22, "lapis_block");
		map.put(73, "redstone_ore");
		map.put(134, "spruce_stairs");
		map.put(67, "stone_stairs");
		map.put(69, "lever");
		map.put(79, "ice");
		map.put(83, "reeds");
		map.put(120, "end_portal_frame");
		map.put(175, "double_plant");
		map.put(141, "carrots");
		map.put(20, "glass");
		map.put(25, "noteblock");
		map.put(85, "fence");
		map.put(45, "brick_block");
		map.put(31, "tallgrass");
		map.put(174, "packed_ice");
		map.put(146, "trapped_chest");
		map.put(100, "red_mushroom_block");
		map.put(112, "nether_brick");
		map.put(116, "enchanting_table");
		map.put(98, "stonebrick");
		map.put(140, "flower_pot");
		map.put(99, "brown_mushroom_block");
		map.put(138, "beacon");
		map.put(34, "piston_head");
		map.put(38, "red_flower");
		map.put(106, "vine");
		map.put(158, "dropper");
		map.put(102, "glass_pane");
		map.put(17, "log");
		map.put(51, "fire");
		map.put(56, "diamond_ore");
		map.put(32, "deadbush");
		map.put(88, "soul_sand");
		map.put(75, "unlit_redstone_torch");
		map.put(173, "coal_block");
		map.put(47, "bookshelf");
		map.put(77, "stone_button");
		map.put(41, "gold_block");
		map.put(123, "redstone_lamp");
		map.put(12, "sand");
		map.put(155, "quartz_block");
		map.put(71, "iron_door");
		map.put(80, "snow");
		map.put(119, "end_portal");
		map.put(153, "quartz_ore");
		map.put(64, "wooden_door");
		map.put(159, "stained_hardened_clay");
		map.put(137, "command_block");
		map.put(90, "portal");
		map.put(94, "powered_repeater");
		map.put(109, "stone_brick_stairs");
		map.put(0, "air");
		map.put(111, "waterlily");
		map.put(164, "dark_oak_stairs");
		map.put(53, "oak_stairs");
		map.put(2, "grass");
		map.put(107, "fence_gate");
		map.put(110, "mycelium");
		map.put(49, "obsidian");
		map.put(4, "cobblestone");
		map.put(68, "wall_sign");
		map.put(156, "quartz_stairs");
		map.put(130, "ender_chest");
		map.put(82, "clay");
		map.put(48, "mossy_cobblestone");
		map.put(3, "dirt");
		map.put(91, "lit_pumpkin");
		map.put(145, "anvil");
		map.put(149, "unpowered_comparator");
		map.put(93, "unpowered_repeater");
		map.put(11, "lava");
		map.put(62, "lit_furnace");
		map.put(15, "iron_ore");
		map.put(74, "lit_redstone_ore");
		map.put(57, "diamond_block");
		map.put(30, "web");
		map.put(170, "hay_block");
		map.put(136, "jungle_stairs");
		map.put(114, "nether_brick_stairs");
		map.put(70, "stone_pressure_plate");
		map.put(143, "wooden_button");
		map.put(43, "double_stone_slab");
		map.put(14, "gold_ore");
		map.put(105, "melon_stem");
		map.put(46, "tnt");
		map.put(154, "hopper");
		map.put(44, "stone_slab");
		map.put(142, "potatoes");
		map.put(129, "emerald_ore");
		map.put(39, "brown_mushroom");
		map.put(18, "leaves");
		map.put(165, "sf_ss:block_name");
		map.put(131, "tripwire_hook");
		map.put(37, "yellow_flower");
		map.put(157, "activator_rail");
		map.put(76, "redstone_torch");
		map.put(125, "double_wooden_slab");
		map.put(96, "trapdoor");
		map.put(124, "lit_redstone_lamp");
		map.put(161, "leaves2");
		map.put(60, "farmland");
		map.put(9, "water");
		map.put(117, "brewing_stand");
		map.put(27, "golden_rail");
		map.put(89, "glowstone");
		map.put(171, "carpet");
		map.put(113, "nether_brick_fence");
		map.put(86, "pumpkin");
		map.put(5, "planks");
		map.put(36, "piston_extension");
		map.put(122, "dragon_egg");
		map.put(63, "standing_sign");
		map.put(23, "dispenser");
		map.put(24, "sandstone");
		map.put(28, "detector_rail");
		map.put(172, "hardened_clay");
		map.put(160, "stained_glass_pane");
		map.put(19, "sponge");
		map.put(26, "bed");
		map.put(147, "light_weighted_pressure_plate");
		map.put(127, "cocoa");
		map.put(95, "stained_glass");
		map.put(101, "iron_bars");
		map.put(66, "rail");
		map.put(97, "monster_egg");
		map.put(52, "mob_spawner");
		map.put(55, "redstone_wire");
		map.put(126, "wooden_slab");
		map.put(144, "skull");
		map.put(139, "cobblestone_wall");
		map.put(65, "ladder");
		map.put(121, "end_stone");
	}

}

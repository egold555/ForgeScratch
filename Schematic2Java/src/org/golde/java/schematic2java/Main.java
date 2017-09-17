package org.golde.java.schematic2java;

import java.io.File;

import org.golde.java.schematic2java.converter.CodeWriter;
import org.golde.java.schematic2java.converter.Converter;

/*
 * Based on my Gui Schematic 2 java, but optmised for ScratchForge
 */
public class Main {

	public static void main(String[] args) {
		if(args.length < 2) {
			PLog.error("Schematic2Java [schematic] [ignore air] <java optput>");
			return;
		}
		
		boolean ignoreAir = Boolean.valueOf(args[1]);
		File output;
		File input = new File(args[0]);
		
		if(args.length != 3) {
			output = new File("output.java");
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

}

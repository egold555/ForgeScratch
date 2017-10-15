package org.golde.java.schematic2xml.converter;

import java.io.File;
import java.io.FileWriter;

import org.golde.java.schematic2xml.Main;
import org.golde.java.schematic2xml.PLog;

public class CodeWriter {


    private static final String NL = System.getProperty("line.separator");
    
    
    
    public static void writeToFile(boolean ignoreAir, final File f) {
        try {
            final FileWriter writer = new FileWriter(f, false);
            writer.write("<?xml version=\"1.0\"?>");
            writer.write(NL);
            writer.write("<ScratchForge version= \"1.0\">");
            writer.write(NL);
            writer.write("<modName>Gen</modName>");
            writer.write(NL);
            writer.write("<textures>");
            writer.write(NL);
            writer.write("</textures>");
            writer.write(NL);
            
            final String XMLTemplate = "<block type=\"mcaction_placeblockmeta\"><value name=\"BLOCK\"><block type=\"mcblockinput\"><field name=\"BLOCK\">%id%</field></block></value><value name=\"META\"><block type=\"math_number\"><field name=\"NUM\">%meta%</field></block></value><value name=\"LOC_X\"><block type=\"math_arithmetic\"><field name=\"OP\">ADD</field><value name=\"A\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"location_block_x\"></block></value><value name=\"B\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"math_number\"><field name=\"NUM\">%x%</field></block></value></block></value><value name=\"LOC_Y\"><block type=\"math_arithmetic\"><field name=\"OP\">ADD</field><value name=\"A\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"location_block_y\"></block></value><value name=\"B\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"math_number\"><field name=\"NUM\">%y%</field></block></value></block></value><value name=\"LOC_Z\"><block type=\"math_arithmetic\"><field name=\"OP\">ADD</field><value name=\"A\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"location_block_z\"></block></value><value name=\"B\"><shadow type=\"math_number\"><field name=\"NUM\">1</field></shadow><block type=\"math_number\"><field name=\"NUM\">%z%</field></block></value></block></value>";
            String blocklyXML = "";
            
            boolean first = true;
            int blocksFound = 0;
            for (int count = 0; count < Converter.blockID.length; ++count) {
            	PLog.info("COUNT: " + count);
            	if (Converter.blockID[count] != 0) {
            		if (!first) {
                    	blocklyXML += "<next>";
                    }            		
            		/*
            		 * %id%
            		 * %meta%
            		 * %x%
            		 * %y%
            		 * %z%
            		 */
            		blocklyXML += XMLTemplate.replace("%id%", Main.map.get(Converter.blockID[count])).replace("%meta%", String.valueOf(0)).replace("%x%", String.valueOf(Converter.posX[count])).replace("%y%", String.valueOf(Converter.posY[count])).replace("%z%", String.valueOf(Converter.posZ[count]));
            	    first = false;
            	    blocksFound += 1;
            	}
            }
            
            for (int count = 0; count < blocksFound; ++count) {
            	blocklyXML += "</block>";
            	if (count != blocksFound - 1) {
            		blocklyXML += "</next>";
            	}
            }
            
            writer.write("<blockly><xml xmlns=\"http://www.w3.org/1999/xhtml\"><block type=\"mcblock\" x=\"488\" y=\"113\"><field name=\"NAME\">Block Name</field><field name=\"MATERIAL\">Material.ground</field><field name=\"UNBREAKABLE\">FALSE</field><field name=\"EXPLOSION\">FALSE</field><statement name=\"Options\"><block type=\"mcblockoptions_blockplaced\"><statement name=\"CODE\">" + blocklyXML + "</statement></block></statement></block></xml></blockly>" + 
            		"");
            writer.write(NL);
            
           /* if (!ignoreAir) {
                for (int count = 0; count < Converter.blockID.length; ++count) {
                    if (count % 1600 == 0 && count != 0) {
                        writeNewMethode(writer);
                    }
                    writer.write(INDENT + "placeBlockAt(" + Converter.posX[count] + ", " + Converter.posY[count] + ", " + Converter.posZ[count] + ", " + Converter.blockID[count] + ");");
                    if (Converter.metadata[count] != 0) {
                        writer.write(NL);
                        writer.write(INDENT +"placeBlockAt(" + Converter.posX[count] + ", " + Converter.posY[count] + ", " + Converter.posZ[count] + ", " + Converter.blockID[count] + ", " + Converter.metadata[count] + ");");
                    }
                    writer.write(NL);
                }
            }
            else {
                for (int count = 0; count < Converter.blockID.length; ++count) {
                    if (CodeWriter.linesWithourAir % 1600 == 0 && CodeWriter.linesWithourAir != 0) {
                        writeNewMethode(writer);
                    }
                    if (Converter.blockID[count] != 0) {
                        writer.write(INDENT +"placeBlockAt(x+" + Converter.posX[count] + ", y+" + Converter.posY[count] + ", z+" + Converter.posZ[count] + ", " + Converter.blockID[count] + ");");
                        if (Converter.metadata[count] != 0) {
                            writer.write(NL);
                            writer.write(INDENT +"placeBlockAt(x+" + Converter.posX[count] + ", y+" + Converter.posY[count] + ", z+" + Converter.posZ[count] + ", " + Converter.blockID[count] + ", " + Converter.metadata[count] + ");");
                        }
                        writer.write(NL);
                        ++CodeWriter.linesWithourAir;
                    }
                }
            }*/
            writer.write("</ScratchForge>");
            writer.write(NL);
            writer.flush();
            writer.close();
            PLog.info("Saved code succesfully.");
        }
        catch (Exception e) {
        	PLog.error(e, "An error occurred.");
        }
    }
	
}

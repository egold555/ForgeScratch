package org.golde.java.schematic2java.converter;

import java.io.File;
import java.io.FileWriter;

import org.golde.java.schematic2java.PLog;

public class CodeWriter {

	private static int currentMethode;
    private static int linesWithourAir;
    private static final String NL = System.getProperty("line.separator");
    private static final String INDENT = "\t";
    
    static {
        CodeWriter.currentMethode = 0;
        CodeWriter.linesWithourAir = 0;
    }
    
    public static void writeToFile(boolean ignoreAir, final File f) {
        try {
            final FileWriter writer = new FileWriter(f, false);
            writer.write("public void generate() {");
            writer.write(NL);
            writer.write(NL);
            if (!ignoreAir) {
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
            }
            writer.write("}");
            writer.flush();
            writer.close();
            PLog.info("Saved code succesfully.");
        }
        catch (Exception e) {
        	PLog.error(e, "An error occurred.");
        }
    }
    
    private static void writeNewMethode(final FileWriter writer) {
        ++CodeWriter.currentMethode;
        try {
            writer.write(INDENT + "spawn" + CodeWriter.currentMethode + "();");
            writer.write(NL);
            writer.write("}");
            writer.write(NL);
            writer.write(NL);
            writer.write("private void spawn" + CodeWriter.currentMethode + "() {");
            writer.write(NL);
            writer.write(NL);
        }
        catch (Exception e) {
        	PLog.error(e, "An error occurred.");
        }
    }
	
}

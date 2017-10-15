package org.golde.java.schematic2xml.converter;

import java.io.File;

import org.golde.java.schematic2xml.PLog;

public class Converter {
	public static SchematicReader sr;
    public static int[] blockID;
    public static int[] posX;
    public static int[] posY;
    public static int[] posZ;
    public static int[] metadata;
    
    static {
        Converter.sr = new SchematicReader();
    }
    
    public static void loadSchematic(final File res) {
        final SchematicReader.Schematic sh = Converter.sr.get(res);
        if (sh == null) {
           PLog.error("Fatal error occured while reading the file!");
        }
        int ipre = 0;

        for (int sy = 0; sy < sh.height; ++sy) {
            for (int sz = 0; sz < sh.length; ++sz) {
                for (int sx = 0; sx < sh.width; ++sx) {
                    /*if (WindowMain.chckbxIgnoreAir.isSelected() && sh.blocks[ipre] != 0) {
                    }*/
                    ++ipre;
                }
            }
        }
        Converter.blockID = new int[ipre];
        Converter.posX = new int[ipre];
        Converter.posY = new int[ipre];
        Converter.posZ = new int[ipre];
        Converter.metadata = new int[ipre];
        int i = 0;
        for (int sy2 = 0; sy2 < sh.height; ++sy2) {
            for (int sz2 = 0; sz2 < sh.length; ++sz2) {
                for (int sx2 = 0; sx2 < sh.width; ++sx2) {
                    if (sh.blocks[i] < 0) {
                        Converter.blockID[i] = convertByte(sh.blocks[i]);
                    }
                    else {
                        Converter.blockID[i] = sh.blocks[i];
                    }
                    Converter.posX[i] = sx2;
                    Converter.posY[i] = sy2;
                    Converter.posZ[i] = sz2;
                    Converter.metadata[i] = sh.data[i];
                    ++i;
                }
            }
        }
        PLog.info("Succesfully converted the schematic file.");
    }
    
    private static short convertByte(final byte b) {
        return (short)(256 + b);
    }
}
    

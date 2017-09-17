package org.golde.java.schematic2java.converter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

public class SchematicReader {
private static NBTInputStream stream;
	
	@SuppressWarnings("deprecation")
	public Schematic get(final File res) {
        try {
            final FileInputStream is = new FileInputStream(res);
            SchematicReader.stream = new NBTInputStream(is);
            final CompoundTag ct = (CompoundTag)SchematicReader.stream.readTag();
            final Map<String, Tag> schematic = ct.getValue();
            final short width = ((ShortTag)this.getChildTag(schematic, "Width", ShortTag.class)).getValue();
            final short length = ((ShortTag)this.getChildTag(schematic, "Length", ShortTag.class)).getValue();
            final short height = ((ShortTag)this.getChildTag(schematic, "Height", ShortTag.class)).getValue();
            final byte[] blocks = ((ByteArrayTag)this.getChildTag(schematic, "Blocks", ByteArrayTag.class)).getValue();
            final byte[] data = ((ByteArrayTag)this.getChildTag(schematic, "Data", ByteArrayTag.class)).getValue();
            is.close();
            SchematicReader.stream.close();
            return new Schematic(width, height, length, blocks, data);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private Object getChildTag(final Map<String, Tag> schematic, final String name, final Class<? extends Tag> outputClass) {
        if (schematic.containsKey(name)) {
            return schematic.get(name);
        }
        try {
            return outputClass.newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    public class Schematic
    {
        public short width;
        public short height;
        public short length;
        public byte[] blocks;
        public byte[] data;
        
        public Schematic(final short width, final short height, final short length, final byte[] blocks, final byte[] data) {
            this.width = width;
            this.height = height;
            this.length = length;
            this.blocks = blocks;
            this.data = data;
        }
    }
}

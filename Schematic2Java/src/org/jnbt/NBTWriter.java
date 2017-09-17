package org.jnbt;

import static org.jnbt.NBTCompression.FROM_BYTE;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterOutputStream;

//@formatter:off

/*
* JNBT License
*
* Copyright (c) 2010 Graham Edgecombe
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
*     * Redistributions of source code must retain the above copyright notice,
*       this list of conditions and the following disclaimer.
*
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*
*     * Neither the name of the JNBT team nor the names of its
*       contributors may be used to endorse or promote products derived from
*       this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/

//@formatter:on

/**
 * Class that allows writing an NBT data structure to a stream step by step without
 * keeping the entire NBT structure in memory.
 * 
 * @author bilde2910
 */
public final class NBTWriter implements Closeable, Flushable {
	private final DataOutputStream os;
	private final int flags;
	
	// The number of items left to write in a TAG_List at the given depth (index=depth)
	// or -1 if the object at that depth is a TAG_Compound.
	private ArrayList<Integer> depthItems = new ArrayList<Integer>();
	
	// The type of element in the TAG_List at the given depth (index=depth)
	// or TAG_End if the object at that depth is a TAG_Compound.
	private ArrayList<NBTTagType> depthType = new ArrayList<NBTTagType>();
	
	// Current data structure writing depth
	private int depth = -1;
	
	// Whether or not the root tag has been written yet.
	private boolean rootWritten = false;
	
	
	/**
	 * Allows multiple root tags to be written to the output stream.
	 * <b>NBT files with multiple root tags are not allowed per the NBT specification.</b>
	 * Please only use this if you are saving or transmitting multiple NBT structures
	 * across a network connection, or to a file container that expects an array of NBT
	 * files that are chained together. This effectively allows creating a chain of
	 * NBT files through a single stream/NBT writer.
	 */
	public static final int FLAG_ALLOW_ROOT_TAG_CHAINING = 0x01;
	
	/**
	 * Allows using a tag other than TAG_Compound as the root tag of the NBT structure.
	 * <b>NBT files with a non-TAG_Compound root are not allowed per the NBT specification.</b>
	 * Only use this if you absolutely <i>need</i> to know the binary structure of a specific
	 * NBT tag. Non-TAG_Compound-rooted files are not considered NBT files; they will NOT be
	 * compatible with the NBT specification and will NOT be possible to open using other
	 * NBT specification compliant software. Do not use this unless you absolutely have to.
	 */
	public static final int FLAG_ALLOW_NON_COMPOUND_ROOT_TAG = 0x02;
	
	/**
	 * Creates a new {@code NBTWriter}, which will output data to the given
	 * output stream using GZip compression.
	 * 
	 * @since 1.7
	 *
	 * @param os The output stream to write the NBT structure to.
	 * 
	 * @deprecated Use {@link #NBTWriter(OutputStream, NBTCompression)} instead.
	 */
	@Deprecated
	public NBTWriter(OutputStream os) throws IOException {
		this(os, NBTCompression.GZIP);
	}
	
	/**
	 * Creates a new {@code NBTWriter}, which will output data to the given
	 * output stream.
	 *
	 * @since 1.7
	 *
	 * @param os The output stream to write the NBT structure to.
	 * @param gzipped Whether the output stream should be GZip-compressed.
	 * 
	 * @deprecated Use {@link #NBTWriter(OutputStream, NBTCompression)} instead.
	 */
	@Deprecated
	public NBTWriter(OutputStream os, boolean gzipped) throws IOException {
		this(os, gzipped ? NBTCompression.GZIP : NBTCompression.UNCOMPRESSED);
	}
	
	/**
	 * Creates a new {@code NBTWriter}, which will output data to the given
	 * output stream.
	 * 
	 * @since 1.7
	 * 
	 * @param os The output stream to write the NBT structure to.
	 * @param compression The compression method to use on the stream.
	 */
	public NBTWriter(OutputStream os, NBTCompression compression) throws IOException {
		this(os, compression, 0);
	}
	
	/**
	 * Creates a new {@code NBTWriter}, which will output data to the given
	 * output stream.
	 * 
	 * @since 1.7
	 * 
	 * @param os The output stream to write the NBT structure to.
	 * @param compression The compression method to use on the stream.
	 * @param flags Processing flags that allow overriding default behavior.
	 * 
	 * @deprecated Using flags to modify the behavior of {@code NBTWriter} may
	 * produce NBT files that do not follow the NBT specification. Such files
	 * will be incompatible with other NBT editors. <b>Only use this constructor
	 * if you have a VERY good reason to do so. You are strongly encouraged to
	 * use {@link #NBTWriter(OutputStream, NBTCompression)} instead.</b>
	 */
	@Deprecated
	public NBTWriter(OutputStream os, NBTCompression compression, int flags) throws IOException {
		this.flags = flags;
		switch (compression) {
			case UNCOMPRESSED:
				this.os = new DataOutputStream(os);
				break;
			case GZIP:
				this.os = new DataOutputStream(new GZIPOutputStream(os));
				break;
			case ZLIB:
				this.os = new DataOutputStream(new InflaterOutputStream(os));
				break;
			case FROM_BYTE:
				throw new IllegalArgumentException(FROM_BYTE.name() + " is only for reading.");
			default:
				throw new AssertionError("[JNBT] Unimplemented " + NBTCompression.class.getSimpleName()
				                         + ": " + compression);
		}
	}
	
	/**
	 * Returns whether or not the given flag has been specified during the
	 * construction of this {@code NBTWriter} instance.
	 * 
	 * @since 1.7
	 * 
	 * @param flag The flag to check for.
	 */
	private boolean hasFlag(int flag) {
		return (this.flags & flag) == flag;
	}
	
	/**
	 * Writes the tag type and name to the stream and performs additional
	 * checks to ensure NBT specification compliance.
	 * 
	 * @since 1.7
	 * 
	 * @param tagName The name of the tag to write.
	 * @param type The type of tag to write.
	 * 
	 * @throws IllegalStateException if an attempt is made to write a tag of
	 * the wrong type to a list, or to a list that has already been filled.
	 */
	private void writeTagHeader(String tagName, NBTTagType type) throws IOException, IllegalStateException {
		if (depth < 0) {
			if (type != NBTTagType.TAG_COMPOUND && !hasFlag(FLAG_ALLOW_NON_COMPOUND_ROOT_TAG)) {
				throw new IOException("[JNBT] Invalid root tag type: " + type.getMojangName() + ".");
			} else if (rootWritten && !hasFlag(FLAG_ALLOW_ROOT_TAG_CHAINING)) {
				throw new IOException("[JNBT] Only one root tag is permitted per file.");
			}
		} else if (getRemainingItems() != -1 && type != this.depthType.get(this.depth)) {
			throw new IllegalStateException("[JNBT] Attempted to write a " + type.getMojangName()
								+ " tag to a " + this.depthType.get(this.depth).getMojangName() + " list!");
		} else if (getRemainingItems() == 0) {
			throw new IllegalStateException("[JNBT] Cannot write item to list; list size exceeded!");
		}
		rootWritten = true;
		if (depth < 0 || getRemainingItems() == -1) {
			os.writeByte(type.getTypeByte());
			byte[] tagNameBytes = tagName.getBytes(NBTConstants.CHARSET);
			os.writeShort(tagNameBytes.length);
			os.write(tagNameBytes);
		}
		if (depth >= 0 && getRemainingItems() != -1) {
			this.depthItems.set(this.depth, getRemainingItems() - 1);
		}
	}
	
	// No javadocs have been added for these methods, as it should be obvious
	// what each of them do.
	
	public void writeByte(ByteTag b) throws IOException {
		writeByte(b.getName(), b.getValue());
	}
	
	public void writeByte(String tagName, byte b) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_BYTE);
		os.writeByte(b);
	}
	
	public void writeShort(ShortTag s) throws IOException {
		writeShort(s.getName(), s.getValue());
	}
	
	public void writeShort(String tagName, short s) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_SHORT);
		os.writeShort(s);
	}
	
	public void writeInt(IntTag i) throws IOException {
		writeInt(i.getName(), i.getValue());
	}
	
	public void writeInt(String tagName, int i) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_INT);
		os.writeInt(i);
	}
	
	public void writeLong(LongTag l) throws IOException {
		writeLong(l.getName(), l.getValue());
	}
	
	public void writeLong(String tagName, long l) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_LONG);
		os.writeLong(l);
	}
	
	public void writeFloat(FloatTag f) throws IOException {
		writeFloat(f.getName(), f.getValue());
	}
	
	public void writeFloat(String tagName, float f) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_FLOAT);
		os.writeFloat(f);
	}
	
	public void writeDouble(DoubleTag d) throws IOException {
		writeDouble(d.getName(), d.getValue());
	}
	
	public void writeDouble(String tagName, double d) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_DOUBLE);
		os.writeDouble(d);
	}
	
	public void writeByteArray(ByteArrayTag ba) throws IOException {
		writeByteArray(ba.getName(), ba.getValue());
	}
	
	public void writeByteArray(String tagName, byte[] ba) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_BYTE_ARRAY);
		os.writeInt(ba.length);
		os.write(ba);
	}
	
	public void writeString(StringTag str) throws IOException {
		writeString(str.getName(), str.getValue());
	}
	
	public void writeString(String tagName, String str) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_STRING);
		byte[] strBytes = str.getBytes(NBTConstants.CHARSET);
		os.writeShort(strBytes.length);
		os.write(strBytes);
	}
	
	public void writeIntArray(IntArrayTag ia) throws IOException {
		writeIntArray(ia.getName(), ia.getValue());
	}
	
	public void writeIntArray(String tagName, int[] ia) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_INT_ARRAY);
		os.writeInt(ia.length);
		for (int i = 0; i < ia.length; i++) os.writeInt(ia[i]);
	}
	
	/**
	 * Returns the remaining number of items that should be written to the currently
	 * active TAG_List parent. -1 if the parent being written to is an TAG_Compound.
	 * 
	 * @since 1.7
	 */
	private int getRemainingItems() {
		return this.depthItems.get(this.depth);
	}
	
	/**
	 * Writes the opening tag of a TAG_Compound to the stream.
	 * 
	 * @since 1.7
	 * 
	 * @param tagName The name of the compound tag.
	 */
	public void beginObject(String tagName) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_COMPOUND);
		// -1 to internally indicate that the tag at this depth is a TAG_Compound.
		this.depthItems.add(-1);
		// TAG_End to internally indicate that the tag at this depth is a TAG_Compound.
		this.depthType.add(NBTTagType.TAG_END);
		// Increase the depth at which we are currently writing.
		this.depth++;
	}
	
	/**
	 * Writes the opening tag of a TAG_List structure to the stream.
	 * 
	 * @since 1.7
	 * 
	 * @param tagName The name of the tag list.
	 * @param listType The type of this list's child elements.
	 * @param length The total number of children this list will contain.
	 */
	public void beginArray(String tagName, NBTTagType listType, int length) throws IOException {
		writeTagHeader(tagName, NBTTagType.TAG_LIST);
		os.writeByte(listType.getTypeByte());
		os.writeInt(length);
		// Internally indicate the number of items that this list will contain, to
		// ensure that no more, and no less, than this number of children will be
		// written to this TAG_List.
		this.depthItems.add(length);
		// Internally indicate the type of list that is present at this structure depth.
		this.depthType.add(listType);
		// Increase the depth at which we are currently writing.
		this.depth++;
	}
	
	/**
	 * Closes the TAG_List that is currently being written to.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if an attempt is made to close a TAG_Compound
	 * using this function, or if fewer tags have been written to the list than what
	 * was specified in {@link #beginArray(String, NBTTagType, int)}.
	 */
	public void endArray() throws IOException, IllegalStateException {
		int itemsLeft = getRemainingItems();
		if (itemsLeft == -1) {
			throw new IllegalStateException("[JNBT] Attempted to end an object using endArray()!");
		} else if (itemsLeft > 0) {
			throw new IllegalStateException("[JNBT] Attempted to end an array prematurely!");
		}
		this.depth--;
		this.depthItems.remove(this.depthItems.size() - 1);
		this.depthType.remove(this.depthType.size() - 1);
	}
	
	/**
	 * Closes the TAG_Compound that is currently being written to, appending a
	 * TAG_End to the structure.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if an attempt is made to close a TAG_List
	 * using this function, or if an attempt is made to close a TAG_Compound
	 * above the root element (root has no parents).
	 */
	public void endObject() throws IOException, IllegalStateException {
		if (this.depth < 0) {
			throw new IllegalStateException("[JNBT] Attempted to end non-existent object above the root element!");
		}
		int itemsLeft = getRemainingItems();
		if (itemsLeft != -1) {
			throw new IllegalStateException("[JNBT] Attempted to end an array using endObject()!");
		}
		os.writeByte(NBTTagType.TAG_END.getTypeByte());
		this.depth--;
		this.depthItems.remove(this.depthItems.size() - 1);
		this.depthType.remove(this.depthType.size() - 1);
	}

	/**
	 * Closes the underlying output stream.
	 */
	@Override
	public void close() throws IOException {
		os.close();
	}

	/**
	 * Flushes the underlying output stream.
	 */
	@Override
	public void flush() throws IOException {
		os.flush();
	}
}

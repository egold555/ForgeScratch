package org.jnbt;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

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
 * Class that allows reading an NBT data structure from a stream step by step without
 * keeping the entire NBT structure in memory.
 * 
 * @author bilde2910
 */
public final class NBTReader implements Closeable {
	private final DataInputStream is;
	
	// The type of the next tag in the stream.
	private int nextType = -1;
	
	// The name of the next tag in the stream.
	private String nextName = null;
	
	// The number of items left to read in the TAG_List at the given depth (index=depth)
	// or -1 if the object at that depth is a TAG_Compound.
	private ArrayList<Integer> depthItems = new ArrayList<Integer>();
	
	// The type of element in the TAG_List at the given depth (index=depth)
	// or -1 if the object at that depth is a TAG_Compound.
	private ArrayList<Integer> depthType = new ArrayList<Integer>();
	
	// Current data structure writing depth.
	private int depth = -1;
	
	/**
	 * Creates a new {@code NBTReader}, reading data from the given input stream
	 * using GZip compression.
	 * 
	 * @since 1.7
	 * 
	 * @param is The input stream to read the NBT structure from.
	 * 
	 * @deprecated Use {@link #NBTReader(InputStream, NBTCompression)} instead.
	 */
	@Deprecated
	public NBTReader(InputStream is) throws IOException {
		this(is, NBTCompression.GZIP);
	}
	
	/**
	 * Creates a new {@code NBTReader}, reading data from the given input stream.
	 * 
	 * @since 1.7
	 * 
	 * @param is The input stream to read the NBT structure from.
	 * @param gzipped Whether the input stream is GZip-compressed.
	 * 
	 * @deprecated Use {@link #NBTReader(InputStream, NBTCompression)} instead.
	 */
	@Deprecated
	public NBTReader(InputStream is, boolean gzipped) throws IOException {
		this(is, gzipped ? NBTCompression.GZIP : NBTCompression.UNCOMPRESSED);
	}
	
	/**
	 * Creates a new {@code NBTReader}, reading data from the given input stream.
	 * 
	 * @since 1.7
	 * 
	 * @param is The input stream to read the NBT structure from.
	 * @param compression The compression type of the input data.
	 */
	public NBTReader(InputStream is, NBTCompression compression) throws IOException {
		NBTCompression resolvedCompression;
		if (compression == NBTCompression.FROM_BYTE) {
			int compressionByte = is.read();
			if (compressionByte < 0) {
				throw new EOFException();
			}
			resolvedCompression = NBTCompression.fromId(compressionByte);
		} else {
			resolvedCompression = compression;
		}

		switch (resolvedCompression) {
			case UNCOMPRESSED:
				this.is = new DataInputStream(is);
				break;
			case GZIP:
				this.is = new DataInputStream(new GZIPInputStream(is));
				break;
			case ZLIB:
				this.is = new DataInputStream(new InflaterInputStream(is));
				break;
			case FROM_BYTE:
				throw new AssertionError("FROM_BYTE Should have been handled already");
			default:
				throw new AssertionError("[JNBT] Unimplemented " + NBTCompression.class.getSimpleName()
				                         + ": " + compression);
		}
	}
	
	/**
	 * Reads and returns the name of the next tag in the stream.
	 * 
	 * @since 1.7
	 */
	public String nextName() throws IOException {
		// Type precedes name in the file, hence it must be determined first.
		nextType();
		
		// Cache the name of the tag once it has been read.
		if (this.nextName != null) {
			return this.nextName;
		}

		if (this.nextType == NBTConstants.TYPE_END) {
			this.nextName = "";
		} else {
			int    nameLength = is.readShort() & 0xFFFF;
			byte[] nameBytes  = new byte[nameLength];
			is.readFully(nameBytes);
			this.nextName = new String(nameBytes, NBTConstants.CHARSET);
		}
		return this.nextName;
	}
	
	/**
	 * Reads and returns the type of the next tag in the stream.
	 * 
	 * @since 1.7
	 */
	public int nextType() throws IOException {
		// Cache the type of tag once it has been read.
		if (this.nextType == -1) {
			this.nextType = is.readByte() & 0xFF;
		}
		return this.nextType;
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Byte.
	 * 
	 * @since 1.7
	 */
	public byte nextByte() throws IOException {
		next();
		return is.readByte();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Short.
	 * 
	 * @since 1.7
	 */
	public short nextShort() throws IOException {
		next();
		return is.readShort();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Int.
	 * 
	 * @since 1.7
	 */
	public int nextInt() throws IOException {
		next();
		return is.readInt();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Long.
	 * 
	 * @since 1.7
	 */
	public long nextLong() throws IOException {
		next();
		return is.readLong();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Float.
	 * 
	 * @since 1.7
	 */
	public float nextFloat() throws IOException {
		next();
		return is.readFloat();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Double.
	 * 
	 * @since 1.7
	 */
	public double nextDouble() throws IOException {
		next();
		return is.readDouble();
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Byte_Array.
	 * 
	 * @since 1.7
	 */
	public byte[] nextByteArray() throws IOException {
		next();
		int length = is.readInt();
		byte[] data = new byte[length];
		is.readFully(data);
		return data;
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_String.
	 * 
	 * @since 1.7
	 */
	public String nextString() throws IOException {
		next();
		int length = is.readShort();
		byte[] bytes = new byte[length];
		is.readFully(bytes);
		return new String(bytes, NBTConstants.CHARSET);
	}
	
	/**
	 * Reads the value of the next tag in the stream as a TAG_Int_Array.
	 * 
	 * @since 1.7
	 */
	public int[] nextIntArray() throws IOException {
		next();
		int length = is.readInt();
		int[] array = new int[length];
		for (int i = 0; i < length; i++) array[i] = is.readInt();
		return array;
	}
	
	/**
	 * Prepares the next tag to be read from the stream.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if an attempt is made to read a tag from a
	 * TAG_List that has no unread tags.
	 */
	private void next() throws IOException, IllegalStateException {
		this.nextName = null;
		int itemsLeft = (this.depth < 0 ? -1 : getRemainingItems());
		if (itemsLeft > 0) {
			itemsLeft--;
			this.depthItems.set(this.depth, itemsLeft);
			this.nextType = this.depthType.get(this.depth);
		} else if (itemsLeft == 0) {
			throw new IllegalStateException("[JNBT] Attempted to read next element from a list with no remaining elements!");
		} else {
			this.nextType = -1;
		}
	}
	
	/**
	 * Returns the remaining number of items that should be read from the currently
	 * active TAG_List parent. -1 if the parent being read from is an TAG_Compound.
	 * 
	 * @since 1.7
	 */
	private int getRemainingItems() {
		return this.depthItems.get(this.depth);
	}
	
	/**
	 * Opens the next tag of the stream as a TAG_Compound, allowing tags within the
	 * TAG_Compound to be read.
	 * 
	 * @since 1.7
	 */
	public void beginObject() {
		// Reset tag name and type caches.
		this.nextName = null;
		this.nextType = -1;
		// -1 to internally indicate that the tag at this depth is a TAG_Compound.
		this.depthItems.add(-1);
		this.depthType.add(-1);
		// Increase the depth at which we are currently reading.
		this.depth++;
	}
	
	/**
	 * Opens the next tag of the stream as a TAG_List, allowing tags within the
	 * TAG_List to be read.
	 * 
	 * @since 1.7
	 */
	public void beginArray() throws IOException {
		// Reset tag name. (Elements in a TAG_List have no names.)
		this.nextName = null;
		// Determine the type and number of tags this list contains.
		int type = is.readByte();
		int length = is.readInt();
		this.nextType = type;
		// Internally indicate the number of items that this list contains, to
		// ensure that no more, and no less, than this number of children will be
		// read from this TAG_List.
		this.depthItems.add(length);
		// Internally indicate the type of list that is present at this structure depth.
		this.depthType.add(type);
		// Increase the depth at which we are currently reading.
		this.depth++;
	}
	
	/**
	 * Whether or not another element is available in this TAG_Compound or TAG_List.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if this method is called at the root level.
	 */
	public boolean hasNext() throws IOException, IllegalStateException {
		if (this.depth < 0) {
			// This method was called at root level
			throw new IllegalStateException("[JNBT] hasNext() cannot be called at the root level!");
		}
		int itemsLeft = getRemainingItems();
		//     [--TAG_List--]   [---------------------TAG_Compound---------------------]
		return itemsLeft > 0 || (itemsLeft == -1 && nextType() != NBTConstants.TYPE_END);
	}
	
	/**
	 * Finish reading the current TAG_List instance and return to reading elements
	 * from that list's parent tag.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if there are remaining elements to read in
	 * this TAG_List.
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
		next();
	}
	
	/**
	 * Finish reading the current TAG_Compound instance and return to reading elements
	 * from that compound tag's parent tag.
	 * 
	 * @since 1.7
	 * 
	 * @throws IllegalStateException if there are remaining elements to read in
	 * this TAG_Compound.
	 */
	public void endObject() throws IOException, IllegalStateException {
		int itemsLeft = getRemainingItems();
		if (itemsLeft != -1) {
			throw new IllegalStateException("[JNBT] Attempted to end an array using endObject()!");
		}
		if (this.nextType != NBTConstants.TYPE_END) {
			throw new IllegalStateException("[JNBT] Attempted to end an object prematurely!");
		}
		this.depth--;
		this.depthItems.remove(this.depthItems.size() - 1);
		this.depthType.remove(this.depthType.size() - 1);
		next();
	}
	
	/**
	 * Skips the next tag in the stream. This must only be called after the name of the
	 * tag has been read using {@link #nextName()}.
	 * 
	 * @since 1.7
	 */
	public void skipValue() throws IOException {
		skipValue(this.nextType);
		next();
	}
	
	/**
	 * Skips the tag of the given type at the current position in the stream.
	 * 
	 * @since 1.7
	 */
	private void skipValue(int type) throws IOException {
		// Number of bytes that should be skipped over in the input stream.
		int length = 0;
		
		switch (type) {
			case NBTConstants.TYPE_END:
				length = 0;
				break;
				
			case NBTConstants.TYPE_BYTE:
				length = 1;
				break;
				
			case NBTConstants.TYPE_SHORT:
				length = 2;
				break;
				
			case NBTConstants.TYPE_INT:
			case NBTConstants.TYPE_FLOAT:
				length = 4;
				break;
				
			case NBTConstants.TYPE_LONG:
			case NBTConstants.TYPE_DOUBLE:
				length = 8;
				break;
				
			case NBTConstants.TYPE_BYTE_ARRAY:
				length = is.readInt();
				break;
				
			case NBTConstants.TYPE_STRING:
				length = is.readUnsignedShort();
				break;
				
			case NBTConstants.TYPE_LIST:
				int listType = is.readByte();
				int listLength = is.readInt();
				for (int i = 0; i < listLength; i++) {
					skipValue(listType);
				}
				length = 0;
				break;
				
			case NBTConstants.TYPE_COMPOUND:
				int compType = is.readByte() & 0xFF;
				while (compType != NBTConstants.TYPE_END) {
					int nameLength = is.readShort() & 0xFFFF;
					
					// Instead of using is.skip, the bytes are read using readFully()
					// which ensures that all of the required bytes are actually read.
					// is.skip may skip fewer than requested bytes in some cases,
					// particularly when is wraps around a network input.
					byte[] skip = new byte[nameLength];
					is.readFully(skip);
					
					skipValue(compType);
					compType = is.readByte() & 0xFF;
				}
				length = 0;
				break;
				
			case NBTConstants.TYPE_INT_ARRAY:
				length = is.readInt() * 4;
				break;
				
			default:
				throw new IOException("[JNBT] Invalid tag type: " + this.nextType + '.');
		}
		
		// Instead of using is.skip, the bytes are read using readFully() which ensures
		// that all of the required bytes are actually read. is.skip may skip fewer than
		// requested bytes in some cases, particularly when is wraps around a network input.
		while (length > 0) {
			int delta = Math.min(length, 32768);
			byte[] discard = new byte[delta];
			is.readFully(discard);
			length -= delta;
		}
	}

	/**
	 * Closes the underlying input stream.
	 */
	@Override
	public void close() throws IOException {
		is.close();
	}
}

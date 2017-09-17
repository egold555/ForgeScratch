package org.jnbt;

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

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import static org.jnbt.NBTCompression.FROM_BYTE;
import static org.jnbt.NBTCompression.GZIP;
import static org.jnbt.NBTCompression.UNCOMPRESSED;
import static org.jnbt.NBTCompression.fromId;
import static org.jnbt.NBTConstants.CHARSET;

/**
 * <p>
 * This class reads <strong>NBT</strong>, or <strong>Named Binary Tag</strong>
 * streams, and produces an object graph of subclasses of the {@code Tag}
 * object.
 * </p>
 * <p>
 * The NBT format was created by Markus Persson, and the specification may be
 * found at <a href="http://www.minecraft.net/docs/NBT.txt">
 * http://www.minecraft.net/docs/NBT.txt</a>.
 * </p>
 *
 * @author Graham Edgecombe, ensirius
 */
public final class NBTInputStream implements Closeable {
	private final DataInputStream is;

	/**
	 * Creates a new {@code NBTInputStream}, which will source its data
	 * from the specified GZIP-compressed input stream.
	 *
	 * @deprecated Use {@link #NBTInputStream(InputStream, NBTCompression)} instead;
	 */
	@Deprecated
	public NBTInputStream(InputStream is) throws IOException {
		this(is, GZIP);
	}

	/**
	 * Creates a new {@code NBTInputStream}, which will source its data
	 * from the specified input stream.
	 *
	 * @param gzipped Whether the stream is GZip-compressed.
	 * @deprecated Use {@link #NBTInputStream(InputStream, NBTCompression)} instead;
	 */
	@Deprecated
	public NBTInputStream(InputStream is, boolean gzipped) throws IOException {
		this(is, gzipped ? GZIP : UNCOMPRESSED);
	}

	/**
	 * Creates a new {@code NBTInputStream}, which will source its data
	 * from the specified raw input stream.
	 *
	 * @since 1.4
	 * @deprecated Use {@link #NBTInputStream(InputStream, NBTCompression)} instead;
	 */
	@Deprecated
	public NBTInputStream(DataInputStream is) {
		this.is = is;
	}

	/**
	 * Creates a new {@code NBTInputStream}, which will source its data
	 * from the specified input stream.
	 *
	 * @param compression The type of compression the input stream uses.
	 * @throws IOException if an I/O error occurs.
	 * @since 1.5
	 */
	public NBTInputStream(InputStream is, NBTCompression compression) throws IOException {
		NBTCompression resolvedCompression;
		if (compression == FROM_BYTE) {
			int compressionByte = is.read();
			if (compressionByte < 0) {
				//noinspection NewExceptionWithoutArguments
				throw new EOFException();
			}
			resolvedCompression = fromId(compressionByte);
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
	 * Reads an NBT tag from the stream.
	 */
	public Tag readTag() throws IOException {
		return readTag(0);
	}

	/**
	 * Reads an NBT from the stream.
	 *
	 * @param depth The depth of this tag.
	 */
	private Tag readTag(int depth) throws IOException {
		int type = is.readByte() & 0xFF;

		String name;
		if (type == NBTConstants.TYPE_END) {
			name = "";
		} else {
			int    nameLength = is.readShort() & 0xFFFF;
			byte[] nameBytes  = new byte[nameLength];
			is.readFully(nameBytes);
			name = new String(nameBytes, CHARSET);
		}

		return readTagPayload(type, name, depth);
	}

	/**
	 * Reads the payload of a tag, given the name and type.
	 */
	private Tag readTagPayload(int type, String name, int depth) throws IOException {
		switch (type) {
			case NBTConstants.TYPE_END:
				return readEndTagPayload(depth);
			case NBTConstants.TYPE_BYTE:
				return readByteTagPayload(name);
			case NBTConstants.TYPE_SHORT:
				return readShortTagPayload(name);
			case NBTConstants.TYPE_INT:
				return readIntTagPayload(name);
			case NBTConstants.TYPE_LONG:
				return readLongTagPayload(name);
			case NBTConstants.TYPE_FLOAT:
				return readFloatTagPayload(name);
			case NBTConstants.TYPE_DOUBLE:
				return readDoubleTagPayload(name);
			case NBTConstants.TYPE_BYTE_ARRAY:
				return readByteArrayTagPayload(name);
			case NBTConstants.TYPE_STRING:
				return readStringTagPayload(name);
			case NBTConstants.TYPE_LIST:
				return readListTagPayload(name, depth);
			case NBTConstants.TYPE_COMPOUND:
				return readCompoundTagPayload(name, depth);
			case NBTConstants.TYPE_INT_ARRAY:
				return readIntArrayPayload(name);
			default:
				throw new IOException("[JNBT] Invalid tag type: " + type + '.');
		}
	}

	private Tag readEndTagPayload(int depth) throws IOException {
		if (depth == 0)
			throw new IOException("[JNBT] TAG_End found without a TAG_Compound/TAG_List tag preceding it.");

		return new EndTag();
	}

	private Tag readByteTagPayload(String name) throws IOException {
		return new ByteTag(name, is.readByte());
	}

	private Tag readShortTagPayload(String name) throws IOException {
		return new ShortTag(name, is.readShort());
	}

	private Tag readIntTagPayload(String name) throws IOException {
		return new IntTag(name, is.readInt());
	}

	private Tag readLongTagPayload(String name) throws IOException {
		return new LongTag(name, is.readLong());
	}

	private Tag readFloatTagPayload(String name) throws IOException {
		return new FloatTag(name, is.readFloat());
	}

	private Tag readDoubleTagPayload(String name) throws IOException {
		return new DoubleTag(name, is.readDouble());
	}

	private Tag readByteArrayTagPayload(String name) throws IOException {
		int    length = is.readInt();
		byte[] bytes  = new byte[length];
		is.readFully(bytes);
		return new ByteArrayTag(name, bytes);
	}

	private Tag readStringTagPayload(String name) throws IOException {
		int    length = is.readShort();
		byte[] bytes  = new byte[length];
		is.readFully(bytes);
		String string = new String(bytes, CHARSET);
		return new StringTag(name, string);
	}

	private Tag readListTagPayload(String name, int depth) throws IOException {
		int       typeByte = is.readByte();
		int       length   = is.readInt();
		List<Tag> tagList  = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			Tag tag = readTagPayload(typeByte, "", depth + 1);
			if (tag instanceof EndTag)
				throw new IOException("[JNBT] TAG_End not permitted in a list.");
			tagList.add(tag);
		}
		NBTTagType tagType = NBTTagType.fromTypeByte(typeByte);
		return new ListTag(name, tagType, tagList);
	}

	private Tag readCompoundTagPayload(String name, int depth) throws IOException {
		Map<String, Tag> tagMap = new HashMap<>(CompoundTag.DEFAULT_INITIAL_CAPACITY);
		while (true) {
			Tag tag = readTag(depth + 1);
			if (tag instanceof EndTag)
				break;

			tagMap.put(tag.getName(), tag);
		}
		return new CompoundTag(name, tagMap);
	}

	private Tag readIntArrayPayload(String name) throws IOException {
		int   length = is.readInt();
		int[] ints   = new int[length];
		for (int i = 0; i < length; i++)
			ints[i] = is.readInt();

		return new IntArrayTag(name, ints);
	}

	@Override
	public void close() throws IOException {
		is.close();
	}
}

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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterOutputStream;

import static org.jnbt.NBTCompression.FROM_BYTE;
import static org.jnbt.NBTCompression.GZIP;
import static org.jnbt.NBTCompression.UNCOMPRESSED;
import static org.jnbt.NBTConstants.CHARSET;

/**
 * <p>
 * This class writes <strong>NBT</strong>, or <strong>Named Binary Tag</strong>
 * {@code Tag} objects to an underlying {@code OutputStream}.
 * </p>
 * <p>
 * The NBT format was created by Markus Persson, and the specification may be
 * found at <a href="http://www.minecraft.net/docs/NBT.txt">
 * http://www.minecraft.net/docs/NBT.txt</a>.
 * </p>
 *
 * @author Graham Edgecombe, Jocopa3
 */
public final class NBTOutputStream implements Closeable {
	private final DataOutputStream os;

	/**
	 * Creates a new {@code NBTOutputStream}, which will write data to the
	 * specified underlying output stream, GZip-compressed.
	 *
	 * @deprecated Use {@link #NBTOutputStream(OutputStream, NBTCompression)} instead;
	 */
	@Deprecated
	public NBTOutputStream(OutputStream os) throws IOException {
		this(os, GZIP);
	}

	/**
	 * Creates a new {@code NBTOutputStream}, which will write data to the
	 * specified underlying output stream.
	 *
	 * @param gzipped Whether the output stream should be GZip-compressed.
	 * @deprecated Use {@link #NBTOutputStream(OutputStream, NBTCompression)} instead;
	 */
	@Deprecated
	public NBTOutputStream(OutputStream os, boolean gzipped) throws IOException {
		this(os, gzipped ? GZIP : UNCOMPRESSED);
	}

	/**
	 * Creates a new {@code NBTOutputStream}, which will write data to the
	 * specified underlying output stream.
	 *
	 * @param compression The type of compression the output stream should use.
	 * @throws IOException if an I/O error occurs.
	 * @since 1.5
	 */
	public NBTOutputStream(OutputStream os, NBTCompression compression) throws IOException {
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

	public void writeTag(Tag tag) throws IOException {
		NBTTagType type      = NBTTagType.fromTagClass(tag.getClass());
		byte[]     nameBytes = tag.getName().getBytes(CHARSET);

		if (type == NBTTagType.TAG_END)
			throw new IOException("[JNBT] Named TAG_End not permitted.");

		os.writeByte(type.getTypeByte());
		os.writeShort(nameBytes.length);
		os.write(nameBytes);

		writeTagPayload(tag);
	}

	private void writeTagPayload(Tag tag) throws IOException {
		NBTTagType type = NBTTagType.fromTagClass(tag.getClass());
		switch (type) {
			case TAG_END:
				writeEndTagPayload((EndTag)tag);
				break;
			case TAG_BYTE:
				writeByteTagPayload((ByteTag)tag);
				break;
			case TAG_SHORT:
				writeShortTagPayload((ShortTag)tag);
				break;
			case TAG_INT:
				writeIntTagPayload((IntTag)tag);
				break;
			case TAG_LONG:
				writeLongTagPayload((LongTag)tag);
				break;
			case TAG_FLOAT:
				writeFloatTagPayload((FloatTag)tag);
				break;
			case TAG_DOUBLE:
				writeDoubleTagPayload((DoubleTag)tag);
				break;
			case TAG_BYTE_ARRAY:
				writeByteArrayTagPayload((ByteArrayTag)tag);
				break;
			case TAG_STRING:
				writeStringTagPayload((StringTag)tag);
				break;
			case TAG_LIST:
				writeListTagPayload((ListTag)tag);
				break;
			case TAG_COMPOUND:
				writeCompoundTagPayload((CompoundTag)tag);
				break;
			case TAG_INT_ARRAY:
				writeIntArrayTagPayload((IntArrayTag)tag);
				break;
			default:
				throw new AssertionError("[JNBT] Unimplemented " + NBTTagType.class.getSimpleName() + ": " + type);
		}
	}

	private void writeByteTagPayload(ByteTag tag) throws IOException {
		os.writeByte(tag.getValue());
	}

	private void writeByteArrayTagPayload(ByteArrayTag tag) throws IOException {
		byte[] bytes = tag.getValue();
		os.writeInt(bytes.length);
		os.write(bytes);
	}

	private void writeCompoundTagPayload(CompoundTag tag) throws IOException {
		for (Tag childTag : tag.getValue().values())
			writeTag(childTag);

		os.writeByte((byte)0); // end tag - better way?
	}

	private void writeListTagPayload(ListTag tag) throws IOException {
		NBTTagType tagType = tag.getType();
		List<Tag>  tags    = tag.getValue();
		int        length  = tags.size();

		os.writeByte(tagType.getTypeByte());
		os.writeInt(length);
		for (Tag t : tags)
			writeTagPayload(t);
	}

	private void writeStringTagPayload(StringTag tag) throws IOException {
		byte[] bytes = tag.getValue().getBytes(CHARSET);
		os.writeShort(bytes.length);
		os.write(bytes);
	}

	private void writeDoubleTagPayload(DoubleTag tag) throws IOException {
		os.writeDouble(tag.getValue());
	}

	private void writeFloatTagPayload(FloatTag tag) throws IOException {
		os.writeFloat(tag.getValue());
	}

	private void writeLongTagPayload(LongTag tag) throws IOException {
		os.writeLong(tag.getValue());
	}

	private void writeIntTagPayload(IntTag tag) throws IOException {
		os.writeInt(tag.getValue());
	}

	private void writeShortTagPayload(ShortTag tag) throws IOException {
		os.writeShort(tag.getValue());
	}

	private void writeIntArrayTagPayload(IntArrayTag tag) throws IOException {
		int[] ints = tag.getValue();
		os.writeInt(ints.length);
		for (int i : ints)
			os.writeInt(i);
	}

	private void writeEndTagPayload(EndTag tag) {
	}

	@Override
	public void close() throws IOException {
		os.close();
	}
}

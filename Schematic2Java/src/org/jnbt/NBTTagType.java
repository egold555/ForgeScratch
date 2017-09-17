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

/**
 * @author Mark Jeronimus
 * @since 1.5
 */
// Created 2016-10-23
public enum NBTTagType {
	TAG_END(0, EndTag.class, "TAG_End"),
	TAG_BYTE(1, ByteTag.class, "TAG_Byte"),
	TAG_SHORT(2, ShortTag.class, "TAG_Short"),
	TAG_INT(3, IntTag.class, "TAG_Int"),
	TAG_LONG(4, LongTag.class, "TAG_Long"),
	TAG_FLOAT(5, FloatTag.class, "TAG_Float"),
	TAG_DOUBLE(6, DoubleTag.class, "TAG_Double"),
	TAG_BYTE_ARRAY(7, ByteArrayTag.class, "TAG_Byte_Array"),
	TAG_STRING(8, StringTag.class, "TAG_String"),
	TAG_LIST(9, ListTag.class, "TAG_List"),
	TAG_COMPOUND(10, CompoundTag.class, "TAG_Compound"),
	TAG_INT_ARRAY(11, IntArrayTag.class, "TAG_Int_Array");

	private final int                  typeByte;
	private final Class<? extends Tag> tagClass;
	private final String               mojangName;

	NBTTagType(int typeByte, Class<? extends Tag> tagClass, String mojangName) {
		this.typeByte = typeByte;
		this.tagClass = tagClass;
		this.mojangName = mojangName;
	}

	public int getTypeByte() {
		return typeByte;
	}

	public Class<? extends Tag> getTagClass() {
		return tagClass;
	}

	public String getMojangName() {
		return mojangName;
	}

	public static NBTTagType fromTypeByte(int typeByte) {
		for (NBTTagType value : values())
			if (value.typeByte == typeByte)
				return value;
		throw new IllegalArgumentException("[JNBT] No " + NBTCompression.class.getSimpleName()
		                                   + " enum constant with typeByte: " + typeByte);
	}

	public static NBTTagType fromTagClass(Class<? extends Tag> tagClass) {
		for (NBTTagType value : values())
			if (value.tagClass == tagClass)
				return value;
		throw new IllegalArgumentException("[JNBT] No " + NBTCompression.class.getSimpleName()
		                                   + " enum constant with tagClass: " + tagClass);
	}

	public static NBTTagType fromMojangName(String mojangName) {
		for (NBTTagType value : values())
			if (value.mojangName.equals(mojangName))
				return value;
		throw new IllegalArgumentException("[JNBT] No " + NBTCompression.class.getSimpleName()
		                                   + " enum constant with mojangName: " + mojangName);
	}
}

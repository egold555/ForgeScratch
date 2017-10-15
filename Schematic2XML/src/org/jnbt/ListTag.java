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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@code TAG_List} tag.
 *
 * @author Graham Edgecombe
 */

public final class ListTag extends Tag {
	/**
	 * @since 1.6
	 */
	public static final int DEFAULT_INITIAL_CAPACITY = 4;

	private static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");

	/**
	 * The type of items in this list.
	 */
	private final NBTTagType type;
	private final List<Tag>  value;

	public ListTag(String name, NBTTagType type, List<Tag> value) {
		super(name);
		this.type = type;
		this.value = value;
	}

	/**
	 * @since 1.6
	 */
	public ListTag(String name, NBTTagType type) {
		this(name, type, new ArrayList<>(DEFAULT_INITIAL_CAPACITY));
	}

	/**
	 * Returns the type of items in this list.
	 */
	public NBTTagType getType() {
		return type;
	}

	@Override
	public List<Tag> getValue() {
		return value;
	}

	/**
	 * Returns the size of the list.
	 *
	 * @since 1.6
	 */
	public int size() {
		return value.size();
	}

	/**
	 * Returns the element at index.
	 *
	 * @since 1.6
	 */
	public Tag get(int index) {
		return value.get(index);
	}

	/**
	 * @since 1.6
	 */
	public void addTag(Tag tag) {
		value.add(tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ListTag)) return false;
		if (!super.equals(obj)) return false;
		ListTag listTag = (ListTag)obj;
		return Objects.equals(type, listTag.type) &&
		       Objects.equals(value, listTag.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), type, value);
	}

	@Override
	public String toString() {
		String joinedTags = value.stream()
		                         .map(Tag::toString)
		                         .map(s -> NEWLINE_PATTERN.matcher(s).replaceAll("\n   "))
		                         .collect(Collectors.joining("\n", "{\n", "\n}"));
		return getTagPrefixedToString(type.getMojangName(), joinedTags);
	}
}

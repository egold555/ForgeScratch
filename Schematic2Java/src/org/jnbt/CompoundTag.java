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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@code TAG_Compound} tag.
 *
 * @author Graham Edgecombe
 */

public final class CompoundTag extends Tag {
	/**
	 * @since 1.6
	 */
	public static final int DEFAULT_INITIAL_CAPACITY = 32;

	private static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");

	private final Map<String, Tag> value;

	public CompoundTag(String name, Map<String, Tag> value) {
		super(name);
		this.value = value;
	}

	/**
	 * @since 1.6
	 */
	public CompoundTag(String name) {
		this(name, new HashMap<>(DEFAULT_INITIAL_CAPACITY));
	}

	@Override
	public Map<String, Tag> getValue() {
		return value;
	}

	/**
	 * @since 1.6
	 */
	public void addTag(Tag tag) {
		value.put(tag.getName(), tag);
	}

	/**
	 * @since 1.6
	 */
	public Tag getTag(String key) {
		return value.get(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof CompoundTag)) return false;
		if (!super.equals(obj)) return false;
		CompoundTag compoundTag = (CompoundTag)obj;
		return Objects.equals(value, compoundTag.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), value);
	}

	@Override
	public String toString() {
		String joinedTags = value.values().stream()
		                         .map(Tag::toString)
		                         .map(s -> NEWLINE_PATTERN.matcher(s).replaceAll("\n   "))
		                         .collect(Collectors.joining("\n", "{\n", "\n}"));
		return getTagPrefixedToString(joinedTags);
	}
}

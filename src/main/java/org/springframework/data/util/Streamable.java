/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.util.Assert;

/**
 * Simple interface to ease streamability of {@link Iterable}s.
 * 
 * @author Oliver Gierke
 * @author Christoph Strobl
 */
public interface Streamable<T> extends Iterable<T> {

	/**
	 * Creates a non-parallel {@link Stream} of the underlying {@link Iterable}.
	 * 
	 * @return will never be {@literal null}.
	 */
	default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Returns an empty {@link Streamable}.
	 * 
	 * @return will never be {@literal null}.
	 */
	static <T> Streamable<T> empty() {
		return Collections::emptyIterator;
	}

	/**
	 * Returns a {@link Streamable} with the given elements.
	 * 
	 * @param t the elements to return.
	 * @return
	 */
	@SafeVarargs
	static <T> Streamable<T> of(T... t) {
		return () -> Arrays.asList(t).iterator();
	}

	/**
	 * Returns a {@link Streamable} for the given {@link Iterable}.
	 * 
	 * @param iterable must not be {@literal null}.
	 * @return
	 */
	static <T> Streamable<T> of(Iterable<T> iterable) {

		Assert.notNull(iterable, "Iterable must not be null!");

		return iterable::iterator;
	}
}

/*
 * (C) Copyright 2016 Pantheon Technologies, s.r.o. and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opendaylight.yangtools.triemap;

final class FailedNode<K, V> extends MainNode<K, V> {
    private final MainNode<K, V> p;

    FailedNode(final MainNode<K, V> p) {
        super(p);
        this.p = p;
    }

    @Override
    int trySize() {
        throw new UnsupportedOperationException();
    }

    @Override
    int size(final ImmutableTrieMap<?, ?> ct) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "FailedNode(" + p + ")";
    }

}
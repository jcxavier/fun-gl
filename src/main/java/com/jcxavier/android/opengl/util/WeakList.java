package com.jcxavier.android.opengl.util;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class WeakList<T> extends AbstractList<T> {

    private final List<WeakReference<T>> items;

    public WeakList() {
        items = new ArrayList<>();
    }

    public WeakList(final Collection<T> collection) {
        items = new ArrayList<>();
        addAll(collection);
    }

    @Override
    public void add(final int location, final T object) {
        items.add(location, new WeakReference<>(object));
    }

    @Override
    public Iterator<T> iterator() {
        return new WeakListIterator();
    }

    @Override
    public int size() {
        removeReleased();
        return items.size();
    }

    @Override
    public T get(final int location) {
        return items.get(location).get();
    }

    private void removeReleased() {
        for (final WeakReference<T> item : items) {
            WeakReference ref = (WeakReference) item;
            if (ref.get() == null) {
                items.remove(ref);
            }
        }
    }

    private class WeakListIterator implements Iterator<T> {
        private final int n;
        private int i;

        public WeakListIterator() {
            n = size();
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public T next() {
            return get(i++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
package org.example.collection;

import org.example.X;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharSequenceIterator implements Iterator<Character> {
    private final CharSequence seq;
    private final int length;
    private int index;

    public CharSequenceIterator(CharSequence seq) {
        this.seq = seq;
        length = seq.length();
    }

    @Override
    public boolean hasNext() {
        return index < length;
    }

    @Override
    public Character next() {
        X.assertTrue(hasNext(), NoSuchElementException::new);
        return seq.charAt(index++);
    }
}
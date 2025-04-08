package org.example;

public interface Deque<E> extends Queue<E> {
    boolean offerFirst(E e);

    boolean offerLast(E e);

    E peekFirst();

    E peekLast();

    E pollFirst();

    E pollLast();
}

package org.example;

public interface PriorityQueue<E> extends Queue<E> {
    Comparator<? super E> comparator();
}

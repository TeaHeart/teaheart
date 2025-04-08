package org.example;

public interface List<E> extends Collection<E> {
    boolean insert(int index, E e);

    E get(int index);

    E set(int index, E e);

    E removeAt(int index);

    int indexOf(E e);
}

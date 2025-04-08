package org.example;

public interface Stack<E> extends Collection<E> {
    boolean push(E e);

    E peek();

    E pop();
}

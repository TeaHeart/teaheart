package org.example.core;

public interface Cloneable<T extends Cloneable<T>> extends java.lang.Cloneable {
    T clone();
}
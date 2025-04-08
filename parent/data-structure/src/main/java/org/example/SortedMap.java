package org.example;

public interface SortedMap<K, V> extends Map<K, V> {
    Comparator<? super K> comparator();

    K firstKey();

    K lastKey();
}

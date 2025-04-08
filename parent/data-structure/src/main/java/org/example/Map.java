package org.example;

public interface Map<K, V> {
    int size();

    boolean isEmpty();

    void clear();

    V put(K key, V value);

    V get(K key);

    V replace(K key, V value);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    Iterable<K> keys();

    Iterable<V> values();

    Iterable<Entry<K, V>> entries();

    interface Entry<K, V> {
        K getKey();

        K setKey(K key);

        V getValue();

        V setValue(V value);
    }
}

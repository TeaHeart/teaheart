package org.example;

public class TreeSet<E> implements SortedSet<E> {
    private static final Object PRESENT = new Object();
    private final SortedMap<E, Object> map;

    public TreeSet(Comparator<? super E> comparator) {
        map = new TreeMap<>(comparator);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) != PRESENT;
    }

    @Override
    public boolean contains(E e) {
        return map.containsKey(e);
    }

    @Override
    public boolean remove(E e) {
        return map.remove(e) == PRESENT;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keys().iterator();
    }

    @Override
    public Comparator<? super E> comparator() {
        return map.comparator();
    }

    @Override
    public E first() {
        return map.firstKey();
    }

    @Override
    public E last() {
        return map.lastKey();
    }
}

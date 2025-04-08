package org.example;

public class HashSet<E> implements Set<E> {
    private static final Object PRESENT = new Object();
    private final Map<E, Object> map = new HashMap<>();

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
}

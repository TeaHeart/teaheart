package org.example;

public class HashMap<K, V> implements Map<K, V> {
    private final Iterable<K> keys = new Iterable<K>() {
        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {
                final NodeIterator it = new NodeIterator();

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public K next() {
                    return it.next().key;
                }
            };
        }
    };
    private final Iterable<V> values = new Iterable<V>() {
        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {
                final NodeIterator it = new NodeIterator();

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public V next() {
                    return it.next().value;
                }
            };
        }
    };
    private final Iterable<Entry<K, V>> entries = new Iterable<Entry<K, V>>() {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new NodeIterator();
        }
    };
    private Node<K, V>[] table = new Node[2];
    private int size;

    private static int hash(Object o) {
        int hash = o != null ? o.hashCode() : 0;
        return hash ^ (hash >>> 16);
    }

    private static <K, V> boolean keyEquals(int hash, K key, Node<K, V> node) {
        return node != null && hash == node.hash && (key == node.key || key != null && key.equals(node.key));
    }

    private static <K, V> Node<K, V> getNode(int hash, K key, Node<K, V> curr) {
        for (; curr != null; curr = curr.next) {
            if (keyEquals(hash, key, curr)) {
                return curr;
            }
        }
        return null;
    }

    private static <K, V> V unlinkNode(Node<K, V> prev, Node<K, V> curr) {
        if (prev != null) {
            prev.next = curr.next;
        }
        V value = curr.setValue(null);
        curr.next = null;
        return value;
    }

    private void grow() {
        if (size == (int) (table.length * 0.75f)) {
            Node<K, V>[] oleTable = table;
            table = new Node[table.length << 1];
            for (Node<K, V> curr : oleTable) {
                while (curr != null) {
                    Node<K, V> next = curr.next;
                    int slot = slot(curr.hash);
                    curr.next = table[slot];
                    table[slot] = curr;
                    curr = next;
                }
            }
        }
    }

    private int slot(int hash) {
        return hash & (table.length - 1);
    }

    private Node<K, V> getNode(K key) {
        int hash = hash(key);
        return getNode(hash, key, table[slot(hash)]);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> curr = table[i];
            while (curr != null) {
                Node<K, V> next = curr.next;
                unlinkNode(null, curr);
                curr = next;
            }
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        grow();
        int hash = hash(key);
        int slot = slot(hash);
        Node<K, V> node = getNode(hash, key, table[slot]);
        if (node != null) {
            return node.setValue(value);
        }
        table[slot] = new Node<>(hash, key, value, table[slot]);
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = getNode(key);
        return node != null ? node.value : null;
    }

    @Override
    public V replace(K key, V value) {
        Node<K, V> node = getNode(key);
        return node != null ? node.setValue(value) : null;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        int slot = slot(hash);
        Node<K, V> dummy = new Node<>(table[slot]);
        Node<K, V> prev = dummy;
        Node<K, V> curr = dummy.next;
        while (curr != null) {
            if (keyEquals(hash, key, curr)) {
                V value = unlinkNode(prev, curr);
                size--;
                table[slot] = dummy.next;
                return value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (Iterator<V> it = values.iterator(); it.hasNext(); ) {
            V v = it.next();
            if (value == v || value != null && value.equals(v)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<K> keys() {
        return keys;
    }

    @Override
    public Iterable<V> values() {
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return entries;
    }

    private static class Node<K, V> implements Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(Node<K, V> next) {
            this(0, null, null, next);
        }

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public K setKey(K key) {
            return null;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V v = this.value;
            this.value = value;
            return v;
        }
    }

    private class NodeIterator implements Iterator<Entry<K, V>> {
        private Node<K, V> next = nextSlot();
        private int slot;

        private Node<K, V> nextSlot() {
            while (slot < table.length) {
                if (table[slot] != null) {
                    return table[slot];
                }
                slot++;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node<K, V> next() {
            Node<K, V> curr = next;
            next = next.next;
            if (next == null) {
                slot++;
                next = nextSlot();
            }
            return curr;
        }
    }
}

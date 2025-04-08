package org.example;

public class TreeMap<K, V> implements SortedMap<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
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
    private final Comparator<? super K> comparator;
    private TreeNode<K, V> root;
    private int size;

    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    private static void flipColor(TreeNode<?, ?> curr) {
        curr.color = RED;
        curr.left.color = BLACK;
        curr.right.color = BLACK;
    }

    private static boolean isColor(TreeNode<?, ?> curr, boolean color) {
        return curr != null && curr.color == color;
    }

    private static <K, V> TreeNode<K, V> getNextNode(TreeNode<K, V> curr) {
        if (curr == null) {
            return null;
        } else if (curr.right != null) {
            curr = curr.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
        TreeNode<K, V> parent = curr.parent;
        while (parent != null && curr == parent.right) {
            curr = parent;
            parent = parent.parent;
        }
        return parent;
    }

    private TreeNode<K, V> getFirst() {
        TreeNode<K, V> curr = root;
        if (curr != null) {
            while (curr.left != null) {
                curr = curr.left;
            }
        }
        return curr;
    }

    private TreeNode<K, V> getLast() {
        TreeNode<K, V> curr = root;
        if (curr != null) {
            while (curr.right != null) {
                curr = curr.right;
            }
        }
        return curr;
    }

    private TreeNode<K, V> getNode(K key) {
        TreeNode<K, V> curr = root;
        while (curr != null) {
            int cmp = comparator.compare(key, curr.key);
            if (cmp < 0) {
                curr = curr.left;
            } else if (cmp > 0) {
                curr = curr.right;
            } else {
                return curr;
            }
        }
        return null;
    }

    private void unlinkNode(TreeNode<K, V> curr, TreeNode<K, V> replace) {
        relink(curr, replace);
        curr.key = null;
        curr.value = null;
        curr.left = curr.right = curr.parent = null;
    }

    private TreeNode<K, V> relink(TreeNode<K, V> curr, TreeNode<K, V> replace) {
        if (replace != null) {
            replace.parent = curr.parent;
        }
        if (curr.parent == null) {
            root = replace;
        } else if (curr.parent.left == curr) {
            curr.parent.left = replace;
        } else {
            curr.parent.right = replace;
        }
        curr.parent = replace;
        return replace;
    }

    private TreeNode<K, V> rotateLeft(TreeNode<K, V> curr) {
        TreeNode<K, V> right = curr.right;
        curr.right = right.left;
        if (right.left != null) {
            right.left.parent = curr;
        }
        right.left = curr;
        right.color = curr.color;
        curr.color = RED;
        return relink(curr, right);
    }

    private TreeNode<K, V> rotateRight(TreeNode<K, V> curr) {
        TreeNode<K, V> left = curr.left;
        curr.left = left.right;
        if (left.right != null) {
            left.right.parent = curr;
        }
        left.right = curr;
        left.color = curr.color;
        curr.color = RED;
        return relink(curr, left);
    }

    private void fixBalance(TreeNode<K, V> curr, boolean isAdd) {
        if (isAdd) {
            while (curr != null) {
                if (isColor(curr, BLACK) && isColor(curr.right, RED)) {
                    curr = rotateLeft(curr);
                }
                if (isColor(curr.left, RED) && isColor(curr.left.left, RED)) {
                    curr = rotateRight(curr);
                }
                if (isColor(curr.left, RED) && isColor(curr.right, RED)) {
                    flipColor(curr);
                }
                curr = curr.parent;
            }
        } else {
            // TODO
        }
        root.color = BLACK;
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
        TreeNode<K, V> next;
        for (TreeNode<K, V> curr = getFirst(); curr != null; curr = next) {
            next = getNextNode(curr);
            unlinkNode(curr, null);
        }
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        TreeNode<K, V> curr = root;
        TreeNode<K, V> parent = null;
        int cmp = 0;
        while (curr != null) {
            parent = curr;
            cmp = comparator.compare(key, curr.key);
            if (cmp < 0) {
                curr = curr.left;
            } else if (cmp > 0) {
                curr = curr.right;
            } else {
                return curr.setValue(value);
            }
        }
        curr = new TreeNode<>(key, value, parent);
        if (cmp < 0) {
            parent.left = curr;
        } else if (cmp > 0) {
            parent.right = curr;
        } else {
            root = curr;
        }
        size++;
        fixBalance(parent, true);
        return null;
    }

    @Override
    public V get(K key) {
        TreeNode<K, V> node = getNode(key);
        return node != null ? node.value : null;
    }

    @Override
    public V replace(K key, V value) {
        TreeNode<K, V> node = getNode(key);
        return node != null ? node.setValue(value) : null;
    }

    @Override
    public V remove(K key) {
        TreeNode<K, V> curr = getNode(key);
        if (curr == null) {
            return null;
        }
        V value = curr.value;
        if (curr.left != null && curr.right != null) {
            TreeNode<K, V> next = getNextNode(curr);
            curr.key = next.key;
            curr.value = next.value;
            curr = next;
        }
        TreeNode<K, V> replace = curr.left != null ? curr.left : curr.right;
        if (replace != null) {
            unlinkNode(curr, replace);
            if (isColor(curr, BLACK)) {
                fixBalance(replace, false);
            }
        } else if (curr.parent == null) {
            unlinkNode(root, null);
        } else {
            if (isColor(curr, BLACK)) {
                fixBalance(curr, false);
            }
            unlinkNode(curr, null);
        }
        size--;
        return value;
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

    @Override
    public Comparator<? super K> comparator() {
        return comparator;
    }

    @Override
    public K firstKey() {
        return getFirst().key;
    }

    @Override
    public K lastKey() {
        return getLast().key;
    }

    private static class TreeNode<K, V> implements Entry<K, V> {
        private K key;
        private V value;
        private TreeNode<K, V> left;
        private TreeNode<K, V> right;
        private TreeNode<K, V> parent;
        private boolean color = RED;

        public TreeNode(K key, V value, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public K setKey(K key) {
            K k = this.key;
            this.key = key;
            return k;
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
        private TreeNode<K, V> next = getFirst();

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public TreeNode<K, V> next() {
            TreeNode<K, V> curr = next;
            next = getNextNode(next);
            return curr;
        }
    }
}

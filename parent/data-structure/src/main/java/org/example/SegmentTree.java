package org.example;

public class SegmentTree {
    public final static int ROOT = 1;
    private final static int FROM = 0, TO = 1, VALUE = 2, DELTA = 3;
    private final int[][] table;

    public SegmentTree(int from, int to) {
        table = new int[(to - from) << 2][];
        build(ROOT, from, to);
    }

    private void build(int parent, int from, int to) {
        int[] root = table[parent] = new int[4];
        root[FROM] = from;
        root[TO] = to;
        if (to - from <= 1) {
            return;
        }
        int middle = (from + to) >>> 1;
        int left = parent << 1;
        int right = left | 1;
        build(left, from, middle);
        build(right, middle, to);
    }

    public void addRange(int parent, int from, int to, int value) {
        int[] root = table[parent];
        if (from >= root[TO] || to <= root[FROM]) {
            return;
        }
        if (from <= root[FROM] && root[TO] <= to) {
            root[DELTA] += value;
            lazyDown(parent);
            return;
        }
        lazyDown(parent);
        int left = parent << 1;
        int right = left | 1;
        addRange(left, from, to, value);
        addRange(right, from, to, value);
        root[VALUE] = table[left][VALUE] + table[right][VALUE];
    }

    public int query(int parent, int from, int to) {
        int[] root = table[parent];
        if (from >= root[TO] || to <= root[FROM]) {
            return 0;
        }
        if (from <= root[FROM] && root[TO] <= to) {
            return root[VALUE];
        }
        lazyDown(parent);
        int left = parent << 1;
        int right = left | 1;
        return query(left, from, to) + query(right, from, to);
    }

    private void lazyDown(int parent) {
        int[] root = table[parent];
        if (root[DELTA] != 0) {
            root[VALUE] += root[DELTA] * (root[TO] - root[FROM]);
            int left = parent << 1;
            int right = left | 1;
            table[left][DELTA] += root[DELTA];
            table[right][DELTA] += root[DELTA];
            root[DELTA] = 0;
        }
    }
}

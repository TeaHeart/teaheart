package org.example;

public class UnionSet {
    private final int[] table;

    public UnionSet(int size) {
        table = new int[size];
        for (int i = 0; i < size; i++) {
            table[i] = -1;
        }
    }

    int size(int x) {
        return -table[find(x)];
    }

    int find(int x) {
        int root = x;
        while (table[root] >= 0) {
            root = table[root];
        }
        while (table[x] >= 0) {
            int parent = table[x];
            table[x] = root;
            x = parent;
        }
        return root;
    }

    void union(int x, int y) {
        if (table[x] >= 0) {
            x = find(x);
        }
        if (table[y] >= 0) {
            y = find(y);
        }
        if (x == y) {
            return;
        }
        if (table[x] <= table[y]) {
            table[x] += table[y];
            table[y] = x;
        } else {
            table[y] += table[x];
            table[x] = y;
        }
    }
}

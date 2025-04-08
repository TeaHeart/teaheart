package org.example.core;

public interface MathX {
    static int ceilDiv(int x, int y) {
        return (x + y - 1) / y;
    }

    static int clamp(int value, int min, int max) {
        return value <= min ? min : Math.min(value, max);
    }

    static int multipleOfTwo(int x) {
        for (int i = 1; i <= 1 << 4; i <<= 1) {
            x |= x >>> i;
        }
        return ++x < 0 ? x >>> 1 : x;
    }
}
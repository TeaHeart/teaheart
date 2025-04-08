package org.example.collection;

import org.example.X;
import org.example.function.Func1;
import org.example.function.FuncX;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public interface ArrayX {
    int INDEX_NOT_FOUND = -1;

    @SafeVarargs
    static <T> T[] asArray(T... args) {
        return args;
    }

    static <T> T get(Object array, int index) {
        return X.cast(Array.get(array, index));
    }

    static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    static <T> Class<T> getComponentType(Object array) {
        X.assertTrue(isArray(array));
        return X.cast(array.getClass().getComponentType());
    }

    static String getComponentName(Object array) {
        return getComponentType(array).getSimpleName();
    }

    static <A> Class<A> getArrayType(Class<?> componentType) {
        return X.cast(newArray(componentType, 0).getClass());
    }

    static <A> A newArray(Class<?> componentType, int length) {
        return X.cast(Array.newInstance(componentType, length));
    }

    static <A> A wrap(Object array) {
        return copyOf(array, Array.getLength(array), X.getWrapperClass(getComponentName(array)));
    }

    static <A> A unwrap(Object array) {
        return copyOf(array, Array.getLength(array), X.getPrimitiveClass(getComponentName(array)));
    }

    static <A> A copyOf(Object array, int newLength) {
        return copyOf(array, newLength, getComponentType(array));
    }

    static <A> A copyOf(Object array, int newLength, Class<?> componentType) {
        return copyOfRange(array, 0, newLength, componentType);
    }

    static <A> A copyOfRange(Object array, int from, int to) {
        return copyOfRange(array, from, to, getComponentType(array));
    }

    static <A> A copyOfRange(Object array, int from, int to, Class<?> componentType) {
        int length = Array.getLength(array);
        int newLength = to - from;
        X.assertTrue(newLength >= 0);
        A dest = newArray(componentType, newLength);
        arraycopy(array, from, dest, 0, Math.min(length - from, newLength));
        return dest;
    }

    static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
        int srcLength = Array.getLength(src);
        int destLength = Array.getLength(dest);
        X.assertTrue(isAscend(0, srcPos, srcPos + length, srcLength));
        X.assertTrue(isAscend(0, destPos, destPos + length, destLength));
        Object def = X.defaultOf(getComponentName(dest));
        for (int i = 0; i < length; i++) {
            Array.set(dest, i + destPos, X.defaultIfNull(get(src, i + srcPos), def));
        }
    }

    static <T> boolean isAscend(Object array, Comparator<? super T> ctor) {
        int length = Array.getLength(array);
        for (int i = 1; i < length; i++) {
            if (ctor.compare(get(array, i - 1), get(array, i)) > 0) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    static <T extends Comparable<T>> boolean isAscend(T... args) {
        return isAscend(args, T::compareTo);
    }

    static <T> boolean isDescend(Object array, Comparator<? super T> ctor) {
        int length = Array.getLength(array);
        for (int i = 1; i < length; i++) {
            if (ctor.compare(get(array, i - 1), get(array, i)) < 0) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    static <T extends Comparable<T>> boolean isDescend(T... args) {
        return isDescend(args, T::compareTo);
    }

    static void swap(Object array, int i, int j) {
        Object tmp = get(array, i);
        Array.set(array, i, get(array, j));
        Array.set(array, j, tmp);
    }

    static void reverse(Object array) {
        reverse(array, 0, Array.getLength(array));
    }

    static void reverse(Object array, int from, int to) {
        X.assertTrue(isAscend(0, from, to, Array.getLength(array)));
        while (from < to) {
            swap(array, from++, --to);
        }
    }

    static <T> T min(Object array, Comparator<? super T> ctor) {
        int length = Array.getLength(array);
        T min = get(array, 0);
        for (int i = 1; i < length; i++) {
            T obj = get(array, i);
            if (ctor.compare(obj, min) < 0) {
                min = obj;
            }
        }
        return min;
    }

    static <T extends Comparable<T>> T min(Object array) {
        return min(array, T::compareTo);
    }

    static <T> T max(Object array, Comparator<? super T> ctor) {
        int length = Array.getLength(array);
        T max = get(array, 0);
        for (int i = 1; i < length; i++) {
            T obj = get(array, i);
            if (ctor.compare(obj, max) > 0) {
                max = obj;
            }
        }
        return max;
    }

    static <T extends Comparable<T>> T max(Object array) {
        return max(array, T::compareTo);
    }

    static <A> A append(Object array, Object... args) {
        return insert(array, Array.getLength(array), args);
    }

    static <A> A insert(Object array, int index, Object... args) {
        X.assertTrue(index >= 0);
        A dest = copyOf(array, Math.min(index, Array.getLength(array)) + args.length);
        arraycopy(args, 0, dest, index, args.length);
        return dest;
    }

    static String join(Object array, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        int length = Array.getLength(array);
        StringJoiner sj = new StringJoiner(delimiter, prefix, suffix);
        for (int i = 0; i < length; i++) {
            sj.add(String.valueOf(get(array, i)));
        }
        return sj.toString();
    }

    static String join(Object array, CharSequence delimiter) {
        return join(array, delimiter, "", "");
    }

    static <T> boolean contains(Object array, T obj) {
        return indexOf(array, obj) > INDEX_NOT_FOUND;
    }

    static <T> int indexOf(Object array, Func1<T, Boolean> predicate) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            if (FuncX.sneaky(predicate, get(array, i))) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    static <T> int indexOf(Object array, T obj) {
        return indexOf(array, FuncX.of(Objects::equals, obj));
    }

    static <T> int lastIndexOf(Object array, Func1<T, Boolean> predicate) {
        int length = Array.getLength(array);
        for (int i = length - 1; i >= 0; i--) {
            if (FuncX.sneaky(predicate, get(array, i))) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    static <T> int lastIndexOf(Object array, T obj) {
        return lastIndexOf(array, FuncX.of(Objects::equals, obj));
    }

    static <T> int lowerBound(Object array, int left, int right, T target, Comparator<? super T> ctor) {
        X.assertTrue(isAscend(0, left, right, Array.getLength(array)));
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (ctor.compare(X.cast(get(array, mid)), target) < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    static <T> int lowerBound(Object array, T target, Comparator<? super T> ctor) {
        return lowerBound(array, 0, Array.getLength(array), target, ctor);
    }

    static <T extends Comparable<T>> int lowerBound(Object array, T target) {
        return lowerBound(array, 0, Array.getLength(array), target, T::compareTo);
    }

    static <T> int upperBound(Object array, int left, int right, T target, Comparator<? super T> ctor) {
        X.assertTrue(isAscend(0, left, right, Array.getLength(array)));
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (ctor.compare(X.cast(get(array, mid)), target) <= 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right;
    }

    static <T> int upperBound(Object array, T target, Comparator<? super T> ctor) {
        return upperBound(array, 0, Array.getLength(array), target, ctor);
    }

    static <T extends Comparable<T>> int upperBound(Object array, T target) {
        return upperBound(array, 0, Array.getLength(array), target, T::compareTo);
    }
}
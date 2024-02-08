package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ArrayUtils {
    @SafeVarargs
    public static <T> @NotNull T @NotNull [] filterNotNull(final T @NotNull... src) {
        @SuppressWarnings("unchecked")
        final Class<T> componentType = (Class<T>) src.getClass().getComponentType();
        final T[] notNull = newArrayOf(componentType, src.length);
        int i = 0;
        for (T e : src) if (e != null) notNull[i++] = e;
        final T[] dst = newArrayOf(componentType, i);
        System.arraycopy(notNull, 0, dst, 0, i);
        return dst;
    }

    @SafeVarargs
    public static <T> @NotNull T @NotNull [] merge(final @NotNull T[] first, final @NotNull T... second) {
        if (first == null) {
            return clone(second);
        } else if (second == null) {
            return clone(first);
        } else {
            final T[] dst = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, dst, first.length, second.length);
            return dst;
        }
    }

    @SafeVarargs
    public static <T> @NotNull T @NotNull [] mergeArraysIfAbsent(final @NotNull T[] first, final @NotNull T... second) {
        if (first == null) {
            return clone(second);
        } else if (second == null) {
            return clone(first);
        } else {
            final Set<T> set = new LinkedHashSet<>(first.length + second.length);
            set.addAll(Arrays.asList(first));
            set.addAll(Arrays.asList(second));
            @SuppressWarnings("unchecked")
            final Class<T> componentType = (Class<T>) first.getClass().getComponentType();
            final T[] dst = newArrayOf(componentType, set.size());
            int i = 0;
            for (T e : set) dst[i++] = e;
            return dst;
        }
    }

    @SafeVarargs
    public static <T> @NotNull T @NotNull [] flatMap(final @NotNull T @NotNull [] @NotNull ... src) {
        @SuppressWarnings("unchecked")
        final Class<T> dstComponentType = (Class<T>) src.getClass().getComponentType().getComponentType();
        final int srcLength = src.length;
        if (srcLength < 1) return newArrayOf(dstComponentType, 0);
        int totalLength = 0;
        final int[] lengths = new int[srcLength];
        for (int i = 0; i < srcLength; i++) {
            final T[] e = src[i];
            final int length = e.length;
            totalLength += length;
            lengths[i] = length;
        }
        final T[] dst = newArrayOf(dstComponentType, totalLength);
        int written = 0;
        for (int i = 0; i < srcLength; i++) {
            final T[] array = src[i];
            final int length = lengths[i];
            if (length > 0) {
                System.arraycopy(array, 0, dst, written, length);
                written += length;
            }
        }
        return dst;
    }

    @SafeVarargs
    public static <T> @NotNull T @NotNull [] reverse(@NotNull T @NotNull... src) {
        @SuppressWarnings("unchecked")
        final Class<T> componentType = (Class<T>) src.getClass().getComponentType();
        final T[] dst = newArrayOf(componentType, src.length);
        int sourceSize = src.length - 1;
        for (int i = 0; i < src.length; i++) dst[i] = src[sourceSize - i];
        return dst;
    }

    @SuppressWarnings("all")
    private static <T> T[] newArrayOf(Class<T> clazz, int length) {
        return ((Object)clazz == (Object)Object[].class)
            ? (T[]) new Object[length]
            : (T[]) Array.newInstance(clazz, length);
    }

    private static <T> T[] clone(T[] array) {
        return array == null ? null : array.clone();
    }
}

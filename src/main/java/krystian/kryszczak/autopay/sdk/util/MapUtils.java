package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class MapUtils {
    public static @NotNull Map<@NotNull String, @NotNull String> notNullMapOf(@Nullable Object... input) {
        if (input == null) return Map.of();
        if (input.length % 2 != 0) throw new InvalidParameterException();

        final Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < input.length / 2; i += 2) {
            final Object value = input[i + 1];

            if (value == null) continue;

            result.put(
                String.valueOf(Objects.requireNonNull(input[i])),
                String.valueOf(value)
            );
        }
        return result;
    }

    @SafeVarargs
    public static <K, V> @NotNull Map<K, V> merge(final @NotNull Map<K, V> @NotNull... src) {
        final Map<K, V> dst = new LinkedHashMap<>();
        for (Map<K, V> map : src) {
            dst.putAll(map);
        }
        return dst;
    }

    @SafeVarargs
    public static <K, V> @NotNull Map<K, V> mergeIfAbsent(final @NotNull Map<K, V> @NotNull... src) {
        final Map<K, V> dst = new LinkedHashMap<>();
        for (Map<K, V> map : src) map.forEach(dst::putIfAbsent);
        return dst;
    }

    public static <V> @NotNull Map<String, V> capitalizeMap(@NotNull Map<String, V> src) {
        final Map<String, V> dst = new LinkedHashMap<>();
        src.forEach((key, value) -> dst.put(StringUtils.capitalize(key), value));
        return dst;
    }
}

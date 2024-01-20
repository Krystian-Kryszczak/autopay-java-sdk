package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CollectionsUtils {
    public static @NotNull Object[] filterNonNull(final Object[] array) {
        return Arrays.stream(Objects.requireNonNull(array)).filter(Objects::nonNull).toArray();
    }

    public static @NotNull Map<@NotNull String, String> nonNullMapFromArray(Object... input) {
        if (input.length % 2 != 0)
            throw new InvalidParameterException();

        final var result = new HashMap<String, String>();
        for (int i = 0; i < input.length / 2; i += 2) {
            final var value = input[i + 1];

            if (value == null)
                continue;

            result.put(
                String.valueOf(Objects.requireNonNull(input[i])),
                String.valueOf(value)
            );
        }
        return result;
    }
}

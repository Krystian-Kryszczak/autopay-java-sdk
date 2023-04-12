package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

final class HashGeneratorImpl implements HashGenerator {

    @Override
    public @NotNull String generateHash(@NotNull Object[] array, @NotNull BlueMediaConfiguration credentials) {
        final StringBuilder builder = new StringBuilder();

        for (Object obj : array) {
            builder
                .append(obj)
                .append(credentials.hashSeparator());
        }

        builder.append(credentials.sharedKey());

        return builder.toString();
    }
}

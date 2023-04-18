package krystian.kryszczak.bm.sdk.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static krystian.kryszczak.bm.sdk.util.StringUtils.bin2hex;

public final class RandomUtils {
    public static final int MESSAGE_ID_LENGTH = 32;

    public static @NotNull String randomMessageId() {
        return bin2hex(new String(randomBytes()));
    }

    private static byte @NotNull [] randomBytes() {
        final var bytes = new byte[MESSAGE_ID_LENGTH/2];

        new Random()
            .nextBytes(bytes);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.abs(bytes[i]));
        }

        return bytes;
    }
}

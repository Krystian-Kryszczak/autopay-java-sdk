package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

import static krystian.kryszczak.autopay.sdk.util.StringUtils.bin2hex;

public final class RandomUtils {
    static final int MESSAGE_ID_LENGTH = 32;

    public static @NotNull String randomMessageId() {
        return bin2hex(new String(randomBytes()));
    }

    private static byte @NotNull [] randomBytes() {
        final var bytes = new byte[MESSAGE_ID_LENGTH/2];

        new SecureRandom()
            .nextBytes(bytes);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.abs(bytes[i]));
        }

        return bytes;
    }
}

package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * Convert binary data into hexadecimal representation
     */
    public static @NotNull String bin2hex(@NotNull String binary) {
        final byte[] bytes = binary.getBytes(StandardCharsets.US_ASCII);

        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Decodes a hexadecimal encoded binary string
     */
    public static @Nullable String hex2bin(@NotNull String hex) {
        try {
            byte[] bytes = new byte[hex.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, (i + 1) * 2), 16);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Make a string's first character uppercase
     * @param text The input string.
     * @return Returns a string with the first character of string capitalized.
     */
    public static @NotNull String capitalize(@NotNull String text) {
        Objects.requireNonNull(text);
        if (text.isBlank()) return text;
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}

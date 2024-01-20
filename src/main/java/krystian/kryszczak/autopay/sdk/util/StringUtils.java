package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

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
    public static @Nullable String hex2bin(@NotNull String hexString) {
        try {
            byte[] bytes = new byte[hexString.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(hexString.substring(i * 2, (i + 1) * 2), 16);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

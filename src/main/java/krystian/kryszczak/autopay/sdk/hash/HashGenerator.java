package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashGenerator {

    /**
     * Generates hash.
     */
    public static @NotNull String generateHash(@NotNull Object[] array, @NotNull AutopayConfiguration configuration) {
        final StringBuilder builder = new StringBuilder();

        final String separator = configuration.getHashSeparator();

        for (Object obj : array) {
            builder.append(obj)
                .append(separator);
        }

        builder.append(configuration.getSharedKey());

        return encode(builder.toString(), configuration.getHashAlgorithm().toString());
    }

    private static @NotNull String encode(String orginalString, @NotNull String hashAlgorithm) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            byte[] hash = digest.digest(orginalString.getBytes());
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class HashGeneratorImpl implements HashGenerator {

    @Override
    public @NotNull String generateHash(@NotNull Object[] array, @NotNull BlueMediaConfiguration configuration) {
        final StringBuilder builder = new StringBuilder();

        final String separator = configuration.getHashSeparator();

        for (Object obj : array) {
            builder
                .append(obj)
                .append(separator);
        }

        builder.append(configuration.getSharedKey());

        return encode(builder.toString(), configuration.getHashAlgorithm().toString());
    }

    private @NotNull String encode(String orginalString, @NotNull String hashAlgorithm) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance(hashAlgorithm);
            byte[] hash = sha256.digest(orginalString.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
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

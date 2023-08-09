package krystian.kryszczak.bm.sdk.itn.decoder;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Base64ItnDecoder implements ItnDecoder {
    public @NotNull String decode(@NotNull String base64Encoded) {
        final byte[] decoded = Base64.getDecoder().decode(base64Encoded);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}

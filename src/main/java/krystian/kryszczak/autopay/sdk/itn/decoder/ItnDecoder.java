package krystian.kryszczak.autopay.sdk.itn.decoder;

import org.jetbrains.annotations.NotNull;

public sealed interface ItnDecoder permits Base64ItnDecoder {
    @NotNull String decode(@NotNull String data);
}

package krystian.kryszczak.bm.sdk.regulation.response;

import org.jetbrains.annotations.NotNull;

public record Regulation(
    @NotNull String regulationID,
    @NotNull String url,
    @NotNull String type,
    @NotNull String language
) {}

package krystian.kryszczak.bm.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.jetbrains.annotations.NotNull;

@JsonClassDescription
public record Regulation(
    @NotNull String regulationID,
    @NotNull String url,
    @NotNull String type,
    @NotNull String language
) {}

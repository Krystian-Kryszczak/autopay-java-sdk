package krystian.kryszczak.bm.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonClassDescription
public record Regulation(
    @NotNull String regulationID,
    @Nullable String url,
    @NotNull String type,
    @NotNull String language,
    @Nullable String inputLabel,
    @Nullable String gatewayID
) {}

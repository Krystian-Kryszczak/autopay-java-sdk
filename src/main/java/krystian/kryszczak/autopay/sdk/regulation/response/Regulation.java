package krystian.kryszczak.autopay.sdk.regulation.response;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@XmlRootElement
public record Regulation(
    @NotNull String regulationID,
    @Nullable String url,
    @NotNull String type,
    @NotNull String language,
    @Nullable String inputLabel,
    @Nullable String gatewayID
) {}

package krystian.kryszczak.autopay.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@XmlRootElement
@JsonClassDescription
public record Regulation(
    @NotNull String regulationID,
    @Nullable String url, // TODO sprawdzić czy jest wogóle szansa, że to występuje w jakiejś sytuacji
    @NotNull String type,
    @NotNull String language,
    @Nullable String inputLabel,
    @Nullable String gatewayID
) {}

package krystian.kryszczak.autopay.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

@XmlRootElement
@JsonClassDescription
public record Gateway(
    int gatewayID,
    @NotNull String gatewayName,
    @NotNull String gatewayType,
    @NotNull String bankName,
    @NotNull String iconURL,
    @NotNull String statusDate
) {}

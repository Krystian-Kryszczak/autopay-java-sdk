package krystian.kryszczak.bm.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription
public record Gateway(
    int gatewayID,
    String gatewayName,
    String gatewayType,
    String bankName,
    String iconURL,
    String statusDate
) {}

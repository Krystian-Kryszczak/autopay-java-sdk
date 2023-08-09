package krystian.kryszczak.bm.sdk.payway.response;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public record Gateway(
    int gatewayID,
    String gatewayName,
    String gatewayType,
    String bankName,
    String iconURL,
    String statusDate
) {}

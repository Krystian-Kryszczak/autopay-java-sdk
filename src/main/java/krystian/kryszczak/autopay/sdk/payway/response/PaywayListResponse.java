package krystian.kryszczak.autopay.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonRootName("list")
@XmlRootElement
@XmlType(propOrder = { "serviceID", "messageID", "gateways", "hash" })
public final class PaywayListResponse extends PaywayList {
    @XmlList
    @JsonProperty("gateway")
    private final @NotNull Gateway @NotNull [] gateways;

    @ConstructorProperties({ "serviceID", "messageID", "gateways", "hash" })
    public PaywayListResponse(int serviceID, @NotNull String messageID, @NotNull Gateway @NotNull [] gateways, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.gateways = gateways;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Object[] base = new Object[] { serviceID, messageID };
        final Object[] dst = new Object[base.length + (gateways.length * 6)];
        System.arraycopy(base, 0, dst, 0, base.length);
        for (int i = 0; i < gateways.length; i++) {
            final Gateway gateway = gateways[i];
            final int j = (i * 6) + base.length;
            dst[j] = gateway.gatewayID();
            dst[j + 1] = gateway.gatewayName();
            dst[j + 2] = gateway.gatewayType();
            dst[j + 3] = gateway.bankName();
            dst[j + 4] = gateway.iconURL();
            dst[j + 5] = gateway.statusDate();
        }
        return dst;
    }
}

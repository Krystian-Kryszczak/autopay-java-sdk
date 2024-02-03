package krystian.kryszczak.autopay.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonRootName("list")
@XmlRootElement
@XmlType(propOrder = { "serviceID", "messageID", "gateways", "hash" })
public final class PaywayListResponse extends PaywayList {
    @XmlList
    @JsonProperty("gateway")
    private final @NotNull List<Gateway> gateways;

    @JsonCreator
    @ConstructorProperties({ "serviceID", "messageID", "gateways", "hash" })
    public PaywayListResponse(@NotNull String serviceID, @NotNull String messageID, @NotNull List<Gateway> gateways, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.gateways = gateways;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        return new Object[] {
            serviceID,
            messageID,
            gateways
        };
    }
}

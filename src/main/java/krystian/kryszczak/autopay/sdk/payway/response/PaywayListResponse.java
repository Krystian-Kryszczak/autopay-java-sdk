package krystian.kryszczak.autopay.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;
import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@XmlRootElement
@XmlType(propOrder = {"serviceID", "messageID", "gateways", "hash"})
public final class PaywayListResponse extends PaywayList {
    @XmlList
    @JsonProperty("gateway")
    private final List<Gateway> gateways;

    @JsonCreator
    @ConstructorProperties({"serviceID", "messageID", "gateways", "hash"})
    public PaywayListResponse(@NotNull String serviceID, @NotNull String messageID, @NotNull List<Gateway> gateways, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.gateways = gateways;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Object[] base = super.toArray();
        final Object[] result = Arrays.copyOf(base, base.length + 1);

        result[base.length] = gateways;

        return result;
    }
}

package krystian.kryszczak.autopay.sdk.payway.response;

import jakarta.xml.bind.annotation.XmlList;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public final class PaywayListResponse extends PaywayList {
    @XmlList
    private final List<Gateway> gateways;

    private PaywayListResponse(@NotNull String serviceID, @NotNull String messageID, @NotNull String hash, List<Gateway> gateways) {
        super(serviceID, messageID, hash);
        this.gateways = gateways;
    }

    @Override
    public @NotNull Object[] toArray() {
        final Object[] base = super.toArray();
        final Object[] result = Arrays.copyOf(base, base.length + 1);

        result[base.length] = gateways;

        return result;
    }
}

package krystian.kryszczak.bm.sdk.itn;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.serializer.XmlSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
public final class Itn extends Hashable implements Serializable {
    private final @NotNull String serviceID;
    private final @NotNull String orderID;
    private final @NotNull String remoteID;
    private final @NotNull String amount;
    private final @NotNull String currency;
    private final int gatewayID;
    private final @NotNull String paymentDate;
    private @NotNull String paymentStatus;
    private @NotNull String paymentStatusDetails;
    private @NotNull CustomerData customerData;

    private @Nullable String hash;

    public static @Nullable Itn buildFormXml(@NotNull String decoded) {
        return new XmlSerializer().deserialize(decoded, Itn.class);
    }

    public boolean isPaymentStatusSuccess() {
        return paymentStatus.equals("SUCCESS");
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[] {
            serviceID,
            orderID,
            remoteID,
            amount,
            currency,
            gatewayID,
            paymentDate,
            paymentStatus,
            paymentStatusDetails,
            customerData
        };
    }
}

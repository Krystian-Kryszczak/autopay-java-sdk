package krystian.kryszczak.bm.sdk.itn;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public final class Itn implements Serializable, Hashable {
    private final @NotNull String serviceId;
    private final @NotNull String orderId;
    private final @NotNull String remoteId;
    private final @NotNull String amount;
    private final @NotNull String currency;
    private final int gatewayId;
    private final @NotNull String paymentDate;
    private @NotNull String paymentStatus;
    private @NotNull String paymentStatusDetails;
    private @NotNull CustomerData customerData;

    private @Nullable String hash;

    public static @NotNull Itn buildFormXml(@NotNull String decoded) {
        return null; // TODO
    }

    @Override
    public boolean isHashPresent() {
        return getHash() != null;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() { // TODO
        return new Object[] {
            serviceId
        };
    }
}

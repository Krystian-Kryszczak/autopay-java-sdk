package krystian.kryszczak.bm.sdk.itn.response;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.serializer.XmlSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItnResponse implements Serializable {
    private final int serviceID;
    private final @NotNull TransactionsConfirmations transactionsConfirmations;
    private final @NotNull String hash;

    private static final String STATUS_CONFIRMED = "CONFIRMED";
    private static final String STATUS_NOT_CONFIRMED = "NOTCONFIRMED";

    public static @NotNull ItnResponse create(@NotNull Itn itn, boolean transactionConfirmed, @NotNull BlueMediaConfiguration configuration) {
        final var confirmation = transactionConfirmed ? STATUS_CONFIRMED : STATUS_NOT_CONFIRMED;

        final String orderId = itn.getOrderID();

        final var hashData = new Object[] {
            configuration.getServiceId(),
            orderId,
            confirmation
        };

        return new ItnResponse(
            configuration.getServiceId(),
            new TransactionsConfirmations(
                new TransactionConfirmed(
                    orderId,
                    confirmation
                )
            ),
            HashGenerator.generateHash(hashData, configuration)
        );
    }

    public @Nullable String toXml() {
        return new XmlSerializer().serialize(this);
    }
}

package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true) // TEMP TODO
public abstract sealed class Transaction implements HttpRequestBody, Hashable, Serializable permits TransactionBackground, TransactionInit, TransactionContinue {
    protected final @NotNull String serviceId;
    /**
     * Transaction ID, required
     */
    protected final @NotNull String orderId;
    /**
     * Transaction amount, required
     */
    protected final @NotNull String amount;
    /**
     * Transaction description, optional
     */
    protected final @Nullable String description;

    protected final @Nullable Integer gatewayId;
    /**
     * Transaction currency, optional, default PLN
     */
    protected final @Nullable String currency;
    /**
     * Customer's email, optional, recommended due to the automatic completion of the field on the BM service
     */
    protected final @Nullable String customerEmail;
    protected final @Nullable String customerNRB;
    protected final @Nullable String texCountry;
    protected final @Nullable String customerIp;
    protected final @Nullable String title;
    protected final @Nullable String receiverName;
    protected final @Nullable LocalDateTime validityTime;
    protected final @Nullable LocalDateTime linkValidityTime;
    protected final @Nullable String authorizationCode;
    protected final @Nullable String screenType;
    protected final @Nullable String blikUIDKey;
    protected final @Nullable String blikUIDLabel;
    protected final @Nullable String returnUrl;
    protected final @Nullable String defaultRegulationAcceptanceState;
    protected final @Nullable String defaultRegulationAcceptanceId;
    protected final @Nullable LocalDateTime defaultRegulationAcceptanceTime;
    protected final @Nullable String receiverNRB;
    protected final @Nullable String receiverAddress;
    protected final @Nullable String remoteId;
    protected final @Nullable String bankHref;

    protected final @Nullable String hash;

    @Override
    public boolean isHashPresent() {
        return hash != null;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        final List<Object> list = new LinkedList<>(List.of(serviceId, orderId, amount));

        final Object[] nullable = new Object[] {
            description,
            gatewayId,
            currency,
            customerEmail,
            customerNRB,
            texCountry,
            customerIp,
            title,
            receiverName,
            validityTime,
            linkValidityTime,
            authorizationCode,
            screenType,
            blikUIDKey,
            blikUIDLabel,
            returnUrl,
            defaultRegulationAcceptanceState,
            defaultRegulationAcceptanceId,
            defaultRegulationAcceptanceTime,
            receiverNRB,
            receiverAddress,
            remoteId,
            bankHref
        };

        for (Object obj : nullable) {
            if (obj != null) {
                list.add(obj);
            }
        }

        return list.toArray();
    }
}

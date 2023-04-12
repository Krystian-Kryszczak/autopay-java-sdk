package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract sealed class Transaction implements Hashable
        permits TransactionBackground, TransactionInit, TransactionContinue {
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
    public @NotNull Object[] toArrayWithoutHash() { // TODO
        return new Object[0];
    }
}

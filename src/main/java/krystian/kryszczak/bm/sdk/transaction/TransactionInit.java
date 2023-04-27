package krystian.kryszczak.bm.sdk.transaction;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public final class TransactionInit extends Transaction {
    private final @NotNull String confirmation;
    private final @Nullable String reason;
    private final @NotNull String paymentStatus;

    public TransactionInit(@NotNull String serviceId, @NotNull String orderId, @NotNull String amount, @Nullable String description, @Nullable Integer gatewayId, @Nullable String currency, @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry, @Nullable String customerIp, @Nullable String title, @Nullable String receiverName, @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime, @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey, @Nullable String blikUIDLabel, @Nullable String returnUrl, @Nullable String defaultRegulationAcceptanceState, @Nullable String defaultRegulationAcceptanceId, @Nullable LocalDateTime defaultRegulationAcceptanceTime, @Nullable String receiverNRB, @Nullable String receiverAddress, @Nullable String remoteId, @Nullable String bankHref, @Nullable String hash, @NotNull String confirmation, @Nullable String reason, @NotNull String paymentStatus) {
        super(serviceId, orderId, amount, description, gatewayId, currency, customerEmail, customerNRB, texCountry, customerIp, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey, blikUIDLabel, returnUrl, defaultRegulationAcceptanceState, defaultRegulationAcceptanceId, defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteId, bankHref, hash);
        this.confirmation = confirmation;
        this.reason = reason;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        return Map.of(
            // TODO
        );
    }
}

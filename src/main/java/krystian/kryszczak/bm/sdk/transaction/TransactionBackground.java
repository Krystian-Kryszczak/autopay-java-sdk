package krystian.kryszczak.bm.sdk.transaction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public final class TransactionBackground extends Transaction {
    public TransactionBackground(@NotNull String serviceId, @NotNull String orderId, @NotNull String amount, @Nullable String description, @Nullable Integer gatewayId, @Nullable String currency, @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry, @Nullable String customerIp, @Nullable String title, @Nullable String receiverName, @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime, @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey, @Nullable String blikUIDLabel, @Nullable String returnUrl, @Nullable String defaultRegulationAcceptanceState, @Nullable String defaultRegulationAcceptanceId, @Nullable LocalDateTime defaultRegulationAcceptanceTime, @Nullable String receiverNRB, @Nullable String receiverAddress, @Nullable String remoteId, @Nullable String bankHref, @Nullable String hash) {
        super(serviceId, orderId, amount, description, gatewayId, currency, customerEmail, customerNRB, texCountry, customerIp, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey, blikUIDLabel, returnUrl, defaultRegulationAcceptanceState, defaultRegulationAcceptanceId, defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteId, bankHref, hash);
    }
}

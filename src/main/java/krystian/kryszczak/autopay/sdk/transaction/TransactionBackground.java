package krystian.kryszczak.autopay.sdk.transaction;

import krystian.kryszczak.autopay.sdk.util.CollectionsUtils;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Map;

@SuperBuilder
public final class TransactionBackground extends Transaction {
    @ConstructorProperties({"receiverNRB", "receiverName", "receiverAddress", "orderID", "amount", "currency", "title", "remoteID", "bankHref", "returnURL", "hash"})
    public TransactionBackground(int serviceID, @NotNull String orderID, @NotNull String amount, @Nullable String description, @Nullable Integer gatewayID, @Nullable String currency, @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry, @Nullable String customerIP, @Nullable String title, @Nullable String receiverName, @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime, @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey, @Nullable String blikUIDLabel, @Nullable String blikAMKey, @Nullable String returnURL, @Nullable String defaultRegulationAcceptanceState, @Nullable String defaultRegulationAcceptanceID, @Nullable LocalDateTime defaultRegulationAcceptanceTime, @Nullable String receiverNRB, @Nullable String receiverAddress, @Nullable String remoteID, @Nullable String bankHref, @Nullable String hash) {
        super(serviceID, orderID, amount, description, gatewayID, currency, customerEmail, customerNRB, texCountry, customerIP, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey, blikUIDLabel, blikAMKey, returnURL, defaultRegulationAcceptanceState, defaultRegulationAcceptanceID, defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteID, bankHref, hash);
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return CollectionsUtils.nonNullMapFromArray(
            "receiverNRB", receiverNRB,
            "receiverName", receiverName,
            "receiverAddress", receiverAddress,
            "orderID", orderID,
            "amount", amount,
            "currency", currency,
            "title", title,
            "remoteID", remoteID,
            "bankHref", bankHref,
            "returnURL", returnURL,
            "hash", hash
        );
    }

    @Override
    public @NotNull Object[] toArray() {
        return CollectionsUtils.filterNonNull(new Object[] {
            receiverNRB,
            receiverName,
            receiverAddress,
            orderID,
            amount,
            currency,
            title,
            remoteID,
            bankHref,
            returnURL
        });
    }
}

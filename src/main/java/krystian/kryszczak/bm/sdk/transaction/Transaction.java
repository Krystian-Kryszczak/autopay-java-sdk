package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@SuperBuilder
public abstract sealed class Transaction implements HttpRequestBody, Hashable, Serializable permits TransactionBackground, TransactionResponse {
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
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        final Map<@NotNull String, @NotNull String> result = new LinkedHashMap<>();
        result.put("serviceID", serviceId);
        result.put("orderID", orderId);
        result.put("amount", amount);

        final Map<@NotNull String, @Nullable Object> nullable = new LinkedHashMap<>();
        nullable.put("description", description);
        nullable.put("gatewayID", gatewayId);
        nullable.put("currency", currency);
        nullable.put("customerEmail", customerEmail);
        nullable.put("customerNRB", customerNRB);
        nullable.put("texCountry", texCountry);
        nullable.put("customerIp", customerIp);
        nullable.put("title", title);
        nullable.put("receiverName", receiverName);
        nullable.put("validityTime", validityTime);
        nullable.put("linkValidityTime", linkValidityTime);
        nullable.put("authorizationCode", authorizationCode);
        nullable.put("screenType", screenType);
        nullable.put("blikUIDKey", blikUIDKey);
        nullable.put("blikUIDLabel", blikUIDLabel);
        nullable.put("returnUrl", returnUrl);
        nullable.put("defaultRegulationAcceptanceState", defaultRegulationAcceptanceState);
        nullable.put("defaultRegulationAcceptanceID", defaultRegulationAcceptanceId);
        nullable.put("defaultRegulationAcceptanceTime", defaultRegulationAcceptanceTime);
        nullable.put("receiverNRB", receiverNRB);
        nullable.put("receiverAddress", receiverAddress);
        nullable.put("remoteId", remoteId);
        nullable.put("bankHref", bankHref);

        nullable.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .forEach(entry -> result.put(entry.getKey(), String.valueOf(entry.getValue())));

        return result;
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

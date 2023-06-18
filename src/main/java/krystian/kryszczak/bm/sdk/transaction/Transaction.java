package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@SuperBuilder
public abstract sealed class Transaction extends Hashable implements HttpRequestBody, Serializable permits TransactionBackground, TransactionResponse {
    @Setter
    protected int serviceID;
    /**
     * Transaction ID, required
     */
    protected final @NotNull String orderID;
    /**
     * Transaction amount, required
     */
    protected final @NotNull String amount;
    /**
     * Transaction description, optional
     */
    protected final @Nullable String description;

    protected final @Nullable Integer gatewayID;
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
    protected final @Nullable String customerIP;
    protected final @Nullable String title;
    protected final @Nullable String receiverName;
    protected final @Nullable LocalDateTime validityTime;
    protected final @Nullable LocalDateTime linkValidityTime;
    protected final @Nullable String authorizationCode;
    protected final @Nullable String screenType;
    protected final @Nullable String blikUIDKey;
    protected final @Nullable String blikUIDLabel;
    protected final @Nullable String returnURL;
    protected final @Nullable String defaultRegulationAcceptanceState;
    protected final @Nullable String defaultRegulationAcceptanceID;
    protected final @Nullable LocalDateTime defaultRegulationAcceptanceTime;
    protected final @Nullable String receiverNRB;
    protected final @Nullable String receiverAddress;
    protected final @Nullable String remoteID;
    protected final @Nullable String bankHref;

    @Setter
    protected @Nullable String hash;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        final Map<@NotNull String, @NotNull String> result = new LinkedHashMap<>();
        result.put("serviceID", String.valueOf(serviceID));
        result.put("orderID", orderID);
        result.put("amount", amount);

        final Map<@NotNull String, @Nullable Object> nullable = new LinkedHashMap<>();
        nullable.put("description", description);
        nullable.put("gatewayID", gatewayID);
        nullable.put("currency", currency);
        nullable.put("customerEmail", customerEmail);
        nullable.put("customerNRB", customerNRB);
        nullable.put("texCountry", texCountry);
        nullable.put("customerIp", customerIP);
        nullable.put("title", title);
        nullable.put("receiverName", receiverName);
        nullable.put("validityTime", validityTime);
        nullable.put("linkValidityTime", linkValidityTime);
        nullable.put("authorizationCode", authorizationCode);
        nullable.put("screenType", screenType);
        nullable.put("blikUIDKey", blikUIDKey);
        nullable.put("blikUIDLabel", blikUIDLabel);
        nullable.put("returnUrl", returnURL);
        nullable.put("defaultRegulationAcceptanceState", defaultRegulationAcceptanceState);
        nullable.put("defaultRegulationAcceptanceID", defaultRegulationAcceptanceID);
        nullable.put("defaultRegulationAcceptanceTime", defaultRegulationAcceptanceTime);
        nullable.put("receiverNRB", receiverNRB);
        nullable.put("receiverAddress", receiverAddress);
        nullable.put("remoteId", remoteID);
        nullable.put("bankHref", bankHref);

        nullable.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .forEach(entry -> result.put(entry.getKey(), String.valueOf(entry.getValue())));

        return result;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        final List<Object> list = new LinkedList<>(List.of(serviceID, orderID, amount));

        final Object[] nullable = new Object[] {
            description,
            gatewayID,
            currency,
            customerEmail,
            customerNRB,
            texCountry,
            customerIP,
            title,
            receiverName,
            validityTime,
            linkValidityTime,
            authorizationCode,
            screenType,
            blikUIDKey,
            blikUIDLabel,
            returnURL,
            defaultRegulationAcceptanceState,
            defaultRegulationAcceptanceID,
            defaultRegulationAcceptanceTime,
            receiverNRB,
            receiverAddress,
            remoteID,
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

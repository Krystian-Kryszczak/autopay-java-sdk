package krystian.kryszczak.autopay.sdk.transaction;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@XmlRootElement
@XmlType(propOrder = {"status", "redirecturl", "orderID", "remoteID"})
public final class TransactionContinue extends Transaction {
    private final String status;
    private final String redirecturl;

    @ConstructorProperties({"status", "redirecturl", "orderID", "remoteID"})
    public TransactionContinue(int serviceID, @NotNull String orderID, @NotNull String amount, @Nullable String description, @Nullable Integer gatewayID, @Nullable String currency, @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry, @Nullable String customerIP, @Nullable String title, @Nullable String receiverName, @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime, @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey, @Nullable String blikUIDLabel, @Nullable String blikAMKey, @Nullable String returnURL, @Nullable String defaultRegulationAcceptanceState, @Nullable String defaultRegulationAcceptanceID, @Nullable LocalDateTime defaultRegulationAcceptanceTime, @Nullable String receiverNRB, @Nullable String receiverAddress, @Nullable String remoteID, @Nullable String bankHref, @Nullable String hash, String status, String redirecturl) {
        super(serviceID, orderID, amount, description, gatewayID, currency, customerEmail, customerNRB, texCountry, customerIP, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey, blikUIDLabel, blikAMKey, returnURL, defaultRegulationAcceptanceState, defaultRegulationAcceptanceID, defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteID, bankHref, hash);
        this.status = status;
        this.redirecturl = redirecturl;
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        final Map<@NotNull String, @Nullable Object> nullable = new LinkedHashMap<>();
        nullable.put("status", receiverNRB);
        nullable.put("redirecturl", redirecturl);
        nullable.put("orderID", orderID);
        nullable.put("remoteID", remoteID);

        nullable.put("hash", hash);

        final Map<@NotNull String, @NotNull String> result = new LinkedHashMap<>();
        nullable.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .forEach(entry -> result.put(entry.getKey(), String.valueOf(entry.getValue())));

        return result;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Object[] nullable = new Object[] {
            status,
            redirecturl,
            orderID,
            remoteID,
        };

        final LinkedList<Object> result = new LinkedList<>();
        for (Object obj : nullable) {
            if (obj != null) {
                result.addLast(obj);
            }
        }

        return result.toArray();
    }
}

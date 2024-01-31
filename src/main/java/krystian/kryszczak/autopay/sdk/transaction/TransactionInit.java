package krystian.kryszczak.autopay.sdk.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@XmlRootElement
@XmlType(propOrder = {"orderID", "remoteID", "confirmation", "reason", "blikAMKey", "blikAMLabel", "paymentStatus"})
public final class TransactionInit extends Transaction {
    private final String confirmation;
    private final String reason;
    private final String paymentStatus;

    @JsonCreator
    @ConstructorProperties({"orderID", "remoteID", "confirmation", "reason", "blikAMKey", "blikAMLabel", "paymentStatus"})
    public TransactionInit(int serviceID, @NotNull String orderID, @Nullable String amount, @Nullable String description,
           @Nullable Integer gatewayID, @Nullable String currency, @Nullable String customerEmail,
           @Nullable String customerNRB, @Nullable String texCountry, @Nullable String customerIP,
           @Nullable String title, @Nullable String receiverName, @Nullable LocalDateTime validityTime,
           @Nullable LocalDateTime linkValidityTime, @Nullable String authorizationCode, @Nullable String screenType,
           @Nullable String blikUIDKey, @Nullable String blikUIDLabel, @Nullable String blikAMKey,
           @Nullable String returnURL, @Nullable String defaultRegulationAcceptanceState,
           @Nullable String defaultRegulationAcceptanceID, @Nullable LocalDateTime defaultRegulationAcceptanceTime,
           @Nullable String receiverNRB, @Nullable String receiverAddress, @Nullable String remoteID,
           @Nullable String bankHref, @Nullable String hash, String confirmation, String reason, String paymentStatus) {
        super(serviceID, orderID, amount, description, gatewayID, currency, customerEmail, customerNRB, texCountry,
            customerIP, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey,
            blikUIDLabel, blikAMKey, returnURL, defaultRegulationAcceptanceState, defaultRegulationAcceptanceID,
            defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteID, bankHref, hash);
        this.confirmation = confirmation;
        this.reason = reason;
        this.paymentStatus = paymentStatus;
    }

    @Transient
    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        final Map<@NotNull String, @Nullable Object> nullable = new LinkedHashMap<>();
        nullable.put("orderID", orderID);
        nullable.put("remoteID", remoteID);
        nullable.put("confirmation", confirmation);
        nullable.put("reason", reason);
        nullable.put("paymentStatus", paymentStatus);

        nullable.put("hash", hash);

        final Map<@NotNull String, @NotNull String> result = new LinkedHashMap<>();
        nullable.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .forEach(entry -> result.put(entry.getKey(), String.valueOf(entry.getValue())));

        return result;
    }

    @Transient
    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Object[] nullable = new Object[] {
            orderID,
            remoteID,
            confirmation,
            paymentStatus,
            reason,
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

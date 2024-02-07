package krystian.kryszczak.autopay.sdk.transaction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Map;

import static krystian.kryszczak.autopay.sdk.util.ArrayUtils.filterNotNull;
import static krystian.kryszczak.autopay.sdk.util.MapUtils.mergeIfAbsent;
import static krystian.kryszczak.autopay.sdk.util.MapUtils.notNullMapOf;

@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription
@XmlRootElement
@XmlType(propOrder = {
    "receiverNRB",
    "receiverName",
    "receiverAddress",
    "orderID",
    "amount",
    "currency",
    "title",
    "remoteID",
    "bankHref",
    "returnURL",
    "hash"
})
public final class TransactionBackground extends Transaction {
    @JsonCreator
    @ConstructorProperties({
        "receiverNRB", "receiverName", "receiverAddress", "orderID", "amount", "currency", "title", "remoteID", "bankHref",
        "returnURL", "hash", "serviceID", "description", "gatewayID", "customerEmail", "customerNRB", "texCountry",
        "customerIP", "validityTime", "linkValidityTime", "authorizationCode", "screenType", "blikUIDKey", "blikUIDLabel",
        "blikAMKey", "defaultRegulationAcceptanceState", "defaultRegulationAcceptanceID", "defaultRegulationAcceptanceTime"
    })
    public TransactionBackground(@NotNull String receiverNRB, @NotNull String receiverName, @NotNull String receiverAddress,
            @NotNull String orderID, @NotNull String amount, @Nullable String currency, @NotNull String title,
            @NotNull String remoteID, @NotNull String bankHref, @Nullable String returnURL, @Nullable String hash,
            @Nullable Integer serviceID, @Nullable String description, @Nullable Integer gatewayID,
            @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry,
            @Nullable String customerIP, @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime,
            @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey,
            @Nullable String blikUIDLabel, @Nullable String blikAMKey, @Nullable String defaultRegulationAcceptanceState,
            @Nullable String defaultRegulationAcceptanceID, @Nullable LocalDateTime defaultRegulationAcceptanceTime) {
        super(serviceID, orderID, amount, description, gatewayID, currency, customerEmail, customerNRB, texCountry,
            customerIP, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey,
            blikUIDLabel, blikAMKey, returnURL, defaultRegulationAcceptanceState, defaultRegulationAcceptanceID,
            defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteID, bankHref, hash);
    }

    public TransactionBackground(@NotNull String receiverNRB, @NotNull String receiverName, @NotNull String receiverAddress,
             @NotNull String orderID, @NotNull String amount, @Nullable String currency, @NotNull String title,
             @NotNull String remoteID, @NotNull String bankHref, @Nullable String returnURL, @Nullable String hash) {
        this(receiverNRB, receiverName, receiverAddress, orderID, amount, currency, title, remoteID, bankHref, returnURL, hash,
            null, null, null, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null);
    }

    @Transient
    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return mergeIfAbsent(notNullMapOf(
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
        ), super.toMap());
    }

    @Transient
    @Override
    public @NotNull String @NotNull [] toArray() {
        return filterNotNull(
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
        );
    }
}

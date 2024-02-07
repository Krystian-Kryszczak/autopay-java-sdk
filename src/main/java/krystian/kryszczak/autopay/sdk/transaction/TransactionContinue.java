package krystian.kryszczak.autopay.sdk.transaction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription
@XmlRootElement
@XmlType(propOrder = {
    "status",
    "redirecturl",
    "orderID",
    "remoteID",
    "hash"
})
public final class TransactionContinue extends Transaction {
    private final String status;
    private final String redirecturl;

    @JsonCreator
    @ConstructorProperties({
        "status", "redirecturl", "orderID", "remoteID", "hash", "serviceID", "amount", "description", "gatewayID",
        "currency", "customerEmail", "customerNRB", "texCountry", "customerIP", "title", "receiverName", "validityTime",
        "linkValidityTime", "authorizationCode", "screenType", "blikUIDKey", "blikUIDLabel", "blikAMKey", "returnURL",
        "defaultRegulationAcceptanceState", "defaultRegulationAcceptanceID", "defaultRegulationAcceptanceTime",
        "receiverNRB", "receiverAddress", "bankHref"
    })
    public TransactionContinue(@NotNull String status, @NotNull String redirecturl, @NotNull String orderID,
           @NotNull String remoteID, @Nullable String hash, @Nullable Integer serviceID,  @Nullable String amount,
            @Nullable String description, @Nullable Integer gatewayID, @Nullable String currency,
            @Nullable String customerEmail, @Nullable String customerNRB, @Nullable String texCountry,
            @Nullable String customerIP, @Nullable String title, @Nullable String receiverName,
            @Nullable LocalDateTime validityTime, @Nullable LocalDateTime linkValidityTime,
            @Nullable String authorizationCode, @Nullable String screenType, @Nullable String blikUIDKey,
            @Nullable String blikUIDLabel, @Nullable String blikAMKey, @Nullable String returnURL,
            @Nullable String defaultRegulationAcceptanceState, @Nullable String defaultRegulationAcceptanceID,
            @Nullable LocalDateTime defaultRegulationAcceptanceTime, @Nullable String receiverNRB,
            @Nullable String receiverAddress, @Nullable String bankHref) {
        super(serviceID, orderID, amount, description, gatewayID, currency, customerEmail, customerNRB, texCountry,
            customerIP, title, receiverName, validityTime, linkValidityTime, authorizationCode, screenType, blikUIDKey,
            blikUIDLabel, blikAMKey, returnURL, defaultRegulationAcceptanceState, defaultRegulationAcceptanceID,
            defaultRegulationAcceptanceTime, receiverNRB, receiverAddress, remoteID, bankHref, hash);
        this.status = status;
        this.redirecturl = redirecturl;
    }

    public TransactionContinue(@NotNull String status, @NotNull String redirecturl, @NotNull String orderID,
            @NotNull String remoteID, @Nullable String hash) {
        this(status, redirecturl, orderID, remoteID, hash, null, null, null, null,
            null, null, null, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null,
            null, null, null, null);
    }

    @Transient
    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return mergeIfAbsent(notNullMapOf(
            "status", status,
            "redirecturl", redirecturl,
            "orderID", orderID,
            "remoteID", remoteID,
            "hash", hash
        ), super.toMap());
    }

    @Transient
    @Override
    public @NotNull String @NotNull [] toArray() {
        return filterNotNull(
            status,
            redirecturl,
            orderID,
            remoteID
        );
    }
}

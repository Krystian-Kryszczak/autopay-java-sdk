package krystian.kryszczak.autopay.sdk.transaction;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static krystian.kryszczak.autopay.sdk.util.ArrayUtils.filterNotNull;
import static krystian.kryszczak.autopay.sdk.util.MapUtils.notNullMapOf;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonRootName("transaction")
@XmlRootElement
@XmlType(propOrder = {
    "serviceID",
    "orderID",
    "amount",
    "description",
    "gatewayID",
    "currency",
    "customerEmail",
    "customerNRB",
    "taxCountry",
    "customerIP",
    "title",
    "receiverName",
    "validityTime",
    "linkValidityTime",
    "authorizationCode",
    "screenType",
    "blikUIDKey",
    "blikUIDLabel",
    "blikAMKey",
    "returnURL",
    "defaultRegulationAcceptanceState",
    "defaultRegulationAcceptanceID",
    "defaultRegulationAcceptanceTime",

    "receiverNRB",
    "receiverAddress",
    "remoteID",
    "bankHref",

    "hash"
})
@AllArgsConstructor
public abstract sealed class Transaction extends Hashable implements HttpRequestBody, Serializable
        permits TransactionBackground, TransactionContinue, TransactionInit, PaywayFormResponse {
    @Setter
    protected @Nullable Integer serviceID;
    /**
     Transaction ID, required
     */
    protected final @Nullable String orderID;
    /**
     Transaction amount, required
     */
    protected final @Nullable String amount;
    /**
     Transaction description, optional
     */
    protected final @Nullable String description;

    protected final @Nullable Integer gatewayID;
    /**
     Transaction currency, optional, default PLN
     */
    protected final @Nullable String currency;
    /**
     Customer's email, optional, recommended due to the automatic completion of the field on the BM service
     */
    protected final @Nullable String customerEmail;
    /**
     * Transaction customer bank account number.
     */
    protected final @Nullable String customerNRB;
    /**
     * Transaction tax country.
     */
    protected final @Nullable String texCountry;
    /**
     * Customer IP address.
     */
    protected final @Nullable String customerIP;
    /**
     * Transaction title.
     */
    protected final @Nullable String title;
    /**
     * Transaction receiver name.
     */
    protected final @Nullable String receiverName;
    /**
     * Transaction validity time.
     */
    protected final @Nullable LocalDateTime validityTime;
    /**
     * Transaction link validity time.
     */
    protected final @Nullable LocalDateTime linkValidityTime;
    /**
     * Transaction authorization code.
     */
    protected final @Nullable String authorizationCode;
    /**
     * Screen tpe for payment authorization (only for card payment).
     */
    protected final @Nullable String screenType;
    /**
     * BLIK Alias UID key.
     */
    protected final @Nullable String blikUIDKey;
    /**
     * BLIK Alias UID label.
     */
    protected final @Nullable String blikUIDLabel;
    /**
     * BLIK banks mobile application key.
     */
    protected final @Nullable String blikAMKey;
    protected final @Nullable String returnURL;
    protected final @Nullable String defaultRegulationAcceptanceState;
    protected final @Nullable String defaultRegulationAcceptanceID;
    protected final @Nullable LocalDateTime defaultRegulationAcceptanceTime;
    /**
     * Receiver bank account number.
     */
    protected final @Nullable String receiverNRB;
    /**
     * Receiver address.
     */
    protected final @Nullable String receiverAddress;
    /**
     * Remote order id.
     */
    protected final @Nullable String remoteID;
    /**
     * Banks system URL.
     */
    protected final @Nullable String bankHref;

    /**
     * Transaction hash.
     */
    @Setter
    protected @Nullable String hash;

    public @Nullable String getHash() {
        return hash != null ? hash.trim() : null;
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return notNullMapOf(
            "serviceID", serviceID,
            "orderID", orderID,
            "amount", amount,
            "description", description,
            "gatewayID", gatewayID,
            "currency", currency,
            "customerEmail", customerEmail,
            "customerNRB", customerNRB,
            "texCountry", texCountry,
            "customerIP", customerIP,
            "title", title,
            "receiverName", receiverName,
            "validityTime", validityTime,
            "linkValidityTime", linkValidityTime,
            "authorizationCode", authorizationCode,
            "screenType", screenType,
            "blikUIDKey", blikUIDKey,
            "blikUIDLabel", blikUIDLabel,
            "blikAMKey", blikAMKey,
            "returnURL", returnURL,
            "defaultRegulationAcceptanceState", defaultRegulationAcceptanceState,
            "defaultRegulationAcceptanceID", defaultRegulationAcceptanceID,
            "defaultRegulationAcceptanceTime", defaultRegulationAcceptanceTime,
            "receiverNRB", receiverNRB,
            "receiverAddress", receiverAddress,
            "remoteID", remoteID,
            "bankHref", bankHref,
            "hash", hash
        );
    }

    @Override
    public @NotNull String[] toArray() {
        return filterNotNull(
            serviceID != null ? String.valueOf(serviceID) : null,
            orderID,
            amount,
            description,
            gatewayID != null ? String.valueOf(gatewayID) : null,
            currency,
            customerEmail,
            customerNRB,
            texCountry,
            customerIP,
            title,
            receiverName,
            validityTime != null ? String.valueOf(validityTime) : null,
            linkValidityTime != null ? String.valueOf(linkValidityTime) : null,
            authorizationCode,
            screenType,
            blikUIDKey,
            blikUIDLabel,
            blikAMKey,
            returnURL,
            defaultRegulationAcceptanceState,
            defaultRegulationAcceptanceID,
            defaultRegulationAcceptanceTime != null ? String.valueOf(defaultRegulationAcceptanceTime) : null,

            receiverNRB,
            receiverAddress,
            remoteID,
            bankHref
        );
    }
}

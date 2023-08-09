package krystian.kryszczak.bm.sdk.transaction;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import krystian.kryszczak.bm.sdk.util.CollectionsUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@SuperBuilder
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
public abstract sealed class Transaction extends Hashable implements HttpRequestBody, Serializable permits TransactionBackground, TransactionContinue, TransactionInit {
    @Setter
    protected int serviceID;
    /**
     Transaction ID, required
     */
    protected final @NotNull String orderID;
    /**
     Transaction amount, required
     */
    protected final @NotNull String amount;
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

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return CollectionsUtils.nonNullMapFromArray(
            "serviceID", String.valueOf(serviceID),
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
    public @NotNull Object[] toArray() {
        return CollectionsUtils.filterNonNull(new Object[] {
            serviceID,
            orderID,
            amount,

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
        });
    }
}

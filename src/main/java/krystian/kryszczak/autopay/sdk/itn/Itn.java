package krystian.kryszczak.autopay.sdk.itn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.util.CollectionsUtils;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.io.Serializable;

@Getter
@Builder
@ToString
@XmlRootElement
@XmlType(propOrder = {
    "serviceID",
    "orderID",
    "remoteID",
    "amount",
    "currency",
    "gatewayID",
    "paymentDate",
    "paymentStatus",
    "paymentStatusDetails",
    "customerData",
    "hash"
})
@AllArgsConstructor(onConstructor_ = {
    @JsonCreator,
    @ConstructorProperties({
        "serviceID",
        "orderID",
        "remoteID",
        "amount",
        "currency",
        "gatewayID",
        "paymentDate",
        "paymentStatus",
        "paymentStatusDetails",
        "customerData",
        "hash"
    })
})
public final class Itn extends Hashable implements Serializable {
    /**
     * Transaction service id.
     */
    private final @Nullable String serviceID;
    /**
     * Transaction order id.
     */
    private final @NotNull String orderID;
    /**
     * Remote order id.
     */
    private final @NotNull String remoteID;
    /**
     * Transaction amount.
     */
    private final @NotNull String amount;
    /**
     * Transaction currency.
     */
    private final @NotNull String currency;
    /**
     * Transaction gateway id.
     */
    private final int gatewayID;
    /**
     * Payment date. YYYYMMDDhhmmss
     */
    private final @NotNull String paymentDate;
    /**
     * Payment status.
     */
    private final @NotNull String paymentStatus;
    /**
     * Payment status details.
     */
    private final @Nullable String paymentStatusDetails;
    /**
     * Customer data.
     */
    private final @Nullable CustomerData customerData;
    /**
     * Itn hash.
     */
    private final @Nullable String hash;

    @SneakyThrows
    public static @Nullable Itn buildFormXml(@NotNull String decoded) {
        final String transaction = new XmlMapper().readTree(decoded).get("transactions").get("transaction").toString();
        return new ObjectMapper().readValue(transaction, Itn.class);
    }

    @Transient
    public boolean isPaymentStatusSuccess() {
        return paymentStatus.equals("SUCCESS");
    }

    public @Nullable String getHash() {
        return hash != null ? hash.trim() : null;
    }

    @Override
    public @NotNull Object[] toArray() {
        return CollectionsUtils.filterNonNull(new Object[] {
            serviceID,
            orderID,
            remoteID,
            amount,
            currency,
            gatewayID,
            paymentDate,
            paymentStatus,
            paymentStatusDetails,
            customerData
        });
    }
}

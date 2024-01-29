package krystian.kryszczak.autopay.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@JsonRootName("confirmationList")
@XmlRootElement(name = "confirmationList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"serviceID", "transactionsConfirmations", "hash"})
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"serviceID", "transactionsConfirmations", "hash"}))
public final class ItnResponse implements Serializable {
    private final @JacksonXmlCData String serviceID;
    private final @NotNull TransactionsConfirmations transactionsConfirmations;
    private final @NotNull @JacksonXmlCData String hash;

    private static final String STATUS_CONFIRMED = "CONFIRMED";
    private static final String STATUS_NOT_CONFIRMED = "NOTCONFIRMED";

    public static @NotNull ItnResponse create(@NotNull Itn itn, boolean transactionConfirmed, @NotNull AutopayConfiguration configuration) {
        final var confirmation = transactionConfirmed ? STATUS_CONFIRMED : STATUS_NOT_CONFIRMED;

        final String orderId = itn.getOrderID();

        final var hashData = new Object[] {
            configuration.serviceId(),
            orderId,
            confirmation
        };

        return new ItnResponse(
            String.valueOf(configuration.serviceId()),
            new TransactionsConfirmations(
                new TransactionConfirmed(
                    orderId,
                    confirmation
                )
            ),
            HashGenerator.generateHash(hashData, configuration)
        );
    }

    public @Nullable String toXml() {
        return new XmlSerializer().serialize(this);
    }
}

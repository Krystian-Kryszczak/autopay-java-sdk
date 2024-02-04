package krystian.kryszczak.autopay.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import jakarta.xml.bind.annotation.*;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.function.Function;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonRootName("confirmationList")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "serviceID", "transactionsConfirmations", "hash" })
@AllArgsConstructor(access = AccessLevel.PUBLIC, onConstructor_ = {
    @JsonCreator,
    @ConstructorProperties({ "serviceID", "transactionsConfirmations", "hash" })
})
public final class ItnResponse implements Serializable {
    private final @NotNull @JacksonXmlCData String serviceID;
    private final @NotNull TransactionsConfirmations transactionsConfirmations;
    private final @NotNull @JacksonXmlCData String hash;

    private static final String STATUS_CONFIRMED = "CONFIRMED";
    private static final String STATUS_NOT_CONFIRMED = "NOTCONFIRMED";

    public static @NotNull ItnResponse create(final @NotNull Itn itn, final boolean transactionConfirmed, final @NotNull AutopayConfiguration configuration) {
        final String confirmation = transactionConfirmed ? STATUS_CONFIRMED : STATUS_NOT_CONFIRMED;

        final String orderId = itn.getOrderID();

        final Object[] hashData = new Object[] {
            configuration.serviceId(),
            orderId,
            confirmation
        };

        return new ItnResponse(
            String.valueOf(configuration.serviceId()),
            new TransactionsConfirmations(
                new TransactionConfirmed[] {
                    new TransactionConfirmed(
                        orderId,
                        confirmation
                    )
                }
            ),
            HashGenerator.generateHash(hashData, configuration)
        );
    }

    public static @NotNull ItnResponse create(final @NotNull ItnRequest request,
          final Function<@NotNull Itn, @NotNull Boolean> transactionConfirmed,
          final @NotNull AutopayConfiguration configuration) {
        final Itn[] transactions = request.getTransactions().transaction();

        final TransactionConfirmed[] confirmations = new TransactionConfirmed[transactions.length];
        final Object[] hashData = new Object[(confirmations.length * 2) + 1];
        hashData[0] = configuration.serviceId();

        for (int i = 0; i < transactions.length; i++) {
            final Itn transaction = transactions[i];
            final String orderID = transaction.getOrderID();
            final String confirmation = transactionConfirmed.apply(transaction)
                ? STATUS_CONFIRMED : STATUS_NOT_CONFIRMED;
            confirmations[i] = new TransactionConfirmed(orderID, confirmation);
            final int j = (i * 2) + 1;
            hashData[j] = orderID;
            hashData[j + 1] = confirmation;
        }

        return new ItnResponse(
            String.valueOf(configuration.serviceId()),
            new TransactionsConfirmations(confirmations),
            HashGenerator.generateHash(hashData, configuration)
        );
    }
}

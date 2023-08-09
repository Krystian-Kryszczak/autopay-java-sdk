package krystian.kryszczak.bm.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonClassDescription
@XmlRootElement
public record TransactionsConfirmations(@NotNull TransactionConfirmed transactionConfirmed) implements Serializable {}

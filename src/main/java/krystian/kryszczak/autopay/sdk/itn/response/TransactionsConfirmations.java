package krystian.kryszczak.autopay.sdk.itn.response;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@XmlRootElement
public record TransactionsConfirmations(@NotNull TransactionConfirmed[] transactionConfirmed) implements Serializable {}

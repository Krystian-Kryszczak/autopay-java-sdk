package krystian.kryszczak.bm.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonClassDescription
public record TransactionsConfirmations(@NotNull TransactionConfirmed... transactionConfirmed) implements Serializable {}

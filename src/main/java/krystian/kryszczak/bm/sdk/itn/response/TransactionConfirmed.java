package krystian.kryszczak.bm.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonClassDescription
public record TransactionConfirmed(@NotNull String orderID, @NotNull String confirmation) implements Serializable {}

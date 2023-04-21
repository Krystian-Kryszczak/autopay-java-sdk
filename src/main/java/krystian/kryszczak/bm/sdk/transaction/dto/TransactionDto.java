package krystian.kryszczak.bm.sdk.transaction.dto;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.dto.AbstractDto;
import krystian.kryszczak.bm.sdk.common.util.Translations;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import krystian.kryszczak.bm.sdk.transaction.TransactionData;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

@Getter
public final class TransactionDto extends AbstractDto implements HttpRequestBody {
    private final @NotNull Transaction transaction;
    private final @NotNull Translations.Language htmlFormLanguage = Translations.Language.pl;

    private TransactionDto(@NotNull String gatewayUrl, @NotNull String request, @NotNull Transaction transaction) {
        super(gatewayUrl, request);
        this.transaction = transaction;
    }

    public static TransactionDto create(@NotNull TransactionData<? extends Transaction> transactionData, @NotNull BlueMediaConfiguration configuration) {
        return new TransactionDto(
            transactionData.gatewayUrl(),
            "TODO",
            transactionData.transaction()
        );
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return null; // TODO
    }

    @Override
    public Serializable getRequestData() {
        return null;
    }
}

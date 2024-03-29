package krystian.kryszczak.autopay.sdk.transaction.request;

import krystian.kryszczak.autopay.sdk.common.util.Translations;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class TransactionContinueRequest extends TransactionRequest<TransactionContinue> {
    @ConstructorProperties({"gatewayUrl", "transaction", "language"})
    private TransactionContinueRequest(@NotNull String gatewayUrl, @NotNull TransactionContinue transaction, @NotNull Translations.Language language) {
        super(gatewayUrl, transaction, language);
    }

    @Contract(value = " -> new", pure = true)
    public static Builder.@NotNull Required builder() {
        return new Builder.Required();
    }

    public static final class Builder extends TransactionRequest.Builder<TransactionContinue> {
        public static final class Required extends TransactionRequest.Builder.Required<TransactionContinue> {
            @Override
            public @NotNull WithGatewayUrl setGatewayUrl(@NotNull String gatewayUrl) {
                return new WithGatewayUrl(gatewayUrl);
            }

            @Override
            public @NotNull WithTransaction setTransaction(@NotNull TransactionContinue transaction) {
                return new WithTransaction(transaction);
            }
        }

        public static final class WithGatewayUrl extends TransactionRequest.Builder.WithGatewayUrl<TransactionContinue> {
            private WithGatewayUrl(String gatewayUrl) {
                super(gatewayUrl);
            }

            public @NotNull Completer setTransaction(@NotNull TransactionContinue transaction) {
                return new Completer(super.getGatewayUrl(), transaction);
            }
        }

        public static final class WithTransaction extends TransactionRequest.Builder.WithTransaction<TransactionContinue> {
            private WithTransaction(@NotNull TransactionContinue transaction) {
                super(transaction);
            }

            public @NotNull Completer setGatewayUrl(@NotNull String gatewayUrl) {
                return new Completer(gatewayUrl, super.getTransaction());
            }
        }

        public static final class Completer extends TransactionRequest.Builder.Completer<TransactionContinue> {
            private Completer(@NotNull String gatewayUrl, @NotNull TransactionContinue transaction) {
                super(gatewayUrl, transaction);
            }

            @Override
            public @NotNull TransactionContinueRequest build() {
                return new TransactionContinueRequest(this.gatewayUrl, this.transaction, this.language);
            }
        }
    }
}

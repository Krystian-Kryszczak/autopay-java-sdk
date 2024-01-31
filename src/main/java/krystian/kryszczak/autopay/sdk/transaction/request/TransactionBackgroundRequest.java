package krystian.kryszczak.autopay.sdk.transaction.request;

import krystian.kryszczak.autopay.sdk.common.util.Translations;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class TransactionBackgroundRequest extends TransactionRequest<TransactionBackground> {
    public TransactionBackgroundRequest(@NotNull String gatewayUrl, @NotNull TransactionBackground transaction, @NotNull Translations.Language language) {
        super(gatewayUrl, transaction, language);
    }

    @Contract(value = " -> new", pure = true)
    public static Builder.@NotNull Required builder() {
        return new Builder.Required();
    }

    public static final class Builder extends TransactionRequest.Builder<TransactionBackground> {
        public static final class Required extends TransactionRequest.Builder.Required<TransactionBackground> {
            @Override
            public @NotNull WithGatewayUrl setGatewayUrl(@NotNull String gatewayUrl) {
                return new WithGatewayUrl(gatewayUrl);
            }

            @Override
            public @NotNull WithTransaction setTransaction(@NotNull TransactionBackground transaction) {
                return new WithTransaction(transaction);
            }
        }

        public static final class WithGatewayUrl extends TransactionRequest.Builder.WithGatewayUrl<TransactionBackground> {
            private WithGatewayUrl(String gatewayUrl) {
                super(gatewayUrl);
            }

            public @NotNull Completer setTransaction(@NotNull TransactionBackground transaction) {
                return new Completer(super.getGatewayUrl(), transaction);
            }
        }

        public static final class WithTransaction extends TransactionRequest.Builder.WithTransaction<TransactionBackground> {
            private WithTransaction(@NotNull TransactionBackground transaction) {
                super(transaction);
            }

            public @NotNull Completer setGatewayUrl(@NotNull String gatewayUrl) {
                return new Completer(gatewayUrl, super.getTransaction());
            }
        }

        public static final class Completer extends TransactionRequest.Builder.Completer<TransactionBackground> {
            private Completer(@NotNull String gatewayUrl, @NotNull TransactionBackground transaction) {
                super(gatewayUrl, transaction);
            }

            @Override
            public @NotNull TransactionBackgroundRequest build() {
                return new TransactionBackgroundRequest(this.gatewayUrl, this.transaction, this.language);
            }
        }
    }
}

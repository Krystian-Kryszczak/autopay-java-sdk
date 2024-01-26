package krystian.kryszczak.autopay.sdk.transaction.request;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.util.Translations;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract sealed class TransactionRequest<T extends Transaction> implements Serializable permits TransactionBackgroundRequest, TransactionInitRequest, TransactionContinueRequest {
    private final @NotNull String gatewayUrl;
    private final @NotNull T transaction;
    private final @NotNull Translations.Language htmlFormLanguage;

    public void configure(final @NotNull AutopayConfiguration configuration) {
        transaction.setServiceID(configuration.serviceId());
        final String hash = HashGenerator.generateHash(
            transaction.toArray(),
            configuration
        );
        transaction.setHash(hash);
    }

    protected abstract sealed static class Builder<T extends Transaction> permits TransactionBackgroundRequest.Builder, TransactionContinueRequest.Builder, TransactionInitRequest.Builder {
        protected abstract static sealed class Required<T extends Transaction> permits TransactionBackgroundRequest.Builder.Required, TransactionContinueRequest.Builder.Required, TransactionInitRequest.Builder.Required {
            public abstract @NotNull WithGatewayUrl<T> setGatewayUrl(@NotNull String gatewayUrl);
            public abstract @NotNull WithTransaction<T> setTransaction(@NotNull T transaction);
        }

        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public abstract static sealed class WithGatewayUrl<T extends Transaction> permits TransactionBackgroundRequest.Builder.WithGatewayUrl, TransactionContinueRequest.Builder.WithGatewayUrl, TransactionInitRequest.Builder.WithGatewayUrl {
            @Getter(AccessLevel.PROTECTED)
            private String gatewayUrl;

            public final @NotNull WithGatewayUrl<T> setGatewayUrl(@NotNull String gatewayUrl) {
                this.gatewayUrl = gatewayUrl;
                return this;
            }

            public abstract @NotNull Completer<T> setTransaction(@NotNull T transaction);
        }

        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public abstract static sealed class WithTransaction<T extends Transaction> permits TransactionBackgroundRequest.Builder.WithTransaction, TransactionContinueRequest.Builder.WithTransaction, TransactionInitRequest.Builder.WithTransaction {
            @Getter(AccessLevel.PROTECTED)
            private @NotNull T transaction;

            public abstract @NotNull Completer<T> setGatewayUrl(@NotNull String gatewayUrl);

            public final @NotNull WithTransaction<T> setTransaction(@NotNull T transaction) {
                this.transaction = transaction;
                return this;
            }
        }

        protected abstract static sealed class Completer<T extends Transaction> permits TransactionBackgroundRequest.Builder.Completer, TransactionContinueRequest.Builder.Completer, TransactionInitRequest.Builder.Completer {
            protected @NotNull String gatewayUrl;
            protected @NotNull T transaction;
            protected @NotNull Translations.Language language = Translations.Language.pl;

            protected Completer(@NotNull String gatewayUrl, @NotNull T transaction) {
                this.gatewayUrl = gatewayUrl;
                this.transaction = transaction;
            }

            public @NotNull Completer<T> setGatewayUrl(@NotNull String gatewayUrl) {
                this.gatewayUrl = gatewayUrl;
                return this;
            }

            public @NotNull Completer<T> setTransaction(@NotNull T transaction) {
                this.transaction = transaction;
                return this;
            }

            public @NotNull Completer<T> setLanguage(@NotNull Translations.Language language) {
                this.language = language;
                return this;
            }

            public abstract @NotNull TransactionRequest<T> build();
        }
    }
}

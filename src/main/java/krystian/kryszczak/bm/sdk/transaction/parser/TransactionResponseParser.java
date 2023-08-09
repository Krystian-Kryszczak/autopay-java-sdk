package krystian.kryszczak.bm.sdk.transaction.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.parser.ResponseParser;
import krystian.kryszczak.bm.sdk.hash.HashChecker;
import krystian.kryszczak.bm.sdk.serializer.XmlSerializer;
import krystian.kryszczak.bm.sdk.transaction.*;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    private final Logger logger = LoggerFactory.getLogger(TransactionResponseParser.class);

    public TransactionResponseParser(@NotNull String response, @NotNull BlueMediaConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull Maybe<Transaction> parse() {
        return parse(false);
    }

    public @NotNull Maybe<Transaction> parse(final boolean transactionInit) {
        return Maybe.just(isResponseValid())
            .flatMap(isValid -> {
                if (!isValid) {
                    return Maybe.empty();
                }

                if (transactionInit) {
                    return this.parseTransactionInitResponse();
                }

                return this.parseTransactionBackgroundResponse();
            })
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }

    @SneakyThrows
    private Maybe<TransactionBackground> parseTransactionBackgroundResponse() {
        return Maybe.fromOptional(Optional.ofNullable(new XmlSerializer().deserialize(this.responseBody, TransactionBackground.class)))
            .onErrorComplete()
            .flatMap(transaction -> {
                if (!HashChecker.checkHash(transaction, this.configuration)) {
                    logger.error("Received wrong Hash! (" + transaction.getHash() + ")");
                    return Maybe.empty();
                }
                return Maybe.just(transaction);
            });
    }

    @SneakyThrows
    private Maybe<Transaction> parseTransactionInitResponse() {
        return Single.just(new XmlMapper().readTree(this.responseBody).findValue("status") != null)
            .map(it -> it ? new XmlMapper().readValue(this.responseBody, TransactionContinue.class)
                        : new XmlMapper().readValue(this.responseBody, TransactionInit.class))
            .flatMapMaybe(it -> HashChecker.checkHash(it, this.configuration) ? Maybe.just(it) : Maybe.empty())
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }
}

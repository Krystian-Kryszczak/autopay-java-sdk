package krystian.kryszczak.bm.sdk.transaction.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.BlueMediaPattern;
import krystian.kryszczak.bm.sdk.common.parser.ResponseParser;
import krystian.kryszczak.bm.sdk.hash.HashChecker;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import krystian.kryszczak.bm.sdk.transaction.TransactionBackground;
import krystian.kryszczak.bm.sdk.transaction.TransactionContinue;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Pattern;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    private final Logger logger = LoggerFactory.getLogger(TransactionResponseParser.class);

    public TransactionResponseParser(@NotNull String response, @NotNull BlueMediaConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull Maybe<T> parse() {
        return parse(false);
    }

    public @NotNull Maybe<T> parse(final boolean transactionInit) {
        return Maybe.just(getPaywayFormResponse())
            .flatMap(paywayForm -> {
                if (transactionInit) {
                    return this.parseTransactionInitResponse();
                }
                return this.parseTransactionBackgroundResponse();
            })
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }

    private @NotNull Map<@NotNull String, @NotNull String> getPaywayFormResponse() {
        return Pattern.compile(BlueMediaPattern.PATTERN_PAYWAY)
                .matcher(this.responseBody)
                .groupCount() == 0 ? Map.of() : new XmlMapper().valueToTree(this.responseBody);
    }

    @SneakyThrows
    private Maybe<TransactionBackground> parseTransactionBackgroundResponse() {
        return Single.just(new XmlMapper().readValue(this.responseBody, TransactionBackground.class))
            .onErrorComplete()
            .flatMap(transaction -> {
                if (!HashChecker.instance.checkHash(transaction, this.configuration)) {
                    logger.error("Received wrong Hash! (" + transaction.getHash() + ")");
                    return Maybe.empty();
                }
                return Maybe.just(transaction);
            });
    }

    @SneakyThrows
    private Maybe<T> parseTransactionInitResponse() {
        return (Maybe<T>) Single.just(new XmlMapper().valueToTree(this.responseBody).findValue("redirectUrl") != null)
            .map(it -> it ? new XmlMapper().readValue(this.responseBody, TransactionContinue.class)
                        : new XmlMapper().readValue(this.responseBody, TransactionInit.class))
            .flatMapMaybe(it -> HashChecker.instance.checkHash(it, this.configuration) ? Maybe.just(it) : Maybe.empty())
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }
}

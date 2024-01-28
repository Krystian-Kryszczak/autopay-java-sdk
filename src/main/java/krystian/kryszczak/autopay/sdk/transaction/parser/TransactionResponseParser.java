package krystian.kryszczak.autopay.sdk.transaction.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.parser.ResponseParser;
import krystian.kryszczak.autopay.sdk.hash.HashChecker;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    private final Logger logger = LoggerFactory.getLogger(TransactionResponseParser.class);

    public TransactionResponseParser(@NotNull String response, @NotNull AutopayConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull Publisher<? extends Transaction> parse() {
        return parse(false);
    }

    public @NotNull Publisher<? extends Transaction> parse(boolean transactionInit) {
        try {
            if (!isResponseValid()) {
                return Mono.empty();
            } else if (transactionInit) {
                return this.parseTransactionInitResponse();
            } else {
                return this.parseTransactionBackgroundResponse();
            }
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return Mono.empty();
        }
    }

    @SneakyThrows
    private Mono<TransactionBackground> parseTransactionBackgroundResponse() {
        return Mono.justOrEmpty(new XmlSerializer().deserialize(this.responseBody, TransactionBackground.class))
            .onErrorComplete()
            .flatMap(transaction -> {
                if (!HashChecker.checkHash(transaction, this.configuration)) {
                    logger.error("Received wrong Hash! (" + transaction.getHash() + ")");
                    return Mono.empty();
                }
                return Mono.just(transaction);
            });
    }

    @SneakyThrows
    private Mono<Transaction> parseTransactionInitResponse() {
        return Mono.just(new XmlMapper().readTree(this.responseBody).findValue("status") != null)
            .map(it -> {
                try {
                    return it ? new XmlMapper().readValue(this.responseBody, TransactionContinue.class)
                            : new XmlMapper().readValue(this.responseBody, TransactionInit.class);
                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                    return null;
                }
            })
            .flatMap(it -> HashChecker.checkHash(it, this.configuration) ? Mono.just(it) : Mono.empty())
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }
}

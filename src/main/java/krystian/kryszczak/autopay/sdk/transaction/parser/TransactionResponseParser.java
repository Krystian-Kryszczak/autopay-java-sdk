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
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    private final Logger logger = LoggerFactory.getLogger(TransactionResponseParser.class);

    public TransactionResponseParser(@NotNull String response, @NotNull AutopayConfiguration configuration) {
        super(response, configuration);
    }

    public @Nullable Transaction parse() {
        return parse(false);
    }

    public @Nullable Transaction parse(boolean transactionInit) {
        try {
            if (isResponseInvalid()) {
                return null;
            } else if (transactionInit) {
                return this.parseTransactionInitResponse();
            } else {
                return this.parseTransactionBackgroundResponse();
            }
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return null;
        }
    }

    @SneakyThrows
    private TransactionBackground parseTransactionBackgroundResponse() {
        try {
            final var transaction = new XmlSerializer().deserialize(this.responseBody, TransactionBackground.class);
            if (transaction != null && !HashChecker.checkHash(transaction, this.configuration)) {
                logger.error("Received wrong Hash! ({})", transaction.getHash());
                return null;
            }
            return transaction;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @SneakyThrows
    private Transaction parseTransactionInitResponse() {
        try {
            final Transaction transaction = new XmlMapper().readTree(this.responseBody).findValue("status") != null
                ? new XmlMapper().readValue(this.responseBody, TransactionContinue.class)
                : new XmlMapper().readValue(this.responseBody, TransactionInit.class);
            if (!HashChecker.checkHash(transaction, this.configuration)) {
                logger.error("Received wrong Hash! ({})", transaction.getHash());
                return null;
            }
            return transaction;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

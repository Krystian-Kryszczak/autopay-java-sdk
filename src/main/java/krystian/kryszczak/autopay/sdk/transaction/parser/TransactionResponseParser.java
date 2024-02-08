package krystian.kryszczak.autopay.sdk.transaction.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.AutopayPattern;
import krystian.kryszczak.autopay.sdk.common.exception.HashException;
import krystian.kryszczak.autopay.sdk.common.exception.XmlException;
import krystian.kryszczak.autopay.sdk.common.parser.ResponseParser;
import krystian.kryszczak.autopay.sdk.hash.HashChecker;
import krystian.kryszczak.autopay.sdk.serializer.jackson.XmlSerializer;
import krystian.kryszczak.autopay.sdk.transaction.*;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static krystian.kryszczak.autopay.sdk.util.StringUtils.unescapeHtml;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    public TransactionResponseParser(@NotNull String response, @NotNull AutopayConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull Transaction parse() throws RuntimeException {
        return parse(false);
    }

    public @NotNull Transaction parse(boolean transactionInit) throws RuntimeException {
        checkResponseError();

        if (isPaywayFormResponse()) {
            return new PaywayFormResponse(unescapeHtml(this.responseBody));
        } else if (transactionInit) {
            return this.parseTransactionInitResponse();
        } else {
            return this.parseTransactionBackgroundResponse();
        }
    }

    private boolean isPaywayFormResponse() {
        return Pattern.compile(AutopayPattern.PATTERN_PAYWAY, Pattern.DOTALL).matcher(this.responseBody).find();
    }

    @SneakyThrows
    private TransactionBackground parseTransactionBackgroundResponse() {
        final var transaction = new XmlSerializer().deserialize(this.responseBody, TransactionBackground.class);

        validateTransaction(transaction);

        return transaction;
    }

    @SneakyThrows
    private Transaction parseTransactionInitResponse() {
        final Transaction transaction = new XmlMapper().readTree(this.responseBody).findValue("redirecturl") != null
            ? new XmlSerializer().deserialize(this.responseBody, TransactionContinue.class)
            : new XmlSerializer().deserialize(this.responseBody, TransactionInit.class);

        validateTransaction(transaction);

        return transaction;
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw XmlException.xmlGeneralError("Transaction deserialize failed!");
        } else if (!HashChecker.checkHash(transaction, this.configuration)) {
            throw new HashException();
        }
    }
}

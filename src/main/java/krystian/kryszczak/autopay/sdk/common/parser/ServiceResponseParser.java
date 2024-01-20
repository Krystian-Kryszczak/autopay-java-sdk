package krystian.kryszczak.autopay.sdk.common.parser;

import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

public final class ServiceResponseParser extends ResponseParser<String> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceResponseParser.class);

    private final XmlSerializer xmlSerializer = new XmlSerializer();

    public ServiceResponseParser(@NotNull String responseBody, @NotNull AutopayConfiguration configuration) {
        super(responseBody, configuration);
    }

    public <T extends Serializable> Maybe<T> parseListResponse(Class<T> type) {
        if (!isResponseValid()) {
            return Maybe.empty();
        }

        try {
            return Maybe.fromOptional(Optional.ofNullable(xmlSerializer.deserialize(this.responseBody, type)));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return Maybe.empty();
        }
    }
}

package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public final class ServiceResponseParser extends ResponseParser<String> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceResponseParser.class);

    private final XmlSerializer xmlSerializer = new XmlSerializer();

    public ServiceResponseParser(@NotNull String responseBody, @NotNull AutopayConfiguration configuration) {
        super(responseBody, configuration);
    }

    public <T extends Serializable> Publisher<T> parseListResponse(Class<T> type) {
        if (!isResponseValid()) {
            return Mono.empty();
        }

        try {
            return Mono.justOrEmpty(xmlSerializer.deserialize(this.responseBody, type));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return Mono.empty();
        }
    }
}

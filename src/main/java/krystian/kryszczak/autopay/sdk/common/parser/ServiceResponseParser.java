package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public final class ServiceResponseParser extends ResponseParser<String> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceResponseParser.class);

    private final Serializer serializer;

    public ServiceResponseParser(@NotNull String responseBody, @NotNull AutopayConfiguration configuration, @NotNull Serializer serializer) {
        super(responseBody, configuration);
        this.serializer = serializer;
    }

    public ServiceResponseParser(@NotNull String responseBody, @NotNull AutopayConfiguration configuration) {
        this(responseBody, configuration, new XmlSerializer());
    }

    public <T extends Serializable> @Nullable T parseListResponse(Class<T> type) {
        if (isResponseInvalid()) {
            logger.info("Received invalid service response (type: {}): {}", type.getSimpleName(), this.responseBody);
            return null;
        }

        try {
            return serializer.deserialize(this.responseBody, type);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

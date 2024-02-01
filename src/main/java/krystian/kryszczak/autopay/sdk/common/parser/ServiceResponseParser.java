package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.exception.XmlException;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;
import org.jetbrains.annotations.NotNull;
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

    public <T extends Serializable> @NotNull T parseListResponse(Class<T> type) throws XmlException {
        checkResponseError();

        try {
            final T result = serializer.deserialize(this.responseBody, type);
            if (result == null) throw XmlException.xmlGeneralError("Parsing ListResponse failed!");
            return result;
        } catch (RuntimeException e) {
            final XmlException xmlException = XmlException.xmlParseError(e);
            logger.error(xmlException.getMessage(), xmlException);
            throw xmlException;
        }
    }
}

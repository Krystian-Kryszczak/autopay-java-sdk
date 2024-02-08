package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.exception.XmlException;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public final class ServiceResponseParser extends ResponseParser<String> {
    public ServiceResponseParser(@NotNull String responseBody,
            @NotNull AutopayConfiguration configuration, @NotNull Serializer serializer) {
        super(responseBody, configuration, serializer);
    }

    public <T extends Serializable> @NotNull T parseListResponse(Class<T> type) throws XmlException {
        checkResponseError();

        try {
            final T result = serializer.deserialize(this.responseBody, type);
            if (result == null)
                throw XmlException.xmlGeneralError("Parsing " + type.getSimpleName() + " failed!");
            return result;
        } catch (RuntimeException e) {
            throw XmlException.xmlParseError(e);
        }
    }
}

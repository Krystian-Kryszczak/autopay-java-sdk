package krystian.kryszczak.bm.sdk.common.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public final class ServiceResponseParser extends ResponseParser<String> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceResponseParser.class);

    private final XmlMapper xmlMapper = new XmlMapper();

    public ServiceResponseParser(@NotNull String responseBody, @NotNull BlueMediaConfiguration credentials) {
        super(responseBody, credentials);
    }

    public <T extends Serializable> Maybe<T> parseListResponse(Class<T> type) {
        if (!isResponseValid()) {
            return Maybe.empty();
        }

        try {
            return Maybe.just(xmlMapper.readValue(this.responseBody, type));
        } catch (RuntimeException | JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return Maybe.empty();
        }
    }
}

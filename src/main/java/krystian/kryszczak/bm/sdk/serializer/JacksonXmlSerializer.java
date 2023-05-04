package krystian.kryszczak.bm.sdk.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public final class JacksonXmlSerializer implements Serializer {
    private static final Logger logger = LoggerFactory.getLogger(JacksonXmlSerializer.class);

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public <T extends HttpRequestBody> @Nullable T serializeDataToDto(@NotNull Object[] data, @NotNull Class<T> type) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public @Nullable Object[] toArray(@NotNull Object object) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public <T> @Nullable T fromArray(@NotNull Object[] data, Class<T> type) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public <T extends Serializable> @Nullable T deserializeXml(@NotNull String xml, @NotNull Class<T> type) {
        try {
            return mapper.readValue(xml, type);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public @Nullable String toXml(@NotNull Serializable value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

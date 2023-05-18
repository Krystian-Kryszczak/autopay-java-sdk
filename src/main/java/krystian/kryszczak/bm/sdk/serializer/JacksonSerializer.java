package krystian.kryszczak.bm.sdk.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public abstract sealed class JacksonSerializer implements Serializer permits JsonSerializer, XmlSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JacksonSerializer.class);

    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public @Nullable Object[] toArray(@NotNull Serializable data) {
        final var tree = mapper.readTree(mapper.writeValueAsString(data));
        final var fields = tree.fields();
        final List<Object> values = new LinkedList<>();
        while (fields.hasNext()) {
            var value = fields.next().getValue().asText();
            values.add(value);
        }
        return values.toArray();
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

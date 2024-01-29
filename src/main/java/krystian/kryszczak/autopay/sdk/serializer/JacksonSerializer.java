package krystian.kryszczak.autopay.sdk.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

public abstract sealed class JacksonSerializer implements Serializer permits JsonSerializer, XmlSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JacksonSerializer.class);

    private final ObjectMapper mapper;

    public JacksonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

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
    public <T extends Serializable> @Nullable T deserialize(@NotNull String data, @NotNull Class<T> type) {
        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public @Nullable String serialize(@NotNull Serializable value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

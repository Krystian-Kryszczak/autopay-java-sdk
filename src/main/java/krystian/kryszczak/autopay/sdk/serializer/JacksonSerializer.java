package krystian.kryszczak.autopay.sdk.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

    @SneakyThrows
    @Override
    public @Nullable Map<String, String> toMap(@NotNull Serializable data) {
        final var mapType = TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class);
        if (data instanceof String str) {
            return mapper.readValue(str, mapType);
        }
        return mapper.convertValue(data, mapType);
    }

    @SneakyThrows
    @Override
    public <T> @Nullable T fromMap(@NotNull Map<String, Object> data, Class<T> type) {
        final String content = mapper.writeValueAsString(data)
            .replaceAll(data.getClass().getSimpleName(), type.getSimpleName());
        return mapper.readValue(content, type);
    }

    @Override
    public <T extends Serializable> @Nullable T deserialize(@NotNull String xml, @NotNull Class<T> type) {
        try {
            return mapper.readValue(xml, type);
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

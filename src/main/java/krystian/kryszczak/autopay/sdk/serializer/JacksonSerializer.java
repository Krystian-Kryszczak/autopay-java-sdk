package krystian.kryszczak.autopay.sdk.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract sealed class JacksonSerializer implements Serializer permits JsonSerializer, XmlSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JacksonSerializer.class);

    private final ObjectMapper mapper;

    public JacksonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.mapper.registerModule(new JavaTimeModule());
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

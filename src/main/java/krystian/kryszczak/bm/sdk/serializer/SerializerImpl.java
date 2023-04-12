package krystian.kryszczak.bm.sdk.serializer;

import krystian.kryszczak.bm.sdk.common.dto.AbstractDto;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public final class SerializerImpl implements Serializer { // TODO

    private final String XML_TYPE = "xml";

    @Override
    public <T extends AbstractDto> @NotNull T serializeDataToDto(@NotNull Object[] data, @NotNull Class<T> type) {
        return null;
    }

    @Override
    public @NotNull Object[] toArray(@NotNull Object object) {
        return new Object[0];
    }

    @Override
    public <T> @NotNull T fromArray(@NotNull Object[] data, Class<T> type) {
        return null;
    }

    @Override
    public <T extends Serializable> @NotNull T deserializeXml(@NotNull String xml, @NotNull Class<T> type) {
        return null;
    }

    @Override
    public @NotNull String toXml(@NotNull Object data) {
        return null;
    }
}

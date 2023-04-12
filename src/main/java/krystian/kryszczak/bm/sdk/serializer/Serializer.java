package krystian.kryszczak.bm.sdk.serializer;

import krystian.kryszczak.bm.sdk.common.dto.AbstractDto;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface Serializer {
    @NotNull <T extends AbstractDto> T serializeDataToDto(@NotNull Object[] data, @NotNull Class<T> type);
    @NotNull Object[] toArray(@NotNull Object object);
    @NotNull <T> T fromArray(@NotNull Object[] data, Class<T> type);
    @NotNull <T extends Serializable> T deserializeXml(@NotNull String xml, @NotNull Class<T> type);
    @NotNull String toXml(@NotNull Object data);
}

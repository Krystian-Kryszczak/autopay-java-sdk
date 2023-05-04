package krystian.kryszczak.bm.sdk.serializer;

import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface Serializer {
    @Nullable <T extends HttpRequestBody> T serializeDataToDto(@NotNull Object[] data, @NotNull Class<T> type);
    @Nullable Object[] toArray(@NotNull Object object);
    @Nullable <T> T fromArray(@NotNull Object[] data, Class<T> type);
    @Nullable <T extends Serializable> T deserializeXml(@NotNull String xml, @NotNull Class<T> type);
    @Nullable String toXml(@NotNull Serializable data);
}

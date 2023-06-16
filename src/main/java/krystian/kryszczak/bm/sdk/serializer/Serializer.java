package krystian.kryszczak.bm.sdk.serializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Map;

public interface Serializer {
    @Nullable Object[] toArray(@NotNull Serializable data);
    @Nullable Map<String, String> toMap(@NotNull Serializable data);
    @Nullable <T extends Serializable> T deserialize(@NotNull String xml, @NotNull Class<T> type);
    @Nullable String serialize(@NotNull Serializable data);
}

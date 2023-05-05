package krystian.kryszczak.bm.sdk.serializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface Serializer {
    @Nullable Object[] toArray(@NotNull Serializable data);
    @Nullable <T extends Serializable> T deserializeXml(@NotNull String xml, @NotNull Class<T> type);
    @Nullable String toXml(@NotNull Serializable data);
}

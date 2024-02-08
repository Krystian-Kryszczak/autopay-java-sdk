package krystian.kryszczak.autopay.sdk.serializer;

import krystian.kryszczak.autopay.sdk.serializer.jackson.XmlSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface Serializer {
    @Nullable Object[] toArray(@NotNull Serializable data);
    @Nullable <T extends Serializable> T deserialize(@NotNull String data, @NotNull Class<T> type);
    @Nullable String serialize(@NotNull Serializable data);

    @Contract(" -> new")
    static @NotNull Serializer createDefault() {
        return new XmlSerializer();
    }
}

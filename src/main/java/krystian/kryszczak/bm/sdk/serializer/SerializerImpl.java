package krystian.kryszczak.bm.sdk.serializer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public final class SerializerImpl implements Serializer {

    private static final String XML_TYPE = "xml";
    private static final XmlMapper mapper = new XmlMapper();

    @Override
    public <T extends HttpRequestBody> @NotNull T serializeDataToDto(@NotNull Object[] data, @NotNull Class<T> type) {
        return null;
    }

    @Override
    public @NotNull Object[] toArray(@NotNull Object object) { // TODO
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

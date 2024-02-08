package krystian.kryszczak.autopay.sdk.serializer.jackson;

import com.fasterxml.jackson.databind.json.JsonMapper;

public final class JsonSerializer extends JacksonSerializer {
    public JsonSerializer() {
        super(new JsonMapper());
    }
}

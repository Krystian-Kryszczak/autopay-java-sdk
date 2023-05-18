package krystian.kryszczak.bm.sdk.serializer;

import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class SerializerTest {
    private final Serializer serializer = new XmlSerializer();

    @Test
    void serializerTest() throws IOException {
        final String data = Files.readString(Paths.get("src/test/resources/fixtures/regulation-list/RegulationList.xml"), StandardCharsets.UTF_8);

        final var deserialized = serializer.deserializeXml(data, RegulationListResponse.class);
        assertNotNull(deserialized);

        final var serialized = serializer.toXml(deserialized);
        assertEquals(data, serialized);
    }
}

package krystian.kryszczak.autopay.sdk.serializer;

import fixtures.itn.ItnFixture;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class XmlSerializerTest {
    @Test
    public void testDeserializeReturnsItnResponse() {
        final String itnResponse = ItnFixture.getItnResponse();
        final ItnResponse deserialized = new XmlSerializer().deserialize(itnResponse, ItnResponse.class);

        assertNotNull(deserialized);
        assertInstanceOf(ItnResponse.class, deserialized);
        assertThat(deserialized.toXml())
            .and(itnResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @ParameterizedTest
    @MethodSource("wrongXmlProvider")
    public void testParseReturnsNullOnWrongXml(String wrongXml) {
        assertNull(new XmlSerializer().deserialize(wrongXml, Serializable.class));
    }

    @Contract(value = " -> new", pure = true)
    public String @NotNull [] wrongXmlProvider() {
        return new String[] {
            "ERROR",
            "",
            "wrong_xml",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        };
    }
}

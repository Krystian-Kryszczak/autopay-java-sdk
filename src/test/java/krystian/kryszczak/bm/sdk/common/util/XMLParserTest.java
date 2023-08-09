package krystian.kryszczak.bm.sdk.common.util;

import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import krystian.kryszczak.bm.sdk.serializer.XmlSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class XMLParserTest {
    @Test
    public void testDeserializeReturnsItnResponse () {
        final String itnResponse = fixtures.itn.Itn.getItnResponse();
        final ItnResponse xmlElement = new XmlSerializer().deserialize(itnResponse, ItnResponse.class);

        assertNotNull(xmlElement);
        assertInstanceOf(ItnResponse.class, xmlElement);
        assertThat(itnResponse)
            .and(xmlElement.toXml())
            .ignoreWhitespace()
            .areIdentical();
    }

    @ParameterizedTest
    @MethodSource("wrongXmlProvider")
    public void testParseReturnsEmptyMaybeOnWrongXml(String wrongXml) {
        assertNull(new XmlSerializer().deserialize(wrongXml, Serializable.class));
    }

    public String[] wrongXmlProvider() {
        return new String[] {
            "ERROR",
            "",
            "wrong_xml",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        };
    }
}

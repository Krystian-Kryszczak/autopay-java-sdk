package krystian.kryszczak.bm.sdk.common.parser;

import krystian.kryszczak.bm.sdk.common.exception.XmlException;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public final class XMLParser {
    public static @NotNull Document parse(@NotNull String xml) {
        try {
            return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw XmlException.xmlParseError(e);
        }
    }
}

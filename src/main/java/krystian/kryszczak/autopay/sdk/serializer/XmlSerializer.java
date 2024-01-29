package krystian.kryszczak.autopay.sdk.serializer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

public final class XmlSerializer extends JacksonSerializer {
    public XmlSerializer() {
        super(
            XmlMapper.builder()
                .defaultUseWrapper(false)
                .configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .configure(SerializationFeature.WRAP_ROOT_VALUE, true)
                .build()
        );
    }
}

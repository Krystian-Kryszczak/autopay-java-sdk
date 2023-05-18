package krystian.kryszczak.bm.sdk.serializer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class XmlSerializer extends JacksonSerializer {
    public XmlSerializer() {
        super(new XmlMapper());
    }
}

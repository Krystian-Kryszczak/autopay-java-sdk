package krystian.kryszczak.bm.sdk.common.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.BlueMediaPattern;
import krystian.kryszczak.bm.sdk.common.exception.XmlException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public abstract class ResponseParser<T extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseParser.class);

    protected final @NotNull String responseBody;
    protected final @NotNull BlueMediaConfiguration configuration;

    protected boolean isResponseValid() {
        if (Pattern.compile(BlueMediaPattern.PATTERN_XML_ERROR).matcher(this.responseBody).matches()) {
            final var xmlData = new XmlMapper().valueToTree(this.responseBody);

            try {
                throw XmlException.xmlBodyContainsError(xmlData.get("name").asText());
            } catch (XmlException e) {
                logger.error(e.getMessage(), e);
            }
            return false;
        }

        if (Pattern.compile(BlueMediaPattern.PATTERN_GENERAL_ERROR).matcher(this.responseBody).matches()) {
            try {
                throw XmlException.xmlGeneralError(this.responseBody);
            } catch (XmlException e) {
                logger.error(e.getMessage(), e);
            }
            return false;
        }

        return true;
    }
}

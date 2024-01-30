package krystian.kryszczak.autopay.sdk.common.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.AutopayPattern;
import krystian.kryszczak.autopay.sdk.common.exception.XmlException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResponseParser<T extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseParser.class);

    protected final @NotNull String responseBody;
    protected final @NotNull AutopayConfiguration configuration;

    protected boolean isResponseInvalid() {
        if (Pattern.compile(AutopayPattern.PATTERN_XML_ERROR).matcher(this.responseBody).matches()) {
            final var xmlData = new XmlMapper().valueToTree(this.responseBody);

            try {
                throw XmlException.xmlBodyContainsError(xmlData.get("name").asText());
            } catch (XmlException e) {
                logger.error(e.getMessage(), e);
            }
            return true;
        }

        if (Pattern.compile(AutopayPattern.PATTERN_GENERAL_ERROR).matcher(this.responseBody).matches()) {
            try {
                throw XmlException.xmlGeneralError(this.responseBody);
            } catch (XmlException e) {
                logger.error(e.getMessage(), e);
            }
            return true;
        }

        return false;
    }
}

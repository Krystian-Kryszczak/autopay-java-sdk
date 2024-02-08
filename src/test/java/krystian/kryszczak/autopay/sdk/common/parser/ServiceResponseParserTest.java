package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.common.exception.XmlException;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.BaseTestCase;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class ServiceResponseParserTest extends BaseTestCase {
    @ParameterizedTest
    @MethodSource("wrongXmlProvider")
    public void parseListResponseReturnsEmptyMonoOnWrongXml(String responseBody) {
        final ServiceResponseParser parser = new ServiceResponseParser(
            responseBody,
            getTestConfiguration(),
            Serializer.createDefault()
        );

        assertThrows(XmlException.class, () -> parser.parseListResponse(PaywayListResponse.class));
    }

    @Contract(value = " -> new", pure = true)
    public String @NotNull [] wrongXmlProvider() {
        return new String[] {
            "ERROR",
            "<error>something</error>"
        };
    }
}

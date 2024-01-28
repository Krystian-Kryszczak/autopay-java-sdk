package krystian.kryszczak.autopay.sdk.common.parser;

import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class ServiceResponseParserTest extends BaseTestCase {
    @ParameterizedTest
    @MethodSource("wrongXmlProvider")
    public void testParseListResponseReturnsEmptyMaybeOnWrongXml(String responseBody) {
        final var parser = new ServiceResponseParser(
            responseBody,
            getConfigurationStub()
        );

        assertTrue(Mono.fromDirect(parser.parseListResponse(PaywayListResponse.class)).blockOptional().isEmpty());
    }

    public String[] wrongXmlProvider() {
        return new String[] {
            "ERROR",
            "<error>something</error>"
        };
    }
}

package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class StringUtilsTest {
    @Test
    public void bin2hexTest() {
        assertEquals("48656c6c6f20776f726c6421", StringUtils.bin2hex("Hello world!"));
    }

    @Test
    public void hex2binTest() {
        assertEquals("Hello world!", StringUtils.hex2bin("48656c6c6f20776f726c6421"));
    }

    @Test
    public void bin2hexAndHex2binTest() {
        final String text = "Hello world!";
        final String hex = StringUtils.bin2hex(text);

        assertEquals(text, StringUtils.hex2bin(hex));
    }

    @ParameterizedTest
    @MethodSource("stringsToCapitalizeProvider")
    public void capitalizeTest(String src, String excepted) {
        assertEquals(excepted, StringUtils.capitalize(src));
    }

    @Test
    public void unescapeHtmlReturnsExceptedString() {
        final String given = "&lt;form action=&quot;https://pg-accept.blue.pl/gateway/test/index.jsp&quot; " +
            "name=&quot;formGoPBL&quot; method=&quot;POST&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;transaction&quot; value=&quot;758519&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;merchantId&quot; value=&quot;6002&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;orderId&quot; value=&quot;9IA5UZEQ&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;email&quot; value=&quot;test@test.test&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;amount&quot; value=&quot;5.12&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;currency&quot; value=&quot;PLN&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;firstName&quot; value=&quot;Jan&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;lastName&quot; value=&quot;Kowalski&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;street&quot; value=&quot;Jasna&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;streetHouseNo&quot; value=&quot;6&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;streetStaircaseNo&quot; value=&quot;A&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;streetPremiseNo&quot; value=&quot;3&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;city&quot; value=&quot;Warszawa&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;postalCode&quot; value=&quot;10-234&quot;&gt;&lt;input type=&quot;hidden&quot; " +
            "name=&quot;title&quot; value=&quot;BPID:9IA5UZEQ Transakcja testowa 1593092942&quot;&gt;&lt;input " +
            "type=&quot;hidden&quot; name=&quot;senderNRB&quot; value=&quot;96109010301793218160815294&quot;&gt;&lt;" +
            "input type=&quot;hidden&quot; name=&quot;receiverNRB&quot; value=&quot;88109086515278696234834868&quot;" +
            "&gt;&lt;input type=&quot;hidden&quot; name=&quot;shopName&quot; value=&quot;Blue Services PrestaShop1.7 " +
            "test&quot;&gt;&lt;input type=&quot;hidden&quot; name=&quot;date&quot; value=&quot;2020-06-25 15:54&quot;" +
            "&gt;&lt;input type=&quot;hidden&quot; name=&quot;accountingDate&quot; value=&quot;2020-06-25&quot;&gt;" +
            "&lt;input type=&quot;hidden&quot; name=&quot;language&quot; value=&quot;PL&quot;&gt;&lt;/form&gt;" +
            "&lt;script type=&quot;text/javascript&quot;&gt;document.formGoPBL.submit();&lt;/script&gt;";

        final String excepted = "<form action=\"https://pg-accept.blue.pl/gateway/test/index.jsp\" " +
            "name=\"formGoPBL\" method=\"POST\"><input type=\"hidden\" name=\"transaction\" value=\"758519\">" +
            "<input type=\"hidden\" name=\"merchantId\" value=\"6002\"><input type=\"hidden\" name=\"orderId\" " +
            "value=\"9IA5UZEQ\"><input type=\"hidden\" name=\"email\" value=\"test@test.test\">" +
            "<input type=\"hidden\" name=\"amount\" value=\"5.12\"><input type=\"hidden\" name=\"currency\" " +
            "value=\"PLN\"><input type=\"hidden\" name=\"firstName\" value=\"Jan\"><input type=\"hidden\" " +
            "name=\"lastName\" value=\"Kowalski\"><input type=\"hidden\" name=\"street\" value=\"Jasna\">" +
            "<input type=\"hidden\" name=\"streetHouseNo\" value=\"6\"><input type=\"hidden\" " +
            "name=\"streetStaircaseNo\" value=\"A\"><input type=\"hidden\" name=\"streetPremiseNo\" value=\"3\">" +
            "<input type=\"hidden\" name=\"city\" value=\"Warszawa\"><input type=\"hidden\" name=\"postalCode\" " +
            "value=\"10-234\"><input type=\"hidden\" name=\"title\" value=\"BPID:9IA5UZEQ Transakcja testowa " +
            "1593092942\"><input type=\"hidden\" name=\"senderNRB\" value=\"96109010301793218160815294\">" +
            "<input type=\"hidden\" name=\"receiverNRB\" value=\"88109086515278696234834868\"><input type=\"hidden\" " +
            "name=\"shopName\" value=\"Blue Services PrestaShop1.7 test\"><input type=\"hidden\" name=\"date\" " +
            "value=\"2020-06-25 15:54\"><input type=\"hidden\" name=\"accountingDate\" value=\"2020-06-25\">" +
            "<input type=\"hidden\" name=\"language\" value=\"PL\"></form><script type=\"text/javascript\">" +
            "document.formGoPBL.submit();</script>";

        assertEquals(excepted, StringUtils.unescapeHtml(given));
    }

    @Contract(" -> new")
    public static @Unmodifiable List<Arguments> stringsToCapitalizeProvider() {
        return List.of(
            Arguments.of("hello world!", "Hello world!"),
            Arguments.of("autopay!", "Autopay!"),
            Arguments.of("Autopay SDK", "Autopay SDK")
        );
    }
}

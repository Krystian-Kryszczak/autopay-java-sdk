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

    @Contract(" -> new")
    public static @Unmodifiable List<Arguments> stringsToCapitalizeProvider() {
        return List.of(
            Arguments.of("hello world!", "Hello world!"),
            Arguments.of("autopay!", "Autopay!"),
            Arguments.of("Autopay SDK", "Autopay SDK")
        );
    }
}

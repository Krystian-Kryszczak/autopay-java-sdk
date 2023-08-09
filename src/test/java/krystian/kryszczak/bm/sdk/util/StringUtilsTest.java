package krystian.kryszczak.bm.sdk.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class StringUtilsTest {

    @Test
    public void bin2hex_test() {
        assertEquals("48656c6c6f20776f726c6421", StringUtils.bin2hex("Hello world!"));
    }

    @Test
    public void hex2bin_test() {
        assertEquals("Hello world!", StringUtils.hex2bin("48656c6c6f20776f726c6421"));
    }

    @Test
    public void bin2hex_and_hex2bin_test() {
        final String text = "Hello world!";
        final String hex = StringUtils.bin2hex(text);

        assertEquals(text, StringUtils.hex2bin(hex));
    }
}

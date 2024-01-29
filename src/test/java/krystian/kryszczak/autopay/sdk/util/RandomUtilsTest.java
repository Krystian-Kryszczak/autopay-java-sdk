package krystian.kryszczak.autopay.sdk.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class RandomUtilsTest {

    @Test
    public void randomMessageIdTest() throws InterruptedException {
        String previously = null;

        for (int i = 0; i < 1000; i++) {
            TimeUnit.MILLISECONDS.sleep(5);

            final String actual = RandomUtils.randomMessageId();

            assertNotEquals(previously, actual);
            assertEquals(RandomUtils.MESSAGE_ID_LENGTH, actual.length());

            previously = actual;
        }
    }
}

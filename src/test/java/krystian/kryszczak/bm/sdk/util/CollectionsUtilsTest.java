package krystian.kryszczak.bm.sdk.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public final class CollectionsUtilsTest {
    @Test
    public void nonNullMapFromArrayWithArrayWithNullsInputShouldNotThrowsAnyException() {
        assertDoesNotThrow(() -> CollectionsUtils.nonNullMapFromArray(null, null));
    }
}

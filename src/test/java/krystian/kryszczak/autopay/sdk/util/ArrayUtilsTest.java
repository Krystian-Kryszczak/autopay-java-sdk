package krystian.kryszczak.autopay.sdk.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class ArrayUtilsTest {
    @Test
    public void filterNotNullReturnsExceptedArray() {
        final String[] notNullArray = ArrayUtils.filterNotNull("Hello ", null, "world!");

        assertNotNull(notNullArray);
        assertEquals(2, notNullArray.length);
        assertArrayEquals(new String[] { "Hello ", "world!" }, notNullArray);
    }

    @Test
    public void mergeArraysReturnsExceptedArray() {
        final String[] mergedArray = ArrayUtils.merge(new String[] { "zero", "one", "two" }, "three", "four");

        assertNotNull(mergedArray);
        assertEquals(5, mergedArray.length);
        assertArrayEquals(new String[] { "zero", "one", "two", "three", "four" }, mergedArray);
    }

    @Test
    public void mergeArraysIfAbsentReturnsExceptedArray() {
        final String[] mergedArray = ArrayUtils.mergeArraysIfAbsent(
            new String[] { "zero", "one", "two" },
            "one", "two", "zero", "three", "four", "four"
        );

        assertNotNull(mergedArray);
        assertEquals(5, mergedArray.length);
        assertArrayEquals(new String[] { "zero", "one", "two", "three", "four" }, mergedArray);
    }

    @Test
    public void flatMapArraysReturnsExceptedArray() {
        final String[] flattedArray = ArrayUtils.flatMap(
            new String[] { "zero", "one", "two" },
            new String[] { "three", "four", "five" }
        );

        assertNotNull(flattedArray);
        assertEquals(6, flattedArray.length);
        assertArrayEquals(new String[] { "zero", "one", "two", "three", "four", "five" }, flattedArray);
    }

    @Test
    public void reverseArrayReturnsExceptedArray() {
        final String[] mergedArray = ArrayUtils.reverse("four", "three", "two", "one", "zero");

        assertNotNull(mergedArray);
        assertEquals(5, mergedArray.length);
        assertArrayEquals(new String[] { "zero", "one", "two", "three", "four" }, mergedArray);
    }
}

package krystian.kryszczak.autopay.sdk.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class MapUtilsTest {
    @Test
    public void testNotNullMapOfWithNullsInputDoesNotThrowsException() {
        assertDoesNotThrow(() -> MapUtils.notNullMapOf(null, null));
    }

    @Test
    public void testNotNullMapOfWithUnpairedInputThrowsException() {
        assertThrows(InvalidParameterException.class, () -> MapUtils.notNullMapOf(null, null, null));
    }

    @Test
    public void testNotNullMapOfReturnsExceptedMap() {
        final Map<String, String> excepted = new LinkedHashMap<>();
        excepted.put("Hello", "world!");
        excepted.put("Autopay", "SDK");
        excepted.put("Java", "VM");

        assertEquals(excepted, MapUtils.notNullMapOf("Hello", "world!", "Autopay", "SDK", "Java", "VM"));
    }

    @Test
    public void testMergeMapsReturnsExceptedMap() {
        final var mergedMap = MapUtils.merge(
            Map.of("Hello world!", 1),
            Map.of("Autopay SDK", 1f),
            Map.of(
                "Hello Autopay SDK", 1.0,
                "Autopay Java SDK", 1L
            )
        );

        assertNotNull(mergedMap);
        assertEquals(4, mergedMap.size());
        assertEquals(
            Map.of(
            "Hello world!", 1,
            "Autopay SDK", 1f,
            "Hello Autopay SDK", 1.0,
            "Autopay Java SDK", 1L
            ), mergedMap
        );
    }

    @Test
    public void testMergeIfAbsentMapsReturnsExceptedMap() {
        final var mergedMap = MapUtils.mergeIfAbsent(
            Map.of("Hello world!", 1),
            Map.of("Autopay SDK", 1f),
            Map.of(
                "Hello Autopay SDK", 1.0,
                "Autopay Java SDK", 1L
            ),
            Map.of("Hello world!", 10),
            Map.of("Hello Autopay SDK", 10.0)
        );

        assertNotNull(mergedMap);
        assertEquals(4, mergedMap.size());
        assertEquals(
            Map.of(
                "Hello world!", 1,
                "Autopay SDK", 1f,
                "Hello Autopay SDK", 1.0,
                "Autopay Java SDK", 1L
            ), mergedMap
        );
    }

    @ParameterizedTest
    @MethodSource("mapsToCapitalizeProvider")
    public void testCapitalizeMapReturnsMapWithExceptedKeysTest(Map<String, String> src, Map<String, String> excepted) {
        assertEquals(excepted, MapUtils.capitalizeMap(src));
    }

    @Contract(" -> new")
    public static @Unmodifiable List<Arguments> mapsToCapitalizeProvider() {
        return List.of(
            Arguments.of(
                Map.of("hello world!", ""),
                Map.of("Hello world!", "")
            ),
            Arguments.of(
                Map.of("autopay", ""),
                Map.of("Autopay", "")
            ),
            Arguments.of(
                Map.of("Autopay SDK", ""),
                Map.of("Autopay SDK", "")
            )
        );
    }
}

package forgetmenot.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CliArgUtilsTest {

    static Stream<Arguments> argCountValidProvider() {
        return Stream.of(
            Arguments.of(2, 0, 3),
            Arguments.of(3, 1, 3),
            Arguments.of(1, 1, 5),
            Arguments.of(1, 1, 1)
        );
    }

    static Stream<Arguments> argCountInvalidProvider() {
        return Stream.of(
            Arguments.of(2, 3, 3),
            Arguments.of(1, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("argCountValidProvider")
    void testValidateArgCountValid(int cnt, int min, int max) {
        assertDoesNotThrow(() -> {
            CliArgUtils.validateArgCount(cnt, min, max);
        });
    }

    @ParameterizedTest
    @MethodSource("argCountInvalidProvider")
    void testValidateArgCountInvalid(int cnt, int min, int max) {
        assertThrows(IllegalArgumentException.class, () -> {
            CliArgUtils.validateArgCount(cnt, min, max);
        });
    }

    @Test
    void testParseId() {
        assertEquals(1, CliArgUtils.parseId("1"));
        assertEquals(50, CliArgUtils.parseId("50"));
        assertThrows(IllegalArgumentException.class, () -> {
            CliArgUtils.parseId("0");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CliArgUtils.parseId("-1");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CliArgUtils.parseId("abc");
        });
    }
    
}

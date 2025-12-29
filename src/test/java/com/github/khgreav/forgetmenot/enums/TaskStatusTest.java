package com.github.khgreav.forgetmenot.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

class TaskStatusTest {

    static Stream<Arguments> statusAndStringProvider() {
        return Stream.of(
            Arguments.of(TaskStatus.TODO, "todo"),
            Arguments.of(TaskStatus.IN_PROGRESS, "in-progress"),
            Arguments.of(TaskStatus.DONE, "done")
        );
    }

    @ParameterizedTest
    @MethodSource("statusAndStringProvider")
    void testFromString(TaskStatus expected, String value) {
        assertEquals(expected, TaskStatus.fromString(value));
    }

    @Test
    void testFromStringInvalid() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                TaskStatus.fromString("almost-done");
            }
        );
    }

    @ParameterizedTest
    @MethodSource("statusAndStringProvider")
    void testToString(TaskStatus status, String expected) {
        assertEquals(expected, status.toString());
    }
    
}

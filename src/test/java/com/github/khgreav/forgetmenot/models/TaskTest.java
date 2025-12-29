package com.github.khgreav.forgetmenot.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.khgreav.forgetmenot.enums.TaskStatus;

public class TaskTest {

    private static final int ID = 1;

    private static final String DESC = "description";

    private static final TaskStatus STATUS = TaskStatus.TODO;

    private static final Instant CREATED_AT = Instant.parse("2025-12-26T18:00:05Z");

    private static final Instant UPDATED_AT = Instant.parse("2025-12-26T19:42:12Z");

    private Task task;

    static Stream<Arguments> taskPropertiesProvider() {
        return Stream.of(
            Arguments.of(
                Map.of(
                    "id", ID,
                    "desc", DESC,
                    "status", STATUS,
                    "createdAt", CREATED_AT,
                    "updatedAt", UPDATED_AT
                )
            )
        );
    }
    
    @BeforeEach
    void setUp() {
        task = new Task(
            ID,
            DESC,
            STATUS,
            CREATED_AT,
            UPDATED_AT
        );
    }

    @Test
    void testSetDesc() {
        assertEquals(UPDATED_AT, task.getUpdatedAt());
        task.setDesc("new description");
        assertEquals("new description", task.getDesc());
        assertNotEquals(UPDATED_AT, task.getUpdatedAt());
    }

    @Test
    void testSetStatus() {
        assertEquals(UPDATED_AT, task.getUpdatedAt());
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertNotEquals(UPDATED_AT, task.getUpdatedAt());
    }

    @ParameterizedTest
    @MethodSource("taskPropertiesProvider")
    void testFromJson(Map<String, Object> properties) {
        assertEquals(task, Task.fromJson(properties));
    }

    @Test
    void testEquals() {
        assertTrue(task.equals(task));
        assertFalse(task.equals(null));
        assertFalse(
            task.equals(
                new Task(
                    ID,
                    DESC,
                    STATUS,
                    CREATED_AT,
                    Instant.now()
                )
            )
        );
    }
}

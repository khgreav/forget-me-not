package tasktracker.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tasktracker.enums.TaskStatus;
import tasktracker.models.Task;

public class FlatJsonProcessorTest {

    private static final String JSON_STRING =
    """
    [
      {\"id\":1,\"desc\":\"test\",\"status\":\"in-progress\",\"createdAt\":\"2025-12-27T12:50:30Z\",\"updatedAt\":\"2025-12-27T13:08:51Z\"},
      {\"id\":2,\"desc\":\"another\",\"status\":\"done\",\"createdAt\":\"2025-12-27T13:30:30Z\",\"updatedAt\":\"2025-12-27T13:32:51Z\"}
    ]
    """;

    private static final HashMap<Integer, Task> TASK_MAP = new HashMap<>(
        Map.of(
            1, new Task(
                1,
                "test",
                TaskStatus.IN_PROGRESS,
                Instant.parse("2025-12-27T12:50:30Z"),
                Instant.parse("2025-12-27T13:08:51Z")
            ),
            2, new Task(
                2,
                "another",
                TaskStatus.DONE,
                Instant.parse("2025-12-27T13:30:30Z"),
                Instant.parse("2025-12-27T13:32:51Z")
            )
        )
    );

    @Test
    void testDeserializeObjectLine() {
        assertEquals(
            TASK_MAP.get(1),
            FlatJsonProcessor.deserializeObject("{\"id\":1,\"desc\":\"test\",\"status\":\"in-progress\",\"createdAt\":\"2025-12-27T12:50:30Z\",\"updatedAt\":\"2025-12-27T13:08:51Z\"}")
        );
    }

    @ParameterizedTest
    @CsvSource({
        "{\"created}",
        "{\"id\":1,\"desc\"",
        "{\"id\":1,\"desc\":\"asd"
    })
    void testDeserializeObjectInvalidJson(String line) {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject(line);
            }
        );
    }

    @Test
    void testDeserializeObjectUnexpectedKey() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":1,\"test\":\"test\"}");
            }
        );
    }

    @Test
    void testDeserializeObjectDuplicateKey() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":1,\"id\":2}");
            }
        );
    }

    @Test
    void testDeserializeObjectInvalidIdType() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":\"test\"}");
            }
        );
    }

    @Test
    void testDeserializeObjectInvalidIdValue() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":0}");
            }
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":1a1}");
            }
        );
    }

    @Test
    void testDeserializeObjectInvalidDateFormat() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"createdAt\":\"abcd\"}");
            }
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"updatedAt\":\"abcd\"}");
            }
        );
    }

    @Test
    void testDeserializeObjectMissingKeys() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                FlatJsonProcessor.deserializeObject("{\"id\":1}");
            }
        );
    }
    
    @Test
    void testSerializeEmpty() {
        assertEquals(
            "[]\n",
            FlatJsonProcessor.serialize(new HashMap<Integer, Task>().values().iterator())
        );
    }

    @Test
    void testSerialize() {
        assertEquals(
            JSON_STRING,
            FlatJsonProcessor.serialize(TASK_MAP.values().iterator())
        );
    }
}

package forgetmenot.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import forgetmenot.enums.TaskStatus;
import forgetmenot.models.Task;

public class TaskSerializerTest {

    private static final int ID = 1;

    private static final String DESC = "description";

    private static final TaskStatus STATUS = TaskStatus.TODO;

    private static final Instant CREATED_AT = Instant.parse("2025-12-26T18:00:05Z");

    private static final Instant UPDATED_AT = Instant.parse("2025-12-26T19:42:12Z");

    private static final Task task = new Task(
        ID,
        DESC,
        STATUS,
        CREATED_AT,
        UPDATED_AT
    );

    @Test
    void testBuildTableHeader() {
        assertEquals(
            "ID   | Description                                                  | Status      | Created at           | Last updated        \n===============================================================================================================================",
            TaskSerializer.buildTableHeader()
        );
    }
    
    @Test
    void testCliSerialize() {
        assertEquals(
            "1    | description                                                  | todo        | 2025-12-26T18:00:05Z | 2025-12-26T19:42:12Z",
            TaskSerializer.cliSerialize(task)
        );
    }

    @Test
    void testJsonSerialize() {
        assertEquals(
            "{\"id\":1,\"desc\":\"description\",\"status\":\"todo\",\"createdAt\":\"2025-12-26T18:00:05Z\",\"updatedAt\":\"2025-12-26T19:42:12Z\"}",
            TaskSerializer.jsonSerialize(task)
        );
    }
}

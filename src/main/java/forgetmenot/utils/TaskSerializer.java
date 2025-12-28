package forgetmenot.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import forgetmenot.models.Task;

public final class TaskSerializer {

    public static final int ID_LEN = 4;

    public static final int DESC_LEN = 60;

    public static final int STATUS_LEN = 11;

    public static final int DATETIME_LEN = 20;
    
    private TaskSerializer() {
        throw new AssertionError("Cannot instantiate.");
    }

    public static String buildTableHeader() {
        return new StringBuilder()
            .append(
                Formatter.padRight("ID", ID_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight("Description", DESC_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight("Status", STATUS_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight("Created at", DATETIME_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight("Last updated", DATETIME_LEN)
            ).append('\n')
            .append(
                Formatter.padRight(
                    null,
                    ID_LEN + DESC_LEN + STATUS_LEN + (2 * DATETIME_LEN) + 12,
                    '='
                )
            ).toString();
    }

    public static String cliSerialize(Task task) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendInstant(0).toFormatter();
        return new StringBuilder()
            .append(
                Formatter.padRight(String.valueOf(task.getId()), ID_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight(task.getDesc(), DESC_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight(task.getStatus().toString(), STATUS_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight(fmt.format(task.getCreatedAt()), DATETIME_LEN)
            ).append(" | ")
            .append(
                Formatter.padRight(fmt.format(task.getUpdatedAt()), DATETIME_LEN)
            )
            .toString();
    }

    public static String jsonSerialize(Task task) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendInstant(0).toFormatter();
        return String.format(
            "{\"id\":%d,\"desc\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
            task.getId(),
            task.getDesc(),
            task.getStatus().toString(),
            fmt.format(task.getCreatedAt()),
            fmt.format(task.getUpdatedAt())
        );
    }
}

package com.github.khgreav.forgetmenot.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.github.khgreav.forgetmenot.models.Task;

/**
 * Utility class for serializing {@link Task} objects for CLI and JSON output.
 */
public final class TaskSerializer {

    /**
     * Fixed length of ID table column.
     */
    public static final int ID_LEN = 4;

    /**
     * Fixed length of description table column.
     */
    public static final int DESC_LEN = 60;

    /**
     * Fixed length of status table column.
     */
    public static final int STATUS_LEN = 11;

    /**
     * Fixed length of datetime table column.
     */
    public static final int DATETIME_LEN = 20;
    
    private TaskSerializer() {
        throw new AssertionError("Cannot instantiate.");
    }

    /**
     * Builds the header row for the task table in CLI output.
     * @return Formatted table header string
     */
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

    /**
     * Serializes a {@link Task} object into a formatted string for CLI output.
     * @param task Task object to serialize
     * @return CLI-formatted task string
     */
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

    /**
     * Serializes a {@link Task} object into a JSON string.
     * @param task Task object to serialize
     * @return JSON representation of the task
     */
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

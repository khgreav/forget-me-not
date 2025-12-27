package forgetmenot.models;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.Objects;

import forgetmenot.enums.TaskStatus;
import forgetmenot.utils.Formatter;

public class Task {
    
    private final int id;
    private String desc;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(
        int id,
        String desc,
        TaskStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {
        this.id = id;
        this.desc = desc;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return this.id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        this.updatedAt = Instant.now();
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public static String buildTableHeader() {
        return new StringBuilder()
            .append(Formatter.padRight("ID", Formatter.ID_LEN)).append(" | ")
            .append(Formatter.padRight("Description", Formatter.DESC_LEN)).append(" | ")
            .append(Formatter.padRight("Status", Formatter.STATUS_LEN)).append(" | ")
            .append(Formatter.padRight("Created at", Formatter.DATETIME_LEN)).append(" | ")
            .append(Formatter.padRight("Last updated", Formatter.DATETIME_LEN)).append('\n')
            .append(
                Formatter.padRight(
                    null,
                    Formatter.ID_LEN + Formatter.DESC_LEN + Formatter.STATUS_LEN + (2 * Formatter.DATETIME_LEN) + 12,
                    '='
                )
            ).toString();
    }

    public String cliSerialize() {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendInstant(0).toFormatter();
        return new StringBuilder()
            .append(Formatter.padRight(String.valueOf(this.id), Formatter.ID_LEN)).append(" | ")
            .append(Formatter.padRight(this.desc, Formatter.DESC_LEN)).append(" | ")
            .append(Formatter.padRight(this.status.toString(), Formatter.STATUS_LEN)).append(" | ")
            .append(Formatter.padRight(fmt.format(createdAt), Formatter.DATETIME_LEN)).append(" | ")
            .append(Formatter.padRight(fmt.format(updatedAt), Formatter.DATETIME_LEN))
            .toString();
    }

    public String jsonSerialize() {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendInstant(0).toFormatter();
        return String.format(
            "{\"id\":%d,\"desc\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
            this.id,
            this.desc,
            this.status.toString().toLowerCase(),
            fmt.format(this.createdAt),
            fmt.format(this.updatedAt)
        );
    }

    private static <T> T getProperty(Map<String, Object> map, String key, Class<T> type) {
        Object obj = map.get(key);
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(
                String.format("Missing or invalid key %s", key)
            );
        }
        return type.cast(obj);
    }

    public static Task fromJson(Map<String, Object> properties) {
        return new Task(
            getProperty(properties, "id", Integer.class),
            getProperty(properties, "desc", String.class),
            getProperty(properties, "status", TaskStatus.class),
            getProperty(properties, "createdAt", Instant.class),
            getProperty(properties, "updatedAt", Instant.class)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return this.id == task.id &&
            this.desc.equals(task.desc) &&
            this.status.equals(task.status) &&
            this.createdAt.equals(task.createdAt) &&
            this.updatedAt.equals(task.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.id,
            this.desc,
            this.status,
            this.createdAt,
            this.updatedAt
        );
    }
}

package forgetmenot.models;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import forgetmenot.enums.TaskStatus;
import forgetmenot.utils.MapUtils;

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

    public final int getId() {
        return this.id;
    }

    public final String getDesc() {
        return this.desc;
    }

    public final void setDesc(String desc) {
        this.desc = desc;
        this.updatedAt = Instant.now();
    }

    public final TaskStatus getStatus() {
        return this.status;
    }

    public final void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public final Instant getCreatedAt() {
        return this.createdAt;
    }

    public final Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public static Task fromJson(Map<String, Object> properties) {
        return new Task(
            MapUtils.getProperty(properties, "id", Integer.class),
            MapUtils.getProperty(properties, "desc", String.class),
            MapUtils.getProperty(properties, "status", TaskStatus.class),
            MapUtils.getProperty(properties, "createdAt", Instant.class),
            MapUtils.getProperty(properties, "updatedAt", Instant.class)
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
        Task other = (Task) o;
        return this.id == other.id &&
            Objects.equals(this.desc, other.desc) &&
            Objects.equals(this.status, other.status) &&
            Objects.equals(this.createdAt, other.createdAt) &&
            Objects.equals(this.updatedAt, other.updatedAt);
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

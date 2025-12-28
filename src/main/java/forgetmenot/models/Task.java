package forgetmenot.models;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import forgetmenot.enums.TaskStatus;
import forgetmenot.utils.MapUtils;

/**
 * Represents a task.
 * <p>
 * Each task has an ID and description associated with it. It also has a status which indicates
 * whether the task is yet to be started, in progress, or completed, as well as timestamps of creation and last update.
 * </p>
 */
public final class Task {

    /**
     * Unique identifier of a task.
     */
    private final int id;

    /**
     * Task description.
     */
    private String desc;

    /**
     * Current status of task.
     */
    private TaskStatus status;

    /**
     * Timestamp when task was created.
     */
    private final Instant createdAt;

    /**
     * Timestamp when task was last updated.
     */
    private Instant updatedAt;

    /**
     * Constructs a new Task.
     * @param id Unique identifier of the task
     * @param desc Task description
     * @param status Current status of the task
     * @param createdAt Timestamp when the task was created
     * @param updatedAt Timestamp when the task was last updated
     */
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

    /**
     * Returns the unique identifier of the task.
     * @return Unique identifier
     */
    public final int getId() {
        return this.id;
    }

    /**
     * Returns the task description.
     * @return Task description
     */
    public final String getDesc() {
        return this.desc;
    }

    /**
     * Sets the task description.
     * <p>
     * Updating the description also changes the last updated timestamp.
     * </p>
     * @param desc New task description
     */
    public final void setDesc(String desc) {
        this.desc = desc;
        this.updatedAt = Instant.now();
    }

    /**
     * Returns the current status of the task.
     * @return Current status
     */
    public final TaskStatus getStatus() {
        return this.status;
    }

    /**
     * Updates current status of the task.
     * <p>
     * Updating the status also changes the last updated timestamp.
     * </p>
     * @param status New current status
     */
    public final void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    /**
     * Returns the timestamp when the task was created.
     * @return Creation timestamp
     */
    public final Instant getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the timestamp when the task was last updated.
     * @return Last updated timestamp
     */
    public final Instant getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Factory method to create {@link Task} from a map of properties.
     * @param properties Map of properties
     * @return Corresponding Task
     */
    public static Task fromJson(Map<String, Object> properties) {
        return new Task(
            MapUtils.getProperty(properties, "id", Integer.class),
            MapUtils.getProperty(properties, "desc", String.class),
            MapUtils.getProperty(properties, "status", TaskStatus.class),
            MapUtils.getProperty(properties, "createdAt", Instant.class),
            MapUtils.getProperty(properties, "updatedAt", Instant.class)
        );
    }

    /**
     * Checks if this task is equal to another object.
     * @param o Object to compare
     * @return True if equal, false otherwise
     */
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

    /** 
     * Returns the hash code of the task.
     * @return Hash code
     */
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

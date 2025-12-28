package forgetmenot.enums;

/**
 * Represents the status of a {@link Task}.
 * <p>
 * The possible statuses are:
 * <ul>
 *    <li>{@link #TODO} - New task, yet to be started.</li>
 *    <li>{@link #IN_PROGRESS} - Task is currently in progress.</li>
 *    <li>{@link #DONE} - Task has been completed.</li>
 * </ul>
 * </p>
 */
public enum TaskStatus {
    /**
     * New task, yet to be started.
     */
    TODO,

    /**
     * Task is currently in progress.
     */
    IN_PROGRESS,

    /**
     * Task has been completed.
     */
    DONE;

    /**
     * Factory method to create {@link TaskStatus} from string value.
     * @param value String value
     * @return Corresponding {@link TaskStatus}
     * @throws IllegalArgumentException if the value does not represent a valid TaskStatus
     */
    public static TaskStatus fromString(String value) {
        return switch (value) {
            case "todo" -> TODO;
            case "in-progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException("Unknown task status value: " + value);
        };
    }

    /**
     * Returns the string representation of the TaskStatus.
     * <p>
     * This method overrides the default toString method to return the status name for use in JSON or CLI output.
     * </p>
     * @return String representation
     */
    @Override
    public String toString() {
        return this.name().replace('_', '-').toLowerCase();
    }
}

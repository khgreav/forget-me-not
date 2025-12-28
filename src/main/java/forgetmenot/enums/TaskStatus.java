package forgetmenot.enums;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public static TaskStatus fromString(String value) {
        return switch (value) {
            case "todo" -> TODO;
            case "in-progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException("Unknown task status value: " + value);
        };
    }

    @Override
    public String toString() {
        return this.name().replace('_', '-').toLowerCase();
    }
}

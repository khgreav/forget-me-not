package tasktracker.enums;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String val;

    TaskStatus(String val) {
        this.val = val;
    }

    public static TaskStatus fromString(String value) {
        return switch (value) {
            case "todo" -> TODO;
            case "in-progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException(String.format("Unknown task status value: %s", value));
        };
    }

    @Override
    public String toString() {
        return this.val;
    }
}

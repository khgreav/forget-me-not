package tasktracker.utils;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tasktracker.enums.TaskStatus;
import tasktracker.models.Task;

public class FlatJsonProcessor {

    private static final Set<String> REQUIRED_KEYS = Set.of(
        "id",
        "desc",
        "status",
        "createdAt",
        "updatedAt"
    );

    public static Task deserializeObjectLine(String line) {
        int i = 0;
        int n = line.length();

        Map<String, Object> props = new HashMap<String, Object>();

        while (i < n) {
            // process one key per outer loop
            while (line.charAt(i) != '"') {
                i++;
            }
            // found start of key
            i++;
            int start = i;
            // find end of key
            while (line.charAt(i) != '"') {
                i++;
            }
            var key = line.substring(start, i);
            // check if key is valid
            if (!REQUIRED_KEYS.contains(key)) {
                throw new IllegalArgumentException("Invalid record key: " + key);
            }
            // check for duplicate key
            if (props.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate record key detected: " + key);
            }
            // find start of value
            while (line.charAt(i) != '"') {
                i++;
            }
            start = i;
            // find end of value
            while (line.charAt(i) != '"') {
                i++;
            }
            var value = line.substring(start, i);
            if (key == "id") {
                try {
                    var id = Integer.parseInt(value);
                    if (id < 0) {
                        throw new IllegalArgumentException("ID property value should be non-zero positive integere.");
                    }
                    props.put(key, id);
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                        String.format("Invalid ID property value: %s", e.getMessage()),
                        e
                    );
                }
            } else if (key == "status") {
                props.put(key, TaskStatus.fromString(value));
            } else if (key == "createdAt" || key == "updatedAt") {
                try {
                    props.put(key, Instant.parse(value));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException(
                        String.format("Invalid datetime property value format: %s", e.getMessage()),
                        e
                    );
                }
            } else {
                props.put(key, value);
            }
        }
        if (props.size() != 5) {
            throw new IllegalArgumentException("Task record should contain 5 properties.");
        }
        return Task.fromJson(props);
    }

    public static String serialize(Iterator<Task> itr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        while (itr.hasNext()) {
            Task task = itr.next();
            sb.append("  ")
                .append(task.jsonSerialize())
                .append('\n');
        }
        sb.append("]\n");
        return sb.toString();
    }
}

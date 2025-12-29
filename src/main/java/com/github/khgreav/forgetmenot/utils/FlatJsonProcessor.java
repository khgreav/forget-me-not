package com.github.khgreav.forgetmenot.utils;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.khgreav.forgetmenot.enums.TaskStatus;
import com.github.khgreav.forgetmenot.models.Task;

/**
 * Utility class for processing flat JSON representations of {@link Task} objects.
 */
public final class FlatJsonProcessor {

    private FlatJsonProcessor() {
        throw new AssertionError("Cannot instantiate FlatJsonProcessor.");
    }

    /**
     * Set of required keys in the JSON representation of a Task.
     */
    private static final Set<String> REQUIRED_KEYS = Set.of(
        "id",
        "desc",
        "status",
        "createdAt",
        "updatedAt"
    );

    /**
     * Deserializes a JSON string into a {@link Task} object.
     * @param line JSON string representing a Task
     * @return Deserialized Task object
     * @throws IllegalArgumentException if the JSON string is invalid, contains invalid keys, or values
     */
    public static Task deserializeObject(String line) {
        int i = 0;
        int n = line.length();

        Map<String, Object> props = new HashMap<String, Object>();

        while (i < n) {
            // process one key per outer loop
            while (i < n && line.charAt(i) != '"') {
                i++;
            }
            if (i >= n) {
                break;
            }
            // found start of key
            i++;
            int start = i;
            // find end of key
            while (i < n && line.charAt(i) != '"') {
                i++;
            }
            if (i >= n) {
                break;
            }
            var key = line.substring(start, i);
            i++;
            // check if key is valid
            if (!REQUIRED_KEYS.contains(key)) {
                throw new IllegalArgumentException("Invalid record key: " + key);
            }
            // check for duplicate key
            if (props.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate record key detected: " + key);
            }
            // find k-v separator
            while (i < n && (Character.isWhitespace(line.charAt(i)) || line.charAt(i) == ':')) {
                i++;
            }
            if (i >= n) {
                break;
            }
            String value;
            if (line.charAt(i) == '"') {
                i++;
                start = i;
                while (i < n && line.charAt(i) != '"') {
                    i++;
                }
                if (i >= n) {
                    break;
                }
                value = line.substring(start, i);
            } else {
                start = i;
                while (i < n && line.charAt(i) != ',' && line.charAt(i) != '}' && !Character.isWhitespace(n)) {
                    if (!Character.isDigit(line.charAt(i))) {
                        throw new IllegalArgumentException("ID property should contain a numeric value.");
                    }
                    i++;
                }
                if (i >= n) {
                    break;
                }
                value = line.substring(start, i);
            }
            i++;
            if (key.equals("id")) {
                try {
                    var id = Integer.parseInt(value);
                    if (id < 1) {
                        throw new IllegalArgumentException("ID property value should be non-zero positive integere.");
                    }
                    props.put(key, id);
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                        "Invalid ID property value: " + e.getMessage(),
                        e
                    );
                }
            } else if (key.equals("status")) {
                props.put(key, TaskStatus.fromString(value));
            } else if (key.equals("createdAt") || key.equals("updatedAt")) {
                try {
                    props.put(key, Instant.parse(value));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException(
                        "Invalid datetime property value format: " +  e.getMessage(),
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

    /**
     * Serializes a collection of {@link Task} objects into a JSON array string.
     * @param tasks Collection of Task objects to serialize
     * @return JSON array representation of the tasks
     */
    public static String serialize(Collection<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        Iterator<Task> itr = tasks.iterator();
        sb.append("[");
        while (itr.hasNext()) {
            sb.append('\n');
            Task task = itr.next();
            sb.append("  ")
                .append(TaskSerializer.jsonSerialize(task));
            if (itr.hasNext()) {
                sb.append(',');
            } else {
                sb.append('\n');
            }
        }
        sb.append("]\n");
        return sb.toString();
    }
}

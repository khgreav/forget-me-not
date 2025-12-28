package forgetmenot.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import forgetmenot.enums.TaskStatus;
import forgetmenot.models.Task;
import forgetmenot.utils.FlatJsonProcessor;

public class TaskRepository {

    private Path storagePath;
    private Path sequencePath;
    private Map<Integer, Task> tasks;
    private int sequence;

    public TaskRepository(Path storagePath, Path sequencePath) throws IOException {
        this.storagePath = storagePath;
        this.sequencePath = sequencePath;
        this.initializeStorage();
        this.initializeSequence();
    }

    public int create(String desc) {
        var now = Instant.now();
        int id = this.sequence;
        this.tasks.put(
            id,
            new Task(
                id,
                desc,
                TaskStatus.TODO,
                now,
                now
            )
        );
        this.sequence++;
        return id;
    }

    public Collection<Task> listAll() {
        return Collections.unmodifiableCollection(this.tasks.values());
    }

    public Task get(int id) {
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID " + id + " does not exist.");
        }
        return this.tasks.get(id);
    }

    public void update(Task task) {
        var id = task.getId();
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID %d does not exist" + id + '.');
        }
        tasks.put(id, task);
    }

    public void delete(int id) {
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID %d does not exist" + id + '.');
        }
        this.tasks.remove(id);
    }

    public void persist() throws IOException {
        Files.writeString(
            this.storagePath,
            FlatJsonProcessor.serialize(Collections.unmodifiableCollection(this.tasks.values())),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
        Files.writeString(
            this.sequencePath,
            new StringBuilder().append(this.sequence).append('\n').toString(),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private void initializeStorage() throws IOException {
        this.tasks = new LinkedHashMap<Integer, Task>();
        if (!Files.exists(this.storagePath)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(this.storagePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("[") || line.startsWith("]") || line.startsWith("[]")) {
                    continue;
                } 
                var task = FlatJsonProcessor.deserializeObject(line);
                this.tasks.put(task.getId(), task);
            }
        }
    }

    private void initializeSequence() throws IOException {
        int maxId = this.tasks.keySet().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0);
        int sequenceCandidate = 1;
        if (Files.exists(sequencePath)) {
            var content = Files.readString(this.sequencePath).trim();
            sequenceCandidate = Integer.parseInt(content);
        }
        this.sequence = Math.max(maxId + 1, sequenceCandidate);
    }

}

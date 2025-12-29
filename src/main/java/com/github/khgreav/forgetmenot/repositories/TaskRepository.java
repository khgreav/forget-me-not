package com.github.khgreav.forgetmenot.repositories;

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

import com.github.khgreav.forgetmenot.enums.TaskStatus;
import com.github.khgreav.forgetmenot.models.Task;
import com.github.khgreav.forgetmenot.utils.FlatJsonProcessor;

/**
 * Repository for managing {@link Task} entities.
 * <p>
 * This repository provides methods to create, read, update, and delete tasks.
 * It also handles persistence of tasks to a file storage.
 * </p>
 */
public class TaskRepository {

    /**
     * Path to the storage file.
     */
    private Path storagePath;

    /**
     * Path to the sequence file.
     */
    private Path sequencePath;

    /**
     * In-memory map of tasks.
     */
    private Map<Integer, Task> tasks;

    /**
     * Sequence number for generating unique task IDs.
     */
    private int sequence;

    /**
     * Constructs a new TaskRepository.
     * @param storagePath Path to the storage file
     * @param sequencePath Path to the sequence file
     * @throws IOException if an I/O error occurs
     */
    public TaskRepository(Path storagePath, Path sequencePath) throws IOException {
        this.storagePath = storagePath;
        this.sequencePath = sequencePath;
        this.initializeStorage();
        this.initializeSequence();
    }

    /**
     * Creates a new task with the given description.
     * @param desc Task description
     * @return ID of the newly created task
     */
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

    /**
     * Returns all tasks in the repository.
     * @return Collection of all tasks
     */
    public Collection<Task> listAll() {
        return Collections.unmodifiableCollection(this.tasks.values());
    }

    /**
     * Retrieves a task by its ID.
     * @param id Task ID
     * @return Corresponding Task
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public Task get(int id) {
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID " + id + " does not exist.");
        }
        return this.tasks.get(id);
    }

    /**
     * Updates an existing task.
     * @param task Task to update
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public void update(Task task) {
        var id = task.getId();
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID %d does not exist" + id + '.');
        }
        tasks.put(id, task);
    }

    /**
     * Deletes a task by its ID.
     * @param id Task ID
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public void delete(int id) {
        if (!this.tasks.containsKey(id)) {
            throw new NoSuchElementException("Task ID %d does not exist" + id + '.');
        }
        this.tasks.remove(id);
    }

    /**
     * Persists the current state of the repository to storage.
     * @throws IOException if an I/O error occurs
     */
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

    /** 
     * Initializes the in-memory storage from the storage file.
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Initializes the sequence number from the sequence file.
     * <p>
     * If the sequence file does not exist, the maximum task ID plus one is used as the initial sequence number.
     * </p>
     * @throws IOException if an I/O error occurs
     */
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

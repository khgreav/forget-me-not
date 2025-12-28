package forgetmenot.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import forgetmenot.enums.TaskStatus;
import forgetmenot.models.Task;
import forgetmenot.repositories.TaskRepository;
import forgetmenot.utils.TaskSerializer;

/**
 * Service layer for managing tasks.
 * <p>
 * This service provides methods to list, add, update, and delete tasks.
 * It interacts with the {@link TaskRepository} for data persistence.
 * </p>
 */
public class TaskService {
    
    /**
     * Repository for managing tasks
     */
    private TaskRepository repo;

    /**
     * Constructs a new TaskService.
     * @param storagePath Path to the storage file
     * @param sequencePath Path to the sequence file
     * @throws IOException if an I/O error occurs
     */
    public TaskService(Path storagePath, Path sequencePath) throws IOException {
        this.repo = new TaskRepository(storagePath, sequencePath);
    }

    /**
     * Retrieves a list of all tasks and prints them to standard output in a tabular format.
     */
    public void listAll() {
        var tasks = repo.listAll();
        System.out.println(TaskSerializer.buildTableHeader());
        for (Task task : tasks) {
            System.out.println(TaskSerializer.cliSerialize(task));
        }
    }

    /**
     * Retrieves a list of tasks filtered by status and prints them to standard output in a tabular format.
     * @param status TaskStatus to filter tasks by
     */
    public void listByStatus(TaskStatus status) {
        var tasks = repo.listAll();
        System.out.println(TaskSerializer.buildTableHeader());
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                System.out.println(TaskSerializer.cliSerialize(task));
            }
        }
    }

    /**
     * Adds a new task with the given description.
     * @param desc Task description
     */
    public void addTask(String desc) {
        var id = this.repo.create(desc);
        System.out.println("New task created with ID " + id + '.');
    }

    /**
     * Updates the description of an existing task.
     * @param id Task ID
     * @param desc New task description
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public void update(int id, String desc) {
        var task = this.getTask(id);
        task.setDesc(desc);
        this.repo.update(task);
        System.out.println("Task successully updated.");
    }

    /**
     * Updates the status of an existing task.
     * @param id Task ID
     * @param status New task status
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public void update(int id, TaskStatus status) {
        var task = this.getTask(id);
        task.setStatus(status);
        this.repo.update(task);
        System.out.println("Task status successfully changed.");
    }

    /**
     * Deletes an existing task.
     * @param id Task ID
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    public void delete(int id) throws NoSuchElementException {
        this.repo.delete(id);
        System.out.println("Task successfully deleted.");
    }

    /**
     * Saves the current state of tasks to persistent storage.
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException {
        this.repo.persist();
    }

    /**
     * Retrieves a task by its ID.
     * @param id Task ID
     * @return Corresponding Task
     * @throws NoSuchElementException if the task with the given ID does not exist
     */
    private Task getTask(int id) {
        return this.repo.get(id);
    }
}

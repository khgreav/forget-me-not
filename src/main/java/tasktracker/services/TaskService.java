package tasktracker.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import tasktracker.enums.TaskStatus;
import tasktracker.models.Task;
import tasktracker.repositories.TaskRepository;

public class TaskService {
    
    private TaskRepository repo;

    public TaskService(Path storagePath, Path sequencePath) throws IOException {
        this.repo = new TaskRepository(storagePath, sequencePath);
        this.repo.init();
    }

    public void listAll() {
        var itr = repo.listAll();
        while (itr.hasNext()) {
            Task task = itr.next();
            System.out.println(task.cliSerialize());
        }
    }

    public void listByStatus(TaskStatus status) {
        var itr = repo.listAll();
        while (itr.hasNext()) {
            Task task = itr.next();
            if (task.getStatus() == status) {
                System.out.println(task.cliSerialize());
            }
        }
    }

    public void addTask(String desc) {
        var id = this.repo.create(desc);
        System.out.println("New task created with ID " + id + '.');
    }

    public void update(int id, String desc) {
        var task = this.getTask(id);
        task.setDesc(desc);
        this.repo.update(task);
        System.out.println("Task successully updated.");
    }

    public void mark(int id, TaskStatus status) {
        var task = this.getTask(id);
        task.setStatus(status);
        this.repo.update(task);
        System.out.println("Task status successfully changed.");
    }

    public void delete(int id) throws NoSuchElementException {
        this.repo.delete(id);
        System.out.println("Task successfully deleted.");
    }

    public void save() throws IOException {
        this.repo.persist();
    }

    private Task getTask(int id) {
        return this.repo.get(id);
    }
}

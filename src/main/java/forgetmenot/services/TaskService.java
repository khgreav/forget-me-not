package forgetmenot.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import forgetmenot.enums.TaskStatus;
import forgetmenot.models.Task;
import forgetmenot.repositories.TaskRepository;
import forgetmenot.utils.TaskSerializer;

public class TaskService {
    
    private TaskRepository repo;

    public TaskService(Path storagePath, Path sequencePath) throws IOException {
        this.repo = new TaskRepository(storagePath, sequencePath);
    }

    public void listAll() {
        var tasks = repo.listAll();
        System.out.println(TaskSerializer.buildTableHeader());
        for (Task task : tasks) {
            System.out.println(TaskSerializer.cliSerialize(task));
        }
    }

    public void listByStatus(TaskStatus status) {
        var tasks = repo.listAll();
        System.out.println(TaskSerializer.buildTableHeader());
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                System.out.println(TaskSerializer.cliSerialize(task));
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

    public void update(int id, TaskStatus status) {
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

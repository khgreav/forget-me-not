package tasktracker;

import java.lang.IllegalArgumentException;
import java.nio.file.Path;

import tasktracker.enums.TaskStatus;
import tasktracker.services.TaskService;
import tasktracker.utils.ArgParser;

public class App {

    public static String constructHelpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task tracker\n\n")
            .append("Usage: task-cli <command> [<arguments>...]\n\n")
            .append("Commands:\n")
            .append("  help                         Show help message\n")
            .append("  list [<status>]              Lists task, optionally filtered by status (todo / in-progress / done)\n")
            .append("  add <description>            Creates a new task\n")
            .append("  update <ID> <description>    Change description of task\n")
            .append("  mark <ID> <status>           Change task status (in-progress / done)\n")
            .append("  delete <ID>                  Delete task\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.err.println("Invalid number of arguments, use help command for usage.");
            System.exit(1);
        }

        try {
            var current = Path.of("").toAbsolutePath();
            TaskService service = new TaskService(
                current.resolve("tasks.json"),
                current.resolve("sequence.txt")
            );
            String cmd = args[0];

            switch (cmd) {
                case "help" -> {
                    System.out.print(constructHelpMessage());
                    return;
                }
                case "list" -> {
                    ArgParser.validateArgCount(args.length, 1, 2);
                    if (args.length == 1) {
                        service.listAll();
                    } else {
                        var status = TaskStatus.fromString(args[1]);
                        service.listByStatus(status);
                    }
                }
                case "add" -> {
                    ArgParser.validateArgCount(args.length, 2, 2);
                    service.addTask(args[1]);
                }
                case "update" -> {
                    ArgParser.validateArgCount(args.length, 3, 3);
                    service.update(
                        ArgParser.parseId(args[1]),
                        args[2]
                    );
                }
                case "mark" -> {
                    ArgParser.validateArgCount(args.length, 3, 3);
                    service.mark(
                        ArgParser.parseId(args[1]),
                        TaskStatus.fromString(args[2])
                    );
                }
                case "delete" -> {
                    ArgParser.validateArgCount(args.length, 2, 2);
                    service.delete(ArgParser.parseId(args[1]));
                }
                default -> throw new IllegalArgumentException(String.format("Invalid command %s", cmd));
            }
            service.save();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
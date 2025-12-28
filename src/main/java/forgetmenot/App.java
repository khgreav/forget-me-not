package forgetmenot;

import java.lang.IllegalArgumentException;
import java.nio.file.Path;

import forgetmenot.enums.TaskStatus;
import forgetmenot.services.TaskService;
import forgetmenot.utils.CliArgUtils;

/**
 * Main application class for Forget Me Not - Task Tracker.
 * <p>
 * This class contains the entry point of the application and handles command-line argument parsing.
 * </p>
 */
public class App {

    /**
     * Constructs the help message for the application.
     * @return Help message string
     */
    public static String constructHelpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Forget Me Not - Task Tracker\n\n")
            .append("Usage: forget-me-not <command> [<arguments>...]\n\n")
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
            System.err.println(constructHelpMessage());
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
                    CliArgUtils.validateArgCount(args.length, 1, 2);
                    if (args.length == 1) {
                        service.listAll();
                    } else {
                        var status = TaskStatus.fromString(args[1]);
                        service.listByStatus(status);
                    }
                }
                case "add" -> {
                    CliArgUtils.validateArgCount(args.length, 2, 2);
                    service.addTask(args[1]);
                }
                case "update" -> {
                    CliArgUtils.validateArgCount(args.length, 3, 3);
                    service.update(
                        CliArgUtils.parseId(args[1]),
                        args[2]
                    );
                }
                case "mark" -> {
                    CliArgUtils.validateArgCount(args.length, 3, 3);
                    service.update(
                        CliArgUtils.parseId(args[1]),
                        TaskStatus.fromString(args[2])
                    );
                }
                case "delete" -> {
                    CliArgUtils.validateArgCount(args.length, 2, 2);
                    service.delete(CliArgUtils.parseId(args[1]));
                }
                default -> throw new IllegalArgumentException(
                    new StringBuilder()
                        .append("Invalid command: ")
                        .append(cmd)
                        .append('.')
                        .toString()
                );
            }
            service.save();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
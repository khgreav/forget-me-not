# Forget Me Not

Forget Me Not is a simple CLI-based task tracker, it allows users to create, update and delete tasks. Each task has a status associated with it: TODO, IN PROGRESS and DONE.

Tasks are stored in a JSON file. The app also maintains a sequence file to ensure that task IDs are unique.

The project is an implementation of the roadmap.sh [Task Tracker](https://roadmap.sh/projects/task-tracker) project spec. The purpose of this project is to get familiar with and learn basics of Java 17 and Maven.

### Build instructions

This project uses Maven, the app can be built using the following command:

```bash
mvn package
```

The command compiles source files into a JAR file and produces an invocation script into `build/bin`.

### Usage

Show help
```bash
forget-me-not help
```

List all tasks
```bash
forget-me-not list
```

List tasks by status
```bash
forget-me-not list <status> # todo / in-progress / done
```

Add task
```bash
forget-me-not add <task_description>
```

Update task - change description
```bash
forget-me-not update <ID> <task_description>
```

Change task status to in-progress or done
```bash
forget-me-not mark <ID> <status> # in-progress / done
```

Delete task
```bash
forget-me-not delete <ID>
```

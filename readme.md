### Requirements
The application should run from the command line, accept user actions and inputs as arguments, and store the tasks in a JSON file. The user should be able to:

- Add, Update, and Delete tasks
- Mark a task as in progress or done
- List all tasks
- List all tasks that are done
- List all tasks that are not done
- List all tasks that are in progress

### API

Show help
```bash
task-cli help
```

List all tasks
```bash
task-cli list
```

List tasks by status
```bash
task-cli list <status> # todo / in-progress / done
```

Add task
```bash
task-cli add <task_description>
```

Update task - change description
```bash
task-cli update <ID> <task_description>
```

Change task status to in-progress or done
```bash
task-cli mark <ID> <status> # in-progress / done
```

Delete task
```bash
task-cli delete <ID>
```

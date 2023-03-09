package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskList;
import com.codurance.training.tasks.TaskListUtility;

import java.util.List;
import java.util.Map;

public class ExecuteServiceImpl implements ExecuteService{
	private final TaskListUtility taskListUtility;
	private final CheckService checkService;
	private final DeleteService deleteService;
	private final TaskService taskService;
	private final ShowService showService;
	private final Map<String, List<Task>> tasks;

	private final AddService addService;

	public ExecuteServiceImpl(TaskListUtility taskListUtility, CheckService checkService, DeleteService deleteService, TaskService taskService, ShowService showService, Map<String, List<Task>> tasks, AddService addService) {
		this.taskListUtility = taskListUtility;
		this.checkService = checkService;
		this.deleteService = deleteService;
		this.taskService = taskService;
		this.showService = showService;
		this.tasks = tasks;
		this.addService = addService;
	}

	@Override
	public void execute(String commandLine) {
		String[] commandRest = commandLine.split(" ", 2);
		String command = commandRest[0];
		switch (command) {
			case "add":
				addService.add(commandRest[1]);
				break;
			case "check":
				checkService.check(commandRest[1]);
				break;
			case "uncheck":
				checkService.uncheck(commandRest[1]);
				break;
			case "delete":
				deleteService.delete(commandRest[1]);
				break;
			case "deadline":
				String[] taskDeadline = commandRest[1].split(" ", 2);
				taskService.addDeadlineToTask(taskDeadline[0], taskDeadline[1]);
				break;
			case "today":
				showService.showDueTodayTasks(tasks);
				break;
			case "view":
				taskListUtility.view(commandRest[1]);
				break;
			case "help":
				taskListUtility.help();
				break;
			default:
				taskListUtility.error(command);
				break;
		}
	}
}

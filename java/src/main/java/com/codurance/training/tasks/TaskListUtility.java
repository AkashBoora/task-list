package com.codurance.training.tasks;

import com.codurance.training.tasks.service.ShowService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskListUtility {

	private final Map<String, List<Task>> tasks;
	private final PrintWriter out;
	private final ShowService showService;

	public TaskListUtility(Map<String, List<Task>> tasks, PrintWriter out, ShowService showService) {
		this.tasks = tasks;
		this.out = out;
		this.showService = showService;
	}

	public void view(String command) {
		if(command.equals("by date"))
			showService.showByDate(tasks);
		else if(command.equals("by deadline"))
			showService.showByDeadline(tasks);
		else if(command.equals("by project"))
			showService.showByProject(tasks);
		else
			error(command);
	}

	public void help() {
		out.println("Commands:");
		out.println("  add project <project name>");
		out.println("  add task <project name> <task ID> <task description>");
		out.println("  check <task ID>");
		out.println("  uncheck <task ID>");
		out.println("  delete <task ID>");
		out.println("  deadline <task ID> <date>");
		out.println("  today");
		out.println("  view by date");
		out.println("  view by deadline");
		out.println("  view by project");
		out.println();
	}

	public void error(String command) {
		out.printf("I don't know what the command \"%s\" is.", command);
		out.println();
	}
}

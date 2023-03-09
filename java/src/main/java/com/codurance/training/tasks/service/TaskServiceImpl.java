package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskServiceImpl implements TaskService{

	private final Map<String, List<Task>> tasks;

	private final PrintWriter out;

	public TaskServiceImpl(Map<String, List<Task>> tasks, PrintWriter writer) {
		this.tasks = tasks;
		this.out = writer;
	}
	@Override
	public void addTask(String project, String taskId, String description) {
		List<Task> projectTasks = tasks.get(project);
		if (projectTasks == null) {
			out.printf("Could not find a project with the name \"%s\".", project);
			out.println();
			return;
		}
		if(Utility.checkIdValidity(taskId))
			projectTasks.add(new Task(taskId, description, false));
		else
			out.println("Id should not contain spaces or special characters");
	}

	@Override
	public void addDeadlineToTask(String taskId, String deadline) {
		Date date = Utility.parseDate(deadline);
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			for(Task task: project.getValue()) {
				if(task.getId().equals(taskId)) {
					task.setDeadline(date);
					return;
				}
			}
		}
		out.printf("Could not find a task with an ID of %s.", taskId);
		out.println();
	}

}

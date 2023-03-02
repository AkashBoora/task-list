package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskServiceImpl implements TaskService{

	private Map<String, List<Task>> tasks = new LinkedHashMap<>();

	private final PrintWriter out;

	private long lastId;

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
		if(checkIdValidity(taskId))
			projectTasks.add(new Task(taskId, description, false));
		else
			out.println("Id should not contain spaces or special characters");
	}

	@Override
	public void addDeadlineToTask(String taskId, String deadline) {
		Date date = parseDate(deadline);
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			for(Task task: project.getValue()) {
				if(task.getId().equals(taskId)) {
					task.setDeadline(date);
					return;
				}
			}
		}
		out.printf("Could not find a task with an ID of %d.", taskId);
		out.println();
	}

	private Date parseDate(String deadline) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(deadline);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	private boolean checkIdValidity(String id) {
		if(id.indexOf(" ") != -1)
			return false;
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(id);
		return !(matcher.find());
	}
}

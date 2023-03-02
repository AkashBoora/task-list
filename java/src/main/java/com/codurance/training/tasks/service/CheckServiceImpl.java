package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheckServiceImpl implements CheckService {

	private final PrintWriter out;
	private Map<String, List<Task>> tasks = new LinkedHashMap<>();

	public CheckServiceImpl(Map<String, List<Task>> tasks, PrintWriter writer) {
		this.out = writer;
		this.tasks = tasks;
	}

	@Override
	public void check(String taskId) {
		setDone(taskId, true);
	}

	@Override
	public void uncheck(String taskId) {
		setDone(taskId, false);
	}

	private void setDone(String taskId, boolean isDone) {
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			for (Task task : project.getValue()) {
				if (task.getId().equals(taskId)) {
					task.setDone(isDone);
					return;
				}
			}
		}
		out.printf("Could not find a task with an ID of %d.", taskId);
		out.println();
	}
}

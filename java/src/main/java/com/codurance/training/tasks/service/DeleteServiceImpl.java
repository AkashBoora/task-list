package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class DeleteServiceImpl implements DeleteService{

	private final Map<String, List<Task>> tasks;

	private final PrintWriter out;

	public DeleteServiceImpl(Map<String, List<Task>> tasks, PrintWriter writer) {
		this.tasks = tasks;
		this.out = writer;
	}

	@Override
	public void delete(String taskId) {
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			for(Task task: project.getValue()) {
				if(task.getId().equals(taskId)) {
					project.getValue().remove(task);
					return;
				}
			}
		}
		out.printf("Could not find a task with an ID of %s.", taskId);
		out.println();
	}
}

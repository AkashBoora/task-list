package com.codurance.training.tasks.service;

public interface TaskService {
	public void addTask(String project, String taskId, String description);
	public void addDeadlineToTask(String taskId, String deadline);
}

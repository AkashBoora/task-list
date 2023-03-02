package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.util.List;
import java.util.Map;

public interface ShowService {
	public void showByProject(Map<String, List<Task>> tasks);
	public void showDueTodayTasks(Map<String, List<Task>> tasks);
	public void showByDate(Map<String, List<Task>> tasks);
	public void showByDeadline(Map<String, List<Task>> tasks);
}
package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectServiceImpl implements ProjectService{

	private final Map<String, List<Task>> tasks;

	public ProjectServiceImpl(Map<String, List<Task>> tasks) {
		this.tasks = tasks;
	}

	@Override
	public void addProject(String name) {
		tasks.put(name, new ArrayList<Task>());
	}
}

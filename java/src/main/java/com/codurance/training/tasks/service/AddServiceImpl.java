package com.codurance.training.tasks.service;

public class AddServiceImpl implements AddService {
	private final ProjectService projectService;
	private final TaskService taskService;

	public AddServiceImpl(ProjectService projectService, TaskService taskService) {
		this.projectService = projectService;
		this.taskService = taskService;
	}


	@Override
	public void add(String commandLine) {
		String[] subcommandRest = commandLine.split(" ", 2);
		String subcommand = subcommandRest[0];
		if (subcommand.equals("project")) {
			projectService.addProject(subcommandRest[1]);
		} else if (subcommand.equals("task")) {
			String[] projectTask = subcommandRest[1].split(" ", 3);
			taskService.addTask(projectTask[0], projectTask[1], projectTask[2]);
		}
	}
}

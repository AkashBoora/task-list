package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.*;

public class ShowServiceImpl implements ShowService {

	private final PrintWriter out;

	public ShowServiceImpl(PrintWriter writer) {
		this.out = writer;
	}
	@Override
	public void showByProject(Map<String, List<Task>> tasks) {
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			out.println(project.getKey());
			for (Task task : project.getValue()) {
				out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
			}
			out.println();
		}
	}

	@Override
	public void showDueTodayTasks(Map<String, List<Task>> tasks) {
		Date today = new Date();
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			out.println(project.getKey());
			for (Task task : project.getValue()) {
				if(task.getDeadline() != null && Utility.parseDateToString(task.getDeadline()).equals(Utility.parseDateToString(today)))
					out.printf("    [%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), Utility.parseDateToString(task.getDeadline()));
			}
			out.println();
		}
	}

	@Override
	public void showByDate(Map<String, List<Task>> tasks) {
		Comparator<Task> compareByDate = Comparator.comparing(p -> Utility.parseDateToString(p.getDeadline()));
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			out.println(project.getKey());
			List<Task> newTasks = project.getValue();
			Collections.sort(newTasks, compareByDate);
			for (Task task : newTasks) {
				out.printf("    [%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), (task.getDeadline()!=null ? Utility.parseDateToString(task.getDeadline()): ""));
			}
			out.println();
		}
	}

	@Override
	public void showByDeadline(Map<String, List<Task>> tasks) {
		Comparator<Task> compareByDate = Comparator.comparing(p -> Utility.parseDateToString(p.getDeadline()));
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			out.println(project.getKey());
			List<Task> newTasks = project.getValue();
			Collections.sort(newTasks, compareByDate);
			for (Task task : newTasks) {
				if(task.getDeadline() != null)
					out.printf("    [%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), Utility.parseDateToString(task.getDeadline()));
			}
			out.println();
		}
	}
}
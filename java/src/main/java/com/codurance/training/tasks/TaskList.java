package com.codurance.training.tasks;

import com.codurance.training.tasks.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    ShowService showService;
    CheckService checkService;
    ProjectService projectService;
    TaskService taskService;
    DeleteService deleteService;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        projectService = new ProjectServiceImpl(tasks);
        taskService = new TaskServiceImpl(tasks, out);
        deleteService = new DeleteServiceImpl(tasks, writer);
        checkService = new CheckServiceImpl(tasks, out);
        showService = new ShowServiceImpl(out);
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                checkService.check(commandRest[1]);
                break;
            case "uncheck":
                checkService.uncheck(commandRest[1]);
                break;
            case "delete":
                deleteService.delete(commandRest[1]);
                break;
            case "deadline":
                String[] taskDeadline = commandRest[1].split(" ", 2);
                taskService.addDeadlineToTask(taskDeadline[0], taskDeadline[1]);
                break;
            case "today":
                showService.showDueTodayTasks(tasks);
                break;
            case "view":
                view(commandRest[1]);
                break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            projectService.addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 3);
            taskService.addTask(projectTask[0], projectTask[1], projectTask[2]);
        }
    }

    private void view(String command) {
        if(command.equals("by date"))
            showService.showByDate(tasks);
        else if(command.equals("by deadline"))
            showService.showByDeadline(tasks);
        else if(command.equals("by project"))
            showService.showByProject(tasks);
        else
            error(command);
    }

    private void help() {
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

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

}
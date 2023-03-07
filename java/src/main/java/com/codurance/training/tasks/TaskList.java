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

    private final ShowService showService;
    private final CheckService checkService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final DeleteService deleteService;
    private final Utility utility;
    private final ExecuteService executeService;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        utility = new UtilityImpl();
        projectService = new ProjectServiceImpl(tasks);
        taskService = new TaskServiceImpl(tasks, out,utility);
        deleteService = new DeleteServiceImpl(tasks, writer);
        checkService = new CheckServiceImpl(tasks, out);
        showService = new ShowServiceImpl(out,utility);
        executeService = new ExecuteServiceImpl(this,checkService,deleteService,taskService,showService,tasks);
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
            executeService.execute(command);
        }
    }


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

    public void view(String command) {
        if(command.equals("by date"))
            showService.showByDate(tasks);
        else if(command.equals("by deadline"))
            showService.showByDeadline(tasks);
        else if(command.equals("by project"))
            showService.showByProject(tasks);
        else
            error(command);
    }

    public void help() {
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

    public void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

}
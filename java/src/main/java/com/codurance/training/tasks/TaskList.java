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
    private final AddService addService;
    private final ExecuteService executeService;
    private final TaskListUtility taskListUtility;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        this.projectService = new ProjectServiceImpl(tasks);
        this.taskService = new TaskServiceImpl(tasks, out);
        this.deleteService = new DeleteServiceImpl(tasks, writer);
        this.checkService = new CheckServiceImpl(tasks, out);
        this.showService = new ShowServiceImpl(out);
        this.taskListUtility = new TaskListUtility(tasks,out,showService);
        this.addService = new AddServiceImpl(projectService,taskService);
        this.executeService = new ExecuteServiceImpl(taskListUtility,checkService,deleteService,taskService,showService,tasks,addService);
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

}
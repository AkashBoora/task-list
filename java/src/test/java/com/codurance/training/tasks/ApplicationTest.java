package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ApplicationTest {
	public static final String PROMPT = "> ";
	private final PipedOutputStream inStream = new PipedOutputStream();
	private final PrintWriter inWriter = new PrintWriter(inStream, true);
	private final PipedInputStream outStream = new PipedInputStream();
	private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

	private final Thread applicationThread;

	public ApplicationTest() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
		PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);
		TaskList taskList = new TaskList(in, out);
		applicationThread = new Thread(taskList);
	}

	@Before
	public void
	start_the_application() {
		applicationThread.start();
	}

	@After
	public void
	kill_the_application() throws IOException, InterruptedException {
		if (!stillRunning()) {
			return;
		}

		Thread.sleep(1000);
		if (!stillRunning()) {
			return;
		}

		applicationThread.interrupt();
		throw new IllegalStateException("The application is still running.");
	}

	@Test(timeout = 1000)
	public void
	it_works() throws IOException, InterruptedException {
		execute("add project secrets");
		execute("add task secrets abc Eat more donuts.");
		execute("add task secrets def Destroy all humans.");

        execute("view by project");
        readLines(
            "secrets",
            "    [ ] abc: Eat more donuts.",
            "    [ ] def: Destroy all humans.",
            ""
        );

		execute("add project training");
		execute("add task training asd Four Elements of Simple Design");
		execute("add task training sdf SOLID");
		execute("add task training dfg Coupling and Cohesion");
		execute("add task training fgh Primitive Obsession");
		execute("add task training hjk Outside-In TDD");
		execute("add task training jkl Interaction-Driven Design");

		execute("check abc");
		execute("check asd");
		execute("check dfg");
		execute("check fgh");
		execute("uncheck fgh");
		execute("deadline abc 02-03-2023");
		execute("delete jkl");
		execute("view by project");
		readLines(
				"secrets",
				"    [x] abc: Eat more donuts.",
				"    [ ] def: Destroy all humans.",
				"",
				"training",
				"    [x] asd: Four Elements of Simple Design",
				"    [ ] sdf: SOLID",
				"    [x] dfg: Coupling and Cohesion",
				"    [ ] fgh: Primitive Obsession",
				"    [ ] hjk: Outside-In TDD",
				""
		);

		execute("quit");
	}

	private void execute(String command) throws IOException {
		read(PROMPT);
		write(command);
	}

	private void read(String expectedOutput) throws IOException {
		int length = expectedOutput.length();
		char[] buffer = new char[length];
		outReader.read(buffer, 0, length);
		assertThat(String.valueOf(buffer), is(expectedOutput));
	}

	private void readLines(String... expectedOutput) throws IOException {
		for (String line : expectedOutput) {
			read(line + lineSeparator());
		}
	}

	private void write(String input) {
		inWriter.println(input);
	}

	private boolean stillRunning() {
		return applicationThread != null && applicationThread.isAlive();
	}
}

package com.project.ngit;

import com.project.ngit.Commands.CreatorCommands.CommitCommand;
import com.project.ngit.Commands.CreatorCommands.AddCommand;
import com.project.ngit.Commands.CreatorCommands.InitCommand;
import com.project.ngit.Commands.InfoCommands.ConstatCommand;
import com.project.ngit.Commands.InfoCommands.InfoCommand;
import com.project.ngit.Commands.InfoCommands.StatusCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class DgitApplication {
	final static String GLOBAL_REPOSITORY_NAME = System.getProperty("user.dir");

	public static void main(String[] args) throws IOException {
		DgitApplication gitClone = new DgitApplication();
		gitClone.processCommand(args);
	}

	private void processCommand(String[] input) throws IOException {
		System.out.println(Arrays.toString(input));

		if (input.length < 1) {return; }

		String command = input[0];
		String argument = (input.length > 1) ? input[1] : null;

		switch (command) {
			case "init" ->  InitCommand.execute(GLOBAL_REPOSITORY_NAME);
			case "add" -> AddCommand.execute(GLOBAL_REPOSITORY_NAME, argument);
			case "info" -> InfoCommand.execute(GLOBAL_REPOSITORY_NAME, argument);
			case "status" -> StatusCommand.execute(GLOBAL_REPOSITORY_NAME);
			case "commit" -> CommitCommand.execute(GLOBAL_REPOSITORY_NAME);
			case "constat" -> ConstatCommand.execute(GLOBAL_REPOSITORY_NAME);
			default -> System.out.println("Unknown ngit command");
		}
	}

	public static void makeFolder(String folderName, String repositoryPath) {
		Path dirPath = Paths.get(repositoryPath + "/" + folderName);

		if (directoryExists(dirPath)) {
			System.err.println("Already exists");
			return;
		}

		try {
			Files.createDirectory(dirPath);
		} catch (IOException e) {
			System.err.println("Failed to create directory: " + e.getMessage());
		}
	}

	protected static boolean directoryExists(Path directory) {
		return Files.exists(directory) && Files.isDirectory(directory);
	}
}

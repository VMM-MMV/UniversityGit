package com.project.ngit.Commands.CreatorCommands;

import com.project.ngit.Commands.InfoCommands.FileStatus;
import com.project.ngit.DataBase.DataBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AddCommand {
    static Path ngitPath;
    static String repositoryPath;
    static List<FileStatus> existingData = new ArrayList<>();

    public static void execute(String pathOfRepository, String argument) {
        if (argument == null) {
            System.out.println("No argument provided for add command.");
            return;
        }

        repositoryPath = pathOfRepository;
        ngitPath = Path.of(repositoryPath, ".ngit");

        existingData = DataBase.loadSerializedData(ngitPath.resolve("index/changes.ser"));

        if (argument.equals(".")) {
            processAllFilesInRepository();
        } else {
            processSingleFile(argument);
        }

        DataBase.saveSerializedData(ngitPath.resolve("index/changes.ser"), existingData);
    }

    private static void processAllFilesInRepository() {
        try (Stream<Path> stream = Files.walk(Path.of(repositoryPath))) {
            stream.forEach(AddCommand::processPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processSingleFile(String argument) {
        Path filePath = Path.of(repositoryPath).resolve(argument);
        processPath(filePath);
    }

    private static void processPath(Path path) {
        String pathString = path.toString();
        if (pathString.contains(".ngit")) {
            return;
        }

        FileTime lastModifiedTime;
        try {
            lastModifiedTime = Files.getLastModifiedTime(path);
        } catch (IOException e) {
            System.out.println("Error getting last modified time for: " + path);
            return;
        }

        FileStatus fileStatus = findFileStatusByPath(path.toString());
        if (fileStatus == null) {
            fileStatus = new FileStatus(path.toString(), lastModifiedTime.toString(), lastModifiedTime.toString(), false);
            existingData.add(fileStatus);
        } else {
            int index = existingData.indexOf(fileStatus);
            fileStatus = new FileStatus(path.toString(), lastModifiedTime.toString(), fileStatus.initialTimestamp(), false);
            existingData.set(index, fileStatus);
        }
    }

    private static FileStatus findFileStatusByPath(String path) {
        for (FileStatus fileStatus : existingData) {
            if (fileStatus.path().equals(path)) {
                return fileStatus;
            }
        }
        return null;
    }

}

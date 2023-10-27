package com.project.ngit.Commands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AddCommand {
    static Path ngitPath;
    static String repositoryPath;
    static Map<String, FileStatus> existingData = new HashMap<>();

    public static void execute(String repositoryPath, String argument) {
        if (argument == null) {
            System.out.println("No argument provided for add command.");
            return;
        }

        AddCommand.repositoryPath = repositoryPath;
        ngitPath = Path.of(repositoryPath, ".ngit");

        existingData = loadSerializedData(ngitPath.resolve("index/changes.ser"));

        if (argument.equals(".")) {
            processAllFilesInRepository();
        } else {
            processSingleFile(argument);
        }
        saveSerializedData(ngitPath.resolve("index/changes.ser"), existingData);
    }

    private static void processAllFilesInRepository() {
        try (Stream<Path> stream = Files.walk(Path.of(repositoryPath))) {
            stream.forEach(AddCommand::processPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processSingleFile(String argument) {
        Path filePath = ngitPath.resolve(argument);
        processPath(filePath);
    }

    private static void processPath(Path path) {
        FileTime lastModifiedTime;
        try {
            lastModifiedTime = Files.getLastModifiedTime(path);
        } catch (IOException e) {
            System.out.println("Error getting last modified time for: " + path);
            return;
        }

        System.out.println(path + " last modified: " + lastModifiedTime);

        FileStatus fileStatus = existingData.get(path.toString());
        if (fileStatus == null) {
            fileStatus = new FileStatus(path.toString(), lastModifiedTime.toString(), lastModifiedTime.toString());
        } else {
            fileStatus = new FileStatus(fileStatus.path(), lastModifiedTime.toString(), fileStatus.initialTimestamp());
        }
        existingData.put(path.toString(), fileStatus);
    }

    static Map<String, FileStatus> loadSerializedData(Path filePath) {
        if (!Files.exists(filePath)) {
            return new LinkedHashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (Map<String, FileStatus>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load serialized data", e);
        }
    }

    private static void saveSerializedData(Path filePath, Map<String, FileStatus> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            out.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

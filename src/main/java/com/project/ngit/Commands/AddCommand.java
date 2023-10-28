package com.project.ngit.Commands;

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
            fileStatus = new FileStatus(path.toString(), lastModifiedTime.toString(), lastModifiedTime.toString());
            existingData.add(fileStatus);
        } else {
            int index = existingData.indexOf(fileStatus);
            fileStatus = new FileStatus(path.toString(), lastModifiedTime.toString(), fileStatus.initialTimestamp());
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

    static List<FileStatus> loadSerializedData(Path filePath) {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (List<FileStatus>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load serialized data", e);
        }
    }

    private static void saveSerializedData(Path filePath, List<FileStatus> data) {
        try {
            Files.createDirectories(filePath.getParent());

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                out.writeObject(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories", e);
        }
    }
}

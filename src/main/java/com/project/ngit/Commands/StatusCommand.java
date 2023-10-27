package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;
import java.util.Map;

public class StatusCommand {
    public static void execute(String repositoryPath) {
        Path ngitPath = Path.of(repositoryPath, ".ngit");
        Map<String, String> serializedData = AddCommand.loadSerializedData(ngitPath.resolve("index/changes.ser"));

        for (Map.Entry<String, String> entry : serializedData.entrySet()) {
            if (entry.getKey().endsWith("-initial")) continue; // Skip initial timestamps when iterating

            String filePath = entry.getKey();
            String activeTimestamp = entry.getValue();
            String initialTimestamp = serializedData.get(filePath + "-initial");

            Path actualPath = Path.of(repositoryPath, filePath);
            if (Files.exists(actualPath)) {
                FileTime currentModifiedTime;
                try {
                    currentModifiedTime = Files.getLastModifiedTime(actualPath);
                } catch (IOException e) {
                    System.out.println("Error getting last modified time for: " + actualPath);
                    continue;
                }

                if (initialTimestamp.equals(activeTimestamp)) {
                    System.out.println(actualPath + " is a new file.");
                } else if (!activeTimestamp.equals(currentModifiedTime.toString())) {
                    System.out.println(actualPath + " has been modified. Stored: " + activeTimestamp + " Current: " + currentModifiedTime);
                }
            } else {
                System.out.println(actualPath + " no longer exists.");
            }
        }
    }
}

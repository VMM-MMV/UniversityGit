package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Map;

public class StatusCommand {

    public static void execute(String repositoryPath) {
        Path ngitPath = Path.of(repositoryPath, ".ngit");
        Map<String, FileStatus> serializedData = AddCommand.loadSerializedData(ngitPath.resolve("index/changes.ser"));

        for (Map.Entry<String, FileStatus> entry : serializedData.entrySet()) {
            String filePath = entry.getKey();
            FileStatus fileStatus = entry.getValue();

            Path actualPath = Path.of(filePath);
            if (Files.exists(actualPath)) {
                FileTime currentModifiedTime;
                try {
                    currentModifiedTime = Files.getLastModifiedTime(actualPath);
                } catch (IOException e) {
                    System.out.println("Error getting last modified time for: " + actualPath);
                    continue;
                }

                String pathStr = actualPath.toString();
                String[] parts = pathStr.split("UniversityGit");
                String relativePath;
                if (parts.length > 1) {
                    relativePath = parts[1];
                    System.out.println(relativePath);
                } else {
                    relativePath = parts[0];
                }

                if (fileStatus.initialTimestamp().equals(fileStatus.activeTimestamp())) {
                    System.out.println(relativePath + " is a new file.");
                } else if (!fileStatus.activeTimestamp().equals(currentModifiedTime.toString())) {
                    System.out.println(relativePath + " has been modified. Stored: " + fileStatus.activeTimestamp() + " Current: " + currentModifiedTime);
                }

            } else {
                System.out.println(actualPath + " no longer exists.");
            }
        }
    }
}

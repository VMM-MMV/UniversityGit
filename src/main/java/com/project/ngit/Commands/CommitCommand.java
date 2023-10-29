package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

public class CommitCommand {

    public static void execute(String repositoryPath) {
        Path ngitPath = Path.of(repositoryPath, ".ngit");
        List<FileStatus> oldSerializedData = AddCommand.loadSerializedData(ngitPath.resolve("index/changes.ser"));
        List<FileStatus> newSerializedData = new ArrayList<>();

        for (FileStatus fileStatus : oldSerializedData) {
            String filePath = fileStatus.path();
            Path actualPath = Path.of(filePath);

            if (!Files.exists(actualPath) || Files.isDirectory(actualPath)) {
                continue;
            }

            FileTime currentModifiedTime;
            try {
                currentModifiedTime = Files.getLastModifiedTime(actualPath);
            } catch (IOException e) {
                System.out.println("Error getting last modified time for: " + actualPath);
                continue;
            }

            // Create a new FileStatus with the updated active timestamp
            FileStatus updatedFileStatus = new FileStatus(
                    filePath,
                    currentModifiedTime.toString(),
                    fileStatus.initialTimestamp(),
                    true
            );
            newSerializedData.add(updatedFileStatus);
        }

        // Save the updated serialized data
        AddCommand.saveSerializedData(ngitPath.resolve("index/changes.ser"), newSerializedData);
    }
}

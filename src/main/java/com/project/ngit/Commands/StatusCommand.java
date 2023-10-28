package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StatusCommand {

    public static void execute(String repositoryPath) {
        Path ngitPath = Path.of(repositoryPath, ".ngit");
        Map<String, FileStatus> serializedData = AddCommand.loadSerializedData(ngitPath.resolve("index/changes.ser"));

        for (Map.Entry<String, FileStatus> entry : serializedData.entrySet()) {
            String filePath = entry.getKey();
            FileStatus fileStatus = entry.getValue();

            Path actualPath = Path.of(filePath);
            if (!Files.exists(actualPath)) {
                System.out.println(actualPath + " Is deleted");
                continue;
            }

            if (Files.isDirectory(actualPath)) {
                continue;
            }

            FileTime currentModifiedTime;
            try {
                currentModifiedTime = Files.getLastModifiedTime(actualPath);
            } catch (IOException e) {
                System.out.println("Error getting last modified time for: " + actualPath);
                continue;
            }

            String repoCore = getNameOfRepoCore(String.valueOf(ngitPath));
            String relativePath = getRelativePath(filePath, repoCore);

            if (fileStatus.initialTimestamp().equals(fileStatus.activeTimestamp())) {
                System.out.println(relativePath + " is a new file.");
            } else if (!fileStatus.activeTimestamp().equals(currentModifiedTime.toString())) {
                System.out.println(relativePath + " has been modified.");
            }
        }
    }

    private static String getRelativePath(String path, String repoCore) {
        String[] parts = path.split(repoCore + "\\\\");
        if (parts.length > 1) {
            return parts[1];
        } else {
            return parts[0];
        }
    }

    private static String getNameOfRepoCore(String path) {
        List<String> parts = List.of(path.split("\\\\"));
        int repoIndex = parts.indexOf(".ngit");
        return parts.get(repoIndex - 1);
    }
}

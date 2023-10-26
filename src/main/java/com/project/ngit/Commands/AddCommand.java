package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AddCommand {
    static Path ngitPath;
    static String repositoryPath;
    static Map<String, String> existingData = new LinkedHashMap<>();

    public static void execute(String repositoryPath, String argument) {
        if (argument == null) {
            System.out.println("No argument provided for add command.");
            return;
        }

        AddCommand.repositoryPath = repositoryPath;
        ngitPath = Path.of(repositoryPath, ".ngit");

        existingData = readExistingJsonData(ngitPath.resolve("index/changes.json"));

        if (argument.equals(".")) {
            processAllFilesInRepository();
        } else {
            processSingleFile(argument);
        }
        saveToJsonFile(ngitPath.resolve("index/changes.json"), existingData);
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
        FileTime lastModifiedTime = DgitApplication.getLastModifiedTime(path);
        System.out.println(path + " last modified: " + lastModifiedTime);
        existingData.put(path.toString(), lastModifiedTime.toString());
    }

    private static Map<String, String> readExistingJsonData(Path filePath) {
        Map<String, String> data = new LinkedHashMap<>();
        if (!Files.exists(filePath)) {
            return data;
        }

        try {
            String content = new String(Files.readAllBytes(filePath));
            String[] pairs = content.replace("{", "").replace("}", "").split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    data.put(keyValue[0].trim().replace("\"", ""), keyValue[1].trim().replace("\"", ""));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read existing JSON data", e);
        }
        return data;
    }

    private static void saveToJsonFile(Path filePath, Map<String, String> data) {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        if (!data.isEmpty()) {
            sb.setLength(sb.length() - 1);  // remove last comma
        }
        sb.append("}");

        try {
            Files.write(filePath, sb.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

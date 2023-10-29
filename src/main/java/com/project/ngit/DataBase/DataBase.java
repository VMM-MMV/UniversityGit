package com.project.ngit.DataBase;

import com.project.ngit.Commands.InfoCommands.FileStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static List<FileStatus> loadSerializedData(Path filePath) {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (List<FileStatus>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load serialized data", e);
        }
    }

    public static void saveSerializedData(Path filePath, List<FileStatus> data) {
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

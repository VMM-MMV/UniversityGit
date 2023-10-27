package com.project.ngit.Commands;

import com.project.ngit.Files.*;
import com.project.ngit.Files.CodeFiles.CodeFile;
import com.project.ngit.Files.CodeFiles.JavaCodeFile;
import com.project.ngit.Files.CodeFiles.PythonCodeFile;

import java.io.IOException;
import java.nio.file.Path;

public class InfoCommand {

    public static void execute(String repositoryPath, String file) throws IOException {
        String[] fileNameAndExtension = getFileNameAndExtension(file);
        String filePath = repositoryPath + "\\" + file;
        String extension = fileNameAndExtension[1];

        switch (extension) {
            case "txt" -> {
                TXTFile txtFile = new TXTFile(filePath);
                printFileInfo(txtFile);
            }

            case "jpg", "jpeg", "png", "bmp", "gif" -> {
                ImageFile imageFile = new ImageFile(filePath);
                printFileInfo(imageFile);
                System.out.println("Size: " + imageFile.getHeight() + "x" + imageFile.getWidth());
            }

            case "py" -> {
                PythonCodeFile pythonFile = new PythonCodeFile(filePath);
                printFileInfo(pythonFile);
                printCodeFileInfo(pythonFile);
            }

            case "java" -> {
                JavaCodeFile javaFile = new JavaCodeFile(filePath);
                printFileInfo(javaFile);
                printCodeFileInfo(javaFile);
            }
        }
    }

    private static void printFileInfo(GeneralFile file) {
        System.out.println("File: " + file.getNameAndExtension());
        System.out.println("Created at: " + file.getTimeOfCreation());
        System.out.println("Modified at: " + file.getTimeOfModification());
    }

    private static void printCodeFileInfo(CodeFile file) {
        System.out.println("Classes: " + file.getClassCount());
        System.out.println("Methods: " + file.getMethodCount());
        System.out.println("Lines: " + file.getLineCount());
    }

    private static String[] getFileNameAndExtension(String file) {
        String filenameStr = Path.of(file).getFileName().toString();
        String[] parts = filenameStr.split("\\.(?=[^\\.]+$)");
        if (parts.length == 2) {
            return parts;
        } else {
            return new String[] {filenameStr, ""};
        }
    }

}

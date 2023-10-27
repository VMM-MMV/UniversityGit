package com.project.ngit.Commands;

import com.project.ngit.Files.*;

import java.io.IOException;
import java.nio.file.Path;

public class InfoCommand {
//
//    public static void main(String[] args) throws IOException {
//        InfoCommand.execute("C:\\Users\\Miguel\\IdeaProjects\\UniversityGit", "1.txt");
//    }

    public static void execute(String repositoryPath, String file) throws IOException {
        String[] fileNameAndExtension = getFileNameAndExtension(file);
        String filePath = repositoryPath +"\\"+ file;
        String extension = fileNameAndExtension[1];

        switch (extension) {
            case "txt" -> {
                TXTFile txtFile = new TXTFile(filePath);
                System.out.println(txtFile.getLineCount());
                printFileInfo(txtFile);
            }

            case "jpg", "jpeg", "png", "bmp", "gif" -> {
                ImageFile imageFile = new ImageFile(filePath);
                printFileInfo(imageFile);
            }

            case "py" -> {
                PythonCodeAnalyzer pythonFile = new PythonCodeAnalyzer(filePath);
                System.out.println(pythonFile.getMethodCount());
                printFileInfo(pythonFile);
            }

            case "java" -> {
                JavaCodeAnalyzer javaCodeAnalyzer = new JavaCodeAnalyzer(filePath);
                System.out.println(javaCodeAnalyzer.getMethodCount());
                printFileInfo(javaCodeAnalyzer);
            }
        }
    }

    private static void printFileInfo(GeneralFile file) {
        System.out.println("File: " + file.getNameAndExtension() + " Created at: " + file.getTimeOfCreation() + " Modified at: " + file.getTimeOfModification());
    }

    protected static String[] getFileNameAndExtension(String file) {
        String filenameStr = Path.of(file).getFileName().toString();
        String[] parts = filenameStr.split("\\.(?=[^\\.]+$)");
        if (parts.length == 2) {
            return parts;
        } else {
            return new String[] {filenameStr, ""};
        }
    }

}

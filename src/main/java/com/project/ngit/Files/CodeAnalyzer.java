package com.project.ngit.Files;

import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public abstract class CodeAnalyzer extends GeneralFile {
    public abstract int getClassCount();
    public abstract int getMethodCount();

    protected String code;

    public CodeAnalyzer(String filePath) throws IOException {
        this.code = new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public int countOccurrences(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.code);
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public int getLineCount() {
        return (int) code.lines().count();
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Miguel\\Downloads\\1.java";  // Replace with your file path
        CodeAnalyzer analyzer;

        if (filePath.endsWith(".py")) {
            try {
                analyzer = new PythonCodeAnalyzer(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (filePath.endsWith(".java")) {
            try {
                analyzer = new JavaCodeAnalyzer(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        System.out.println("Number of classes: " + analyzer.getClassCount());
        System.out.println("Number of methods: " + analyzer.getMethodCount());
        System.out.println(analyzer.getLineCount());
    }
}

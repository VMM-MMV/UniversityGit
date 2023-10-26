package com.project.ngit.Files;

import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public abstract class CodeAnalyzer {
    public abstract int countClasses();
    public abstract int countMethods();

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

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\Miguel\\Downloads\\1.java";  // Replace with your file path
        CodeAnalyzer analyzer;

        if (filePath.endsWith(".py")) {
            analyzer = new PythonCodeAnalyzer(filePath);
        } else if (filePath.endsWith(".java")) {
            analyzer = new JavaCodeAnalyzer(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        System.out.println("Number of classes: " + analyzer.countClasses());
        System.out.println("Number of methods: " + analyzer.countMethods());
    }
}

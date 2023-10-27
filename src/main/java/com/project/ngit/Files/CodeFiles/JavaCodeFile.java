package com.project.ngit.Files.CodeFiles;

import com.project.ngit.Files.CodeFiles.CodeFile;

import java.io.IOException;

public class JavaCodeFile extends CodeFile {
    private static final String CLASS_REGEX = "\\bclass\\s+";
    private static final String METHOD_REGEX = "\\b\\w+(?:<[^>]+>)?\\s+\\w+\\s*\\([^)]*\\)\\s*\\{";

    public JavaCodeFile(String filePath) throws IOException {
        super(filePath);
    }

    public int getClassCount() {
        return countOccurrences(CLASS_REGEX);
    }

    public int getMethodCount() {
        return countOccurrences(METHOD_REGEX);
    }
}



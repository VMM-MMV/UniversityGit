package com.project.ngit.Files.CodeFiles;

import com.project.ngit.Files.CodeFiles.CodeFile;

import java.io.IOException;

public class PythonCodeFile extends CodeFile {
    private static final String CLASS_REGEX = "\\bclass\\s+\\w+\\s*:";
    private static final String METHOD_REGEX = "\\bdef\\s+\\w+\\s*\\(";

    public PythonCodeFile(String filePath) throws IOException {
        super(filePath);
    }

    public int getClassCount() {
        return countOccurrences(CLASS_REGEX);
    }

    public int getMethodCount() {
        return countOccurrences(METHOD_REGEX);
    }
}

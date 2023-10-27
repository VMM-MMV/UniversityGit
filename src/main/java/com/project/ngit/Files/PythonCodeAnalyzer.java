package com.project.ngit.Files;

import java.io.IOException;

public class PythonCodeAnalyzer extends CodeAnalyzer {
    private static final String CLASS_REGEX = "\\bclass\\s+\\w+\\s*:";
    private static final String METHOD_REGEX = "\\bdef\\s+\\w+\\s*\\(";

    public PythonCodeAnalyzer(String filePath) throws IOException {
        super(filePath);
    }

    public int getClassCount() {
        return countOccurrences(CLASS_REGEX);
    }

    public int getMethodCount() {
        return countOccurrences(METHOD_REGEX);
    }
}

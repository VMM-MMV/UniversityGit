package com.project.ngit.Files;

import java.io.IOException;

class JavaCodeAnalyzer extends CodeAnalyzer {
    private static final String CLASS_REGEX = "\\bclass\\s+\\w+\\s*\\{";
    private static final String METHOD_REGEX = "\\b\\w+(?:<[^>]+>)?\\s+\\w+\\s*\\([^)]*\\)\\s*\\{";

    public JavaCodeAnalyzer(String filePath) throws IOException {
        super(filePath);
    }

    public int countClasses() {
        return countOccurrences(CLASS_REGEX);
    }

    public int countMethods() {
        return countOccurrences(METHOD_REGEX);
    }
}



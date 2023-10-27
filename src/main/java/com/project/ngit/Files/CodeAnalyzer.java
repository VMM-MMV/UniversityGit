package com.project.ngit.Files;

import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public abstract class CodeAnalyzer extends GeneralFile {
    public abstract int getClassCount();
    public abstract int getMethodCount();

    protected String code;

    public CodeAnalyzer(String filePath) throws IOException {
        super(filePath);
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
}

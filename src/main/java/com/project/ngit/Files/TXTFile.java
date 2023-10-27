package com.project.ngit.Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TXTFile extends GeneralFile {
    private final int lineCount;
    private final int wordCount;
    private final int charCount;

    public TXTFile(String filePath) throws IOException {
        super(filePath);
        Path path = Path.of(filePath);
        String code = new String(Files.readAllBytes(path));
        this.charCount = code.length();
        this.lineCount = (int) code.lines().count();

        String cleanedCode = code.replaceAll("[^a-zA-Z\\s]", "");
        String[] words = cleanedCode.split("\\s+");
        this.wordCount = words.length;
    }

    public int getCharCount() {
        return charCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}


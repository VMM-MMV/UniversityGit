package com.project.ngit.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TXTFile extends GeneralFile {
    private int lineCount;
    private int wordCount;
    private int charCount;

//    public static void main(String[] args) {
//        TXTFile txtFile = null;
//        try {
//            txtFile = new TXTFile("C:\\Users\\Miguel\\Downloads\\2.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(txtFile.getCharCount() + " " + txtFile.getWordCount() + " " + txtFile.getLineCount());
//    }

    public TXTFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String code = new String(Files.readAllBytes(path));
        charCount = code.length();
        lineCount = (int) code.lines().count();

        String cleanedCode = code.replaceAll("[^a-zA-Z\\s]", "");
        String[] words = cleanedCode.split("\\s+");
        wordCount = words.length;
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


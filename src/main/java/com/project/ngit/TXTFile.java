package com.project.ngit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TXTFile {
    int lineCount = 0;
    int wordCount = 0;
    int charCount = 0;

    public static void main(String[] args) {
        TXTFile txtFile = new TXTFile(new File("C:\\Users\\Miguel\\Downloads\\1.txt"));
        System.out.println(txtFile.getCharCount() + " " + txtFile.getWordCount() + " " + txtFile.getLineCount());
    }

    public TXTFile (File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();

                String cleanedLine = line.replaceAll("[^a-zA-Z\\s]", "");

                String[] words = cleanedLine.split("\\s+");
                wordCount += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

package com.project.ngit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TXTFile {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Miguel\\Downloads\\1.txt");
        getStatistics(file);
    }

    public static void getStatistics(File file) {
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

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

        System.out.println("Line Count: " + lineCount);
        System.out.println("Word Count: " + wordCount);
        System.out.println("Character Count: " + charCount);
    }
}

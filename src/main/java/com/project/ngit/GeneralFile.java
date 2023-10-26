package com.project.ngit;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.function.Function;

public abstract class GeneralFile {

    public static void main(String[] args) {
        System.out.println(getPrettyFileNameAndExtension(Path.of("C:\\Users\\Miguel\\Downloads\\1.txt")));
    }

    protected FileTime getCreatedFileTime(Path path) {
        return getFileTime(path, BasicFileAttributes::creationTime);
    }

    protected static FileTime getLastModifiedFileTime(Path path) {
        return getFileTime(path, BasicFileAttributes::lastModifiedTime);
    }

    private static FileTime getFileTime(Path path, Function<BasicFileAttributes, FileTime> fileTimeExtractor) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return fileTimeExtractor.apply(attrs);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected static String getPrettyFileNameAndExtension(Path path) {
        String fileName = path.getFileName().toString();
        return fileName;
    }
}

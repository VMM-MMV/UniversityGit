package com.project.ngit.Files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.function.Function;

public abstract class GeneralFile {
    private FileTime timeOfCreation;
    private FileTime timeOfModification;
    private String nameAndExtension;

    public GeneralFile(Path path) {
        this.timeOfCreation = getCreatedFileTime(path);
        this.timeOfModification = getLastModifiedFileTime(path);
        this.nameAndExtension = getPrettyFileNameAndExtension(path);
    }

    private FileTime getCreatedFileTime(Path path) {
        return getFileTime(path, BasicFileAttributes::creationTime);
    }

    private static FileTime getLastModifiedFileTime(Path path) {
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

    private static String getPrettyFileNameAndExtension(Path path) {
        return path.getFileName().toString();
    }

    // Getter methods for the new fields
    public FileTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public FileTime getTimeOfModification() {
        return timeOfModification;
    }

    public String getNameAndExtension() {
        return nameAndExtension;
    }
}

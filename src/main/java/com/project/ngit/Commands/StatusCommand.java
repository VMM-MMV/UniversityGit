package com.project.ngit.Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.concurrent.*;

public class StatusCommand {
    public static void execute(String repositoryPath) {
        Path ngitPath = Path.of(repositoryPath, ".ngit");
        List<FileStatus> serializedData = AddCommand.loadSerializedData(ngitPath.resolve("index/changes.ser"));

        ConcurrentLinkedQueue<FileStatus> fileStatusQueue = new ConcurrentLinkedQueue<>(serializedData);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch latch = new CountDownLatch(fileStatusQueue.size());

        while (!fileStatusQueue.isEmpty()) {
            FileStatus fileStatus = fileStatusQueue.poll();
            if (fileStatus != null) {
                executorService.submit(new StatusTask(fileStatus, ngitPath, latch));
            }
        }

        executorService.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Execution was interrupted");
        }
    }

    private static class StatusTask implements Runnable {
        private final FileStatus fileStatus;
        private final Path ngitPath;
        private final CountDownLatch latch;

        public StatusTask(FileStatus fileStatus, Path ngitPath, CountDownLatch latch) {
            this.fileStatus = fileStatus;
            this.ngitPath = ngitPath;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                String filePath = fileStatus.path();
                Path actualPath = Path.of(filePath);

                if (!Files.exists(actualPath)) {
                    System.out.println(actualPath + " is deleted");
                    return;
                }

                if (Files.isDirectory(actualPath)) {
                    return;
                }

                FileTime currentModifiedTime;
                try {
                    currentModifiedTime = Files.getLastModifiedTime(actualPath);
                } catch (IOException e) {
                    System.out.println("Error getting last modified time for: " + actualPath);
                    return;
                }

                String repoCore = getNameOfRepoCore(String.valueOf(ngitPath));
                String relativePath = getRelativePath(filePath, repoCore);

                boolean fileIsNotCommitted = !fileStatus.isCommitted();
                boolean firstSetDateIsEqualToTheUpdatedDate = fileStatus.initialTimestamp().equals(fileStatus.activeTimestamp());
                boolean theUpdatedDateIsEqualToTheCurrentDate = fileStatus.activeTimestamp().equals(currentModifiedTime.toString());

                boolean updatedDateIsNotEqualToCurrentCalculatedDate = !fileStatus.activeTimestamp().equals(currentModifiedTime.toString());
                if (fileIsNotCommitted && firstSetDateIsEqualToTheUpdatedDate && theUpdatedDateIsEqualToTheCurrentDate) {
                    System.out.println(relativePath + " is a new file.");
                } else if (updatedDateIsNotEqualToCurrentCalculatedDate) {
                    System.out.println(relativePath + " has been modified.");
                }
            } finally {
                latch.countDown();
            }
        }

        private static String getRelativePath(String path, String repoCore) {
            String[] parts = path.split(repoCore + "\\\\");
            if (parts.length > 1) {
                return parts[1];
            } else {
                return parts[0];
            }
        }

        private static String getNameOfRepoCore(String path) {
            List<String> parts = List.of(path.split("\\\\"));
            int repoIndex = parts.indexOf(".ngit");
            return parts.get(repoIndex - 1);
        }
    }
}

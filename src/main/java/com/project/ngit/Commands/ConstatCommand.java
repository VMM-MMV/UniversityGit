package com.project.ngit.Commands;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConstatCommand {
    private static ScheduledExecutorService scheduler;

    public static void execute(String repositoryPath) {
        if (scheduler != null && !scheduler.isShutdown()) {
            System.out.println("Constat is already running.");
            return;
        }

        scheduler = Executors.newScheduledThreadPool(10);
        Runnable statusTask = () -> StatusCommand.execute(repositoryPath);
        scheduler.scheduleAtFixedRate(statusTask, 0, 5, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }
}

package com.pandaism.serializer.thread;

import com.pandaism.serializer.Main;

import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * Used to manage the status of all URLConnection executors
 */
public class ExecutorSchedulerThread implements Runnable {
    private boolean running;

    public ExecutorSchedulerThread() {
        this.running = true;
    }

    @Override
    public void run() {
        while (this.running) {
            Main.exportable = Main.EXECUTORS.stream().allMatch(ExecutorService::isTerminated);
            Main.EXECUTORS = Main.EXECUTORS.stream().filter(executorService -> !executorService.isTerminated()).collect(Collectors.toList());
        }
    }
}

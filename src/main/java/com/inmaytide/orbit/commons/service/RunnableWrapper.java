package com.inmaytide.orbit.commons.service;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author inmaytide
 * @since 2023/5/16
 */

public record RunnableWrapper(Runnable runnable) {

    private static final ExecutorService executor = Executors.newFixedThreadPool(5, new BasicThreadFactory.Builder().namingPattern("commons-service-%d").build());

    public void run() {
        try {
            executor.submit(runnable).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void execute(Runnable runnable) {
        new RunnableWrapper(runnable).run();
    }

}

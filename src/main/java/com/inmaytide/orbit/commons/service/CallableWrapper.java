package com.inmaytide.orbit.commons.service;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author inmaytide
 * @since 2023/5/16
 */
public record CallableWrapper<T>(Callable<T> callable) {

    private static final ExecutorService executor = Executors
            .newFixedThreadPool(5, new BasicThreadFactory.Builder().namingPattern("commons-service-%d").build());

    public T call() {
        try {
            return executor.submit(callable).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T call(Supplier<T> supplier) {
        return new CallableWrapper<>(supplier::get).call();
    }

}


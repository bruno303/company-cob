package com.companycob.testsbase.utils;

import java.util.concurrent.CompletableFuture;

public class UnitTestsUtils {

    private UnitTestsUtils() { }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    public static void awaitAllCompletableFutures(CompletableFuture<?>... completableFutures) {
        CompletableFuture.allOf(completableFutures).join();
    }

}

package org.example.taskmanager;

import lombok.AllArgsConstructor;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Question: <a href="https://stackoverflow.com/q/77916705/11289119">JUnit Test for CompletableFuture supplyAsync</a>
 *
 * @author stefano-riffaldi
 */
@Slf4j
@AllArgsConstructor
public class TaskManager {
    private final TaskExecutor taskExecutor;

    public void cancel() throws TimeoutException, ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                taskExecutor.cancelTask();// function that cancels a task
            } catch (ApiException ex) {
                log.error("Api exception while cancelling a tasks");
            }
            return null;
        }).get(taskExecutor.getCancelTimeoutInMillis(), TimeUnit.MILLISECONDS);
    }

    @StandardException
    public static class ApiException extends RuntimeException {
    }
}


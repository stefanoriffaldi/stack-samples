package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.example.taskmanager.TaskExecutor;
import org.example.taskmanager.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeoutException;

@Slf4j
class TaskManagerTest {
    @Test
    public void testCancel() throws Exception {

        // Create the CompletableFuture under test
        TaskExecutor taskExecutor = new TaskExecutor(500) {
            @Override
            public void cancelTask() throws TaskManager.ApiException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        TaskManager manager = new TaskManager(taskExecutor);

        // Verify that the expected exception is thrown
        Assertions.assertThatThrownBy(manager::cancel).isInstanceOf(TimeoutException.class);
    }
}
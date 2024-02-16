package org.example.taskmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskExecutor {
    private final int cancelTimeoutInMillis;

    public void cancelTask() throws TaskManager.ApiException {
    }
}

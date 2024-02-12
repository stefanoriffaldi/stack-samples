package org.example.relativepath;

import java.nio.file.Path;

public class RelativePathService {
    private final String directoryPath;

    public RelativePathService(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void doSomethingWithDirectoryPath() {
        System.out.println("Directory path: " + Path.of(directoryPath));
    }
}

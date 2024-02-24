package org.example.datasource;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class FileDataSource extends AbstractDataSource {

    private final Path path;

    @Override
    public InputStream openInputStream() {
        return open(() -> Files.newInputStream(path));
    }

    @Override
    public OutputStream openOutputStream() {
        return open(() -> Files.newOutputStream(path));
    }

    @Override
    public Reader openReader() {
        return open(() -> new BufferedReader(super.openReader()));
    }

    @Override
    public Writer openWriter() {
        return open(() -> new BufferedWriter(super.openWriter()));
    }
}

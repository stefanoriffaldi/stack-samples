package org.example.test;


import lombok.SneakyThrows;
import org.example.datasource.DataSource;
import org.example.datasource.FileDataSource;
import org.example.datasource.MemoryDataSource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.MethodName.class)
class DataSourceTest {

    @SneakyThrows
    private String read(Path tempFile) {
        return Files.readString(tempFile);
    }

    @SneakyThrows
    private String read(DataSource ds) {
        String content;
        try (StringWriter writer = new StringWriter(); ds; Reader reader = ds.openReader()) {
            char[] buffer = new char[4096]; // Adjust buffer size as needed
            for (int read = reader.read(buffer); read != -1; read = reader.read(buffer)) {
                writer.write(buffer, 0, read);
            }
            content = writer.toString();
        }
        return content;
    }

    @SneakyThrows
    private void readTest(DataSource ds, String expected) {
        // test reading via input stream
        String fileReadContent;
        try (ds; BufferedReader br = new BufferedReader(new InputStreamReader(ds.openInputStream()))) {
            fileReadContent = br.lines().collect(Collectors.joining());
        }
        // Then
        assertThat(fileReadContent).isEqualTo(expected);
        // test reading via reader
        try (ds; BufferedReader br = new BufferedReader(ds.openReader())) {
            fileReadContent = br.lines().collect(Collectors.joining());
        }
        assertThat(fileReadContent).isEqualTo(expected);
    }

    private void writeAndReadTest(DataSource ds, String testContent) throws Exception {
        writeTest(ds, testContent, () -> read(ds));
        readTest(ds, testContent);
    }

    @SneakyThrows
    private void writeTest(DataSource ds, String testContent, Supplier<String> contentSupplier) {
        // test writing via output stream
        try (ds; OutputStream outputStream = ds.openOutputStream()) {
            outputStream.write(testContent.getBytes());
        }
        // Then
        assertThat(contentSupplier.get()).isEqualTo(testContent);
        // test writing via writer
        try (ds) {
            ds.openWriter().write(testContent);
        }

        // Then
        assertThat(contentSupplier.get()).isEqualTo(testContent);
    }

    @Test
    @SneakyThrows
    void dataSourceFileWritingTest() {
        // Given
        String fileContentAsString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam";
        Path tempFile = Files.createTempFile("ds-test-write", "txt");
        try (DataSource ds = new FileDataSource(tempFile)) {
            writeTest(ds, fileContentAsString, () -> read(tempFile));
        }
    }

    @Test
    @SneakyThrows
    void dataSourceFileReadingTest() {
        // Given
        String fileContentAsString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore etLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam";
        Path tempFile = Files.createTempFile("ds-test-read", "txt");
        Files.write(tempFile, fileContentAsString.getBytes());
        // When
        try (DataSource ds = new FileDataSource(tempFile)) {
            readTest(ds, fileContentAsString);
        }
    }

    @Test
    @SneakyThrows
    void dataSourceFileReadAndWriteTest() {
        // Given
        String fileContentAsString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod";
        Path tempFile = Files.createTempFile("ds-test-read-and-write", "txt");
        try (DataSource ds = new FileDataSource(tempFile)) {
            writeAndReadTest(ds, fileContentAsString);
        }
    }

    @Test
    @SneakyThrows
    void dataSourceMemoryWritingTest() {
        // Given
        String contentAsString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam";
        try (DataSource ds = new MemoryDataSource()) {
            writeTest(ds, contentAsString, () -> read(ds));
        }
    }

    @Test
    @SneakyThrows
    void dataSourceMemoryReadTest() {
        // Given
        String contentAsString = "";
        try (DataSource ds = new MemoryDataSource()) {
            readTest(ds, contentAsString);
        }
    }

    @Test
    @SneakyThrows
    void dataSourceMemoryWriteAndReadTest() {
        // Given
        String contentAsString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod";
        try (DataSource ds = new MemoryDataSource()) {
            writeAndReadTest(ds, contentAsString);
        }
    }
}
package org.example.datasource;

@FunctionalInterface
public interface ExceptionalSupplier<T> {
    T supply() throws Exception;
}

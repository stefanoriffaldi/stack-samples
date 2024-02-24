package org.example.datasource;

import java.io.*;

abstract class AbstractDataSource implements DataSource {
    private Closeable innerCloseable;

    @Override
    public void close() {
        if (innerCloseable == null) {
            return;
        }
        try {
            innerCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        innerCloseable = null;
    }

    @Override
    public Reader openReader() {
        return open(() -> new InputStreamReader(openInputStream()));
    }

    @Override
    public Writer openWriter() {
        return open(() -> new OutputStreamWriter(openOutputStream()));
    }

    protected <T extends Closeable> T open(ExceptionalSupplier<T> supplier) {
        if (innerCloseable != null) {
            close();
        }
        T closeable = null;
        try {
            closeable = supplier.supply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        innerCloseable = closeable;
        return closeable;
    }
}

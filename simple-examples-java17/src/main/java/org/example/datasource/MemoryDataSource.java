package org.example.datasource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class MemoryDataSource extends AbstractDataSource {
    private byte[] buffer = new byte[0];
    private ByteArrayOutputStream bos;

    @Override
    public void close() {
        super.close();
        if (bos != null) {
            buffer = bos.toByteArray();
            bos = null;
        }
    }

    @Override
    public InputStream openInputStream() {
        return open(() -> new ByteArrayInputStream(buffer));
    }

    @Override
    public OutputStream openOutputStream() {
        return bos = open(ByteArrayOutputStream::new);
    }
}

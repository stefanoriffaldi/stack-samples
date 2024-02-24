package org.example.datasource;

import java.io.*;

public interface DataSource extends Closeable {
    InputStream openInputStream();

    OutputStream openOutputStream();

    default Reader openReader() {
        return new InputStreamReader(openInputStream());
    }

    default Writer openWriter() {
        return new OutputStreamWriter(openOutputStream());
    }
}

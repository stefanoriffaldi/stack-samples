package org.example;

import lombok.SneakyThrows;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.CellType;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UpdateExcel {
    @SneakyThrows
    public static void main(String[] args) {
        try (
                InputStream is = Files.newInputStream(Paths.get("src/test/resources/book.xlsx"));
                ReadableWorkbook reader = new ReadableWorkbook(is);
                OutputStream os = Files.newOutputStream(Paths.get("target/output.xlsx"));
                Workbook writer = new Workbook(os, "MyApplication", "1.0")
        ) {
            reader.getSheets().forEach(sourceSheet -> updateSheet(sourceSheet, writer));
            writer.finish();
        }
    }

    @SneakyThrows
    private static void updateSheet(Sheet input, Workbook writer) {
        Worksheet output = writer.newWorksheet(input.getName());
        input.openStream().forEach(row -> row.forEach(cell -> updateCell(cell, output, row.getRowNum() - 1)));
    }

    private static void updateCell(Cell input, Worksheet output, int rowIndex) {
        // Uppercase first row values for each sheets
        if (input.getType() == CellType.STRING) {
            String string = input.asString();
            output.value(rowIndex, input.getColumnIndex(), rowIndex > 0 ? string.toUpperCase() : string);
        } else if (input.getType() == CellType.BOOLEAN) {
            output.value(rowIndex, input.getColumnIndex(), input.asBoolean());
        } else if (input.getType() == CellType.NUMBER) {
            output.value(rowIndex, input.getColumnIndex(), input.asNumber());
        } else if (input.getType() == CellType.FORMULA) {
            output.formula(rowIndex, input.getColumnIndex(), input.getFormula());
        } else if (input.getType() == CellType.ERROR) {
            output.value(rowIndex, input.getColumnIndex(), input.getRawValue());
        } else if (input.getType() == CellType.EMPTY) {
            // do nothing
            // output.value(rowIndex, input.getColumnIndex(), input.getRawValue());
        }
    }
}
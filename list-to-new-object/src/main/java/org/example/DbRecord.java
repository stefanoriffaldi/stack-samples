package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DbRecord {
    private int Id;
    private String name;
    private String descr;
    private String type;
    private String freq;
}

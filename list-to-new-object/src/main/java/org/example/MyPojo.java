package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPojo {
    private int id;
    private String name;
    private String desc;
    private List<String> type = new ArrayList<>();
    private List<String> frequency = new ArrayList<>();
}

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Map<NameDescKey, List<DbRecord>> collect = Stream.of(
                new DbRecord(1, "A", "desc ", "SMS", "daily"),
                new DbRecord(2, "A", "desc ", "Push", "weekly"),
                new DbRecord(3, "A", "desc ", "InAp", "custom"),
                new DbRecord(4, "B", "desc1 ", "SMS", "weekly"),
                new DbRecord(5, "B", "desc1 ", "Push", "daily")
        ).collect(
                Collectors.groupingBy(dbRecord -> new NameDescKey(dbRecord.getName(), dbRecord.getDescr()))
        );

        List<MyPojo> output = new ArrayList<>();
        for (List<DbRecord> dbRecordList : collect.values()) {
            MyPojo pojo = null;
            for (DbRecord record : dbRecordList) {
                if (pojo == null) {
                    pojo = new MyPojo();
                    pojo.setId(record.getId());
                    pojo.setName(record.getName());
                    pojo.setDesc(record.getDescr());
                }
                pojo.getFrequency().add(record.getFreq());
                pojo.getType().add(record.getType());
            }
            output.add(pojo);
        }

        for (MyPojo pojo : output) {
            System.out.println(pojo);
        }
    }
}
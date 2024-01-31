package org.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Question: <a href="https://stackoverflow.com/q/77906839/11289119">Can't return a void result</a>
 *
 * @author stefano-riffaldi
 */
@Slf4j
public class UdaService {

    private String udaReference(List<Uda> list, String udaCode) {
        return list.stream().filter(a -> a.getCode().equals(udaCode))
                .findFirst()
                .map(a -> a.getReferences().get(0).getCode())
                .orElse(null); // if findFirst() do not return a value, return null
    }

    public String udaReferenceSafe(List<Uda> list, String udaCode) {
        return list.stream().filter(a -> a.getCode().equals(udaCode))
                .findFirst()
                .filter(a -> a.getReferences() != null)
                .flatMap(a -> a.getReferences().stream().findFirst())
                .map(Uda::getCode)
                .orElse(null);
    }

    @Data
    public static class Uda {
        private final String code;
        private List<Uda> references;
    }
}

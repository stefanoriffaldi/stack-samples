package org.example.domain;

import lombok.Getter;

@Getter
public enum RuleMarkup {
    MANUAL("manual markup"),
    NO_REQUIRED("markup not required");

    private final String rule;

    RuleMarkup(String rule) {
        this.rule = rule;
    }
}
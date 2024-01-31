package org.example.domain.converters;

import org.example.domain.RuleMarkup;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class RuleMarkupConverter implements AttributeConverter<RuleMarkup, String> {
    @Override
    public String convertToDatabaseColumn(RuleMarkup attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getRule();
    }

    @Override
    public RuleMarkup convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(RuleMarkup.values())
                .filter(ruleMarkup -> ruleMarkup.getRule().equals(dbData))
                .findAny().orElseThrow(() -> new IllegalArgumentException(dbData));
    }

}
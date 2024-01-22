package org.example.domain;

import lombok.*;
import org.example.domain.conerters.RuleMarkupConverter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type")
    private Long subjectType;

    @Column(name = "markup")
    @Convert(converter = RuleMarkupConverter.class)
    private RuleMarkup nonRuleMarkup = RuleMarkup.MANUAL;
}


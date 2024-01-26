package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.example.SpringBootTestClass;
import org.example.domain.Document;
import org.example.domain.RuleMarkup;
import org.example.repository.DocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
@DataJpaTest
@Slf4j
public class ConverterTest {
    @Autowired
    private DocumentRepository repository;

    @Test
    public void convertToEntityAttributeTest() {
        List<Document> documents = repository.nativeGetByMarkup("manual markup");
        documents.forEach(document -> log.info(document.toString()));

        assertThat(documents).hasSize(2)
                .extracting("subjectType")
                .containsExactlyInAnyOrder(3L, 4L);
    }

    @Test
    public void convertToDatabaseColumnTest() {
        Document entity = new Document();
        entity.setSubjectType(6L);
        entity.setNonRuleMarkup(RuleMarkup.NO_REQUIRED);
        repository.save(entity);

        List<Document> documents = repository.nativeGetByMarkup(RuleMarkup.NO_REQUIRED.getRule());
        documents.forEach(document -> log.info(document.toString()));
        assertThat(documents).hasSize(2)
                .extracting("subjectType")
                .containsExactlyInAnyOrder(5L, 6L);

    }

}


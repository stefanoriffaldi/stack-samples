package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.example.SpringBootTestClass;
import org.example.domain.Document;
import org.example.domain.RuleMarkup;
import org.example.service.DocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
//@DataJpaTest
@Slf4j
public class DocumentServiceTest {

    @Autowired
    private DocumentService service;

    @Test
    public void getByMarkup() {
        assertThat(service.getByMarkup("ciao"))
                .isNotNull()
                .isEmpty();

        assertThat(service.getByMarkup("manual markup"))
                .isNotNull()
                .hasSize(2).asList()
                .containsExactlyInAnyOrder(
                        new Document(1L, 3L, RuleMarkup.MANUAL),
                        new Document(2L, 4L, RuleMarkup.MANUAL)
                );
    }
}
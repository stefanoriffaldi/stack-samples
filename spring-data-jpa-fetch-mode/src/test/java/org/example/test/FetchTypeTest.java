package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Condition;
import org.example.SpringBootTestClass;
import org.example.domain.University;
import org.example.repository.UniversityRepository;
import org.example.test.tools.LogExtractor;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
@DataJpaTest
@Slf4j
public class FetchTypeTest {
    @Autowired
    private UniversityRepository repository;
    private LogExtractor logExtractor = null;

    @Test
    public void fetchModeJoinTest() {
        logExtractor = new LogExtractor("org.hibernate.SQL");
        List<University> universities = repository.findAll(new PageRequest(0, 100, new Sort(Sort.Direction.ASC, "name"))).getContent();

        assertThat(universities)
                .hasSize(2)
                .extracting(University::getName)
                .containsExactly(
                        "Harvard University",
                        "MIT - Massachusetts Institute of Technology"
                )
        ;

        Iterable<Condition<? super String>> keywords = Stream.of("select", "from", "join", "order by")
                .map(keyword -> new Condition<String>(o -> o.contains(keyword), keyword))
                .collect(Collectors.toList());

        assertThat(logExtractor.getFormattedMessages().collect(Collectors.toList()))
                .as("Check query count, expected 1 and only 1")
                .hasSize(1)
                .first()
                .has(allOf(keywords))
        ;
    }

    @After
    public void tearDown() throws Exception {
        if (logExtractor != null) {
            logExtractor.detach();
            logExtractor = null;
        }
    }

}


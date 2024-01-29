package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.example.SpringBootTestClass;
import org.example.domain.University;
import org.example.repository.UniversityRepository;
import org.example.tools.LogExtractor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.junit.After;
import org.junit.Before;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
@DataJpaTest
@Slf4j
public class FetchModeTest {
    private LogExtractor logExtractor;
    @Autowired
    private UniversityRepository repository;

    @Test
    public void fetchModeManualViaQuery() {
        LogExtractor queryTranslator = new LogExtractor(QueryTranslatorImpl.class);
        List<University> universities = repository.pageAll(new PageRequest(0, 100, new Sort(Sort.Direction.ASC, "name"))).getContent();
        assertThat(universities)
                .hasSize(2)
                .extracting(University::getName)
                .containsExactly(
                        "Harvard University",
                        "MIT - Massachusetts Institute of Technology"
                )
        ;

        // Expectation is that FetchMode.JOIN doesn't work
        assertThat(logExtractor.getFormattedMessages().collect(Collectors.toList()))
                .as("Check query count, expected 1")
                .hasSize(1)
                .has(allOf("select", "from university", "left outer join university_students", "left outer join student", "order by", "asc"), atIndex(0))
        ;

        assertThat(queryTranslator.getFormattedMessages().collect(Collectors.toList()))
                .as("Check warning 'HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!'")
                .hasSize(1)
                .first()
                .isEqualTo("HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!")
        ;
        // spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch property seems not working
        queryTranslator.clear();
        queryTranslator.detach();
    }

    @Test
    public void fetchModeOnFindAllPaging() {
        List<University> universities = repository.findAll(new PageRequest(0, 100, new Sort(Sort.Direction.ASC, "name"))).getContent();

        assertThat(universities)
                .hasSize(2)
                .extracting(University::getName)
                .containsExactly(
                        "Harvard University",
                        "MIT - Massachusetts Institute of Technology"
                )
        ;

        // Expectation is that FetchMode.JOIN doesn't work
        assertThat(logExtractor.getFormattedMessages().collect(Collectors.toList()))
                .as("Check query count, expected 3")
                .hasSize(3)
                .has(allOf("select", "from university", "order by"), atIndex(0)) // first: query on university
                .has(allOf("select", "from university_students", "inner join student"), atIndex(1)) // 2nd: query on Student with join table
                .has(allOf("select", "from university_students", "inner join student"), atIndex(2)) // 3rd: query on Student with join table
        ;
    }

    @Test
    public void fetchModeOnFindOne() {
        University university = repository.findOne("20cdd371-2803-4ad6-8c5f-7189d00461e9");
        assertThat(university).isNotNull()
                .hasFieldOrPropertyWithValue("name", "MIT - Massachusetts Institute of Technology")
                .hasFieldOrPropertyWithValue("address", "Massachusetts")
        ;
        // Expectation is, in that scenario, FetchMode.JOIN works
        assertThat(logExtractor.getFormattedMessages().collect(Collectors.toList()))
                .as("Check query count, expected 1 and only 1")
                .hasSize(1)
                .has(allOf("select", "from university", "left outer join university_students", "left outer join student"), atIndex(0))
        ;
    }

    @Before
    public void setUp() {
        logExtractor = new LogExtractor("org.hibernate.SQL");
    }

    @After
    public void tearDown() {
        if (logExtractor != null) {
            logExtractor.detach();
            logExtractor = null;
        }
    }

    private static Condition<? super String> allOf(String... keywords) {
        return Assertions.allOf(Stream.of(keywords)
                .map(keyword -> new Condition<String>(o -> o.contains(keyword), keyword))
                .collect(Collectors.toList()));
    }
}


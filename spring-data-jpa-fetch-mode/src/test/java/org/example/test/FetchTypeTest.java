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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .extracting("name")
                .containsExactly(
                        "Harvard University",
                        "MIT - Massachusetts Institute of Technology"
                )
        ;

        assertThat(logExtractor.getFormattedMessages().collect(Collectors.toList()))
                .hasSize(1)
                .first()
                .has(containsEach("select", "from", "join", "order by"))
        ;
    }

    @After
    public void tearDown() throws Exception {
        if (logExtractor != null) {
            logExtractor.detach();
            logExtractor = null;
        }
    }

    private Condition<? super String> containsEach(String... strings) {
        return new Condition<String>("Must contains each of " + Arrays.toString(strings)) {
            @Override
            public boolean matches(String content) {
                String lowerCase = content.toLowerCase();
                for (String string : strings) {
                    if (!lowerCase.contains(string.toLowerCase())) {
                        return false;
                    }
                }
                return true;
            }
        };
    }
}


package org.example.test;
// Generated by CodiumAI

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.example.SpringBootTestClass;
import org.example.domain.Student;
import org.example.service.JdbcTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
@DataJpaTest
@Slf4j
public class JdbcTemplateServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Returns a list of students matching the given full name
    @Test
    public void getStudentsTest() {
        // Given
        JdbcTemplateService service = new JdbcTemplateService(jdbcTemplate, null);
        String fullName = "Luke-Hobbs";

        // When
        List<Student> result = service.getStudents(fullName);

        // Then
        // Add assertions here
        assertThat(result).isNotNull().hasSize(1)
                .extracting("firstName", "lastName")
                .containsExactly(
                        Tuple.tuple("Luke", "Hobbs"
                        )
                )
        ;
    }

    @Test
    public void getStudentsWithNamedParameterTest() {
        // Given
        JdbcTemplateService service = new JdbcTemplateService(null, namedParameterJdbcTemplate);
        String fullName = "Luke-Hobbs";

        // When
        List<Student> result = service.getStudentsWithNamedParameter(fullName);

        // Then
        // Add assertions here
        assertThat(result).isNotNull().hasSize(1)
                .extracting("firstName", "lastName")
                .containsExactly(
                        Tuple.tuple("Luke", "Hobbs"
                        )
                )
        ;
    }

    @Test
    public void getStudentsWithQuestionMarkNotationTest() {
        // Given
        JdbcTemplateService service = new JdbcTemplateService(jdbcTemplate, null);
        String fullName = "Luke-Hobbs";

        // When
        List<Student> result = service.getStudentsWithQuestionMarkNotation(fullName);

        // Then
        // Add assertions here
        assertThat(result).isNotNull().hasSize(1)
                .extracting("firstName", "lastName")
                .containsExactly(
                        Tuple.tuple("Luke", "Hobbs"
                        )
                )
        ;
    }

}
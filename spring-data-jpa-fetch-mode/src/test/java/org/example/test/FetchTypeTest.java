package org.example.test;

import lombok.extern.log4j.Log4j2;
import org.example.SpringBootTestClass;
import org.example.domain.Student;
import org.example.domain.University;
import org.example.repository.UniversityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestClass.class)
@DataJpaTest
@Log4j2
public class FetchTypeTest {
    @Autowired
    private UniversityRepository repository;

    @Test
    public void findAll() {
        List<University> universityList = repository.findAll(new PageRequest(0, 100)).getContent();
        for (University university : universityList) {
            log.info("{} [{}]", university.getName(), university.getAddress());
            for (Student student : university.getStudents()) {
                log.info("{}, {}", student.getLastName(), student.getFirstName());
            }
        }
    }
}

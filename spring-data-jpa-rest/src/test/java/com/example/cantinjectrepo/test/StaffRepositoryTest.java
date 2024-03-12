package com.example.cantinjectrepo.test;

import com.example.cantinjectrepo.JpaTestConfig;
import com.example.cantinjectrepo.repo.StaffRepository;
import com.example.cantinjectrepo.service.StaffService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfig.class)
public class StaffRepositoryTest {
	@Autowired
	private StaffRepository repository;
	@Autowired(required = false)
	private StaffService service;

	@Test
	void contextLoads() {
		Assertions.assertThat(repository).isNotNull();
		Assertions.assertThat(service).isNull();

		repository.findAll(PageRequest.of(0, 10)).forEach(System.out::println);
	}
}

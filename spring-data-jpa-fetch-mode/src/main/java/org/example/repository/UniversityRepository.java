package org.example.repository;

import org.example.domain.University;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, String> {

    @Query(value = "SELECT u FROM University u LEFT JOIN FETCH u.students s", countQuery = "SELECT u FROM University u")
    <S extends University> Page<S> pageAll(Pageable pageable);

    @Query(value = "SELECT u FROM University u LEFT JOIN FETCH u.students s", countQuery = "SELECT u FROM University u")
    <S extends University> List<S>  findAllFetching(Example<S> pageable);
//    ^^ seems not working, throws InvalidDataAccessApiUsageException: Parameter with that position [1] did not exist; nested exception is java.lang.IllegalArgumentException: Parameter with that position [1] did not exist

//    @Query(value = "SELECT u FROM University u LEFT JOIN FETCH u.students s order by u.name", countQuery = "SELECT u FROM University u")
//    @Override
//    Page<University> findAll(Pageable var1);
//    ^^ overriding PagingAndSortingRepository.findAll(Pageable) seems work ;) but still show HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
}

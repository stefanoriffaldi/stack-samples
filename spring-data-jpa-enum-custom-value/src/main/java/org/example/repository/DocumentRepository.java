package org.example.repository;

import org.example.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {

    @Query(value = "select * from Document where markup = ?1", nativeQuery = true)
    List<Document> nativeGetByMarkup(String rule);

}

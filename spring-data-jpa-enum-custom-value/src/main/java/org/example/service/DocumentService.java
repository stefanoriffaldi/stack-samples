package org.example.service;

import org.example.domain.Document;
import org.example.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public List<Document> getByMarkup(String rule) {
        return repository.nativeGetByMarkup(rule);
    }
}

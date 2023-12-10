package com.example.pc_composer.repository;

import com.example.pc_composer.model.Case;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CaseRepository extends MongoRepository<Case, String> {
}

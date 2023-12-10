package com.example.pc_composer.repository;

import com.example.pc_composer.model.Memory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemoryRepository extends MongoRepository<Memory, String> {
}

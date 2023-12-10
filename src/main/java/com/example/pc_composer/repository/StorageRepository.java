package com.example.pc_composer.repository;

import com.example.pc_composer.model.Storage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorageRepository extends MongoRepository<Storage, String> {
}

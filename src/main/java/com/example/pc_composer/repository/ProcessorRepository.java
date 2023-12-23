package com.example.pc_composer.repository;

import com.example.pc_composer.model.pc_parts.Processor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessorRepository extends MongoRepository<Processor, String> {
}

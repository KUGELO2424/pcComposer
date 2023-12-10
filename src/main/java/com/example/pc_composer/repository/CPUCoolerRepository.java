package com.example.pc_composer.repository;

import com.example.pc_composer.model.CPUCooler;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CPUCoolerRepository extends MongoRepository<CPUCooler, String> {
}

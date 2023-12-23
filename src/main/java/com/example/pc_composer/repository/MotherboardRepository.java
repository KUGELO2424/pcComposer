package com.example.pc_composer.repository;

import com.example.pc_composer.model.pc_parts.Motherboard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MotherboardRepository extends MongoRepository<Motherboard, String> {
}

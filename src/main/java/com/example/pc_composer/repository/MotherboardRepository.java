package com.example.pc_composer.repository;

import com.example.pc_composer.model.Motherboard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MotherboardRepository extends MongoRepository<Motherboard, String> {
}

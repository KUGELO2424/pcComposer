package com.example.pc_composer.repository;

import com.example.pc_composer.model.PowerSupply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PowerSupplyRepository extends MongoRepository<PowerSupply, String> {
}

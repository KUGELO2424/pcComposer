package com.example.pc_composer.repository;

import com.example.pc_composer.model.GraphicsCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraphicsCardRepository extends MongoRepository<GraphicsCard, String> {
}

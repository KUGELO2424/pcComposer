package com.example.pc_composer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "builds")
public class ComputerSet {

    @Id
    private String id;
    private double fullPrice;
    private Case computerCase;
    private Motherboard motherboard;
    private PowerSupply powerSupply;
    private Processor cpu;
}

package com.example.pc_composer.model.pc_parts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ram")
public class Memory {

    @Id
    private String id;
    private String name;
    private String manufacturer; // Corsair, G.SKILL
    private int capacity; // GB
    private String type; // DDR4, DDR3
    private int speed; // MHz
    private String formFactor; // DIMM, SO-DIMM
    private double price;
}

package com.example.pc_composer.model.pc_parts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "powerSupplies")
public class PowerSupply {

    @Id
    private String id;
    private String name;
    private int wattage; // Power in watts
    private boolean modular;
    private String efficiencyRating; // (80 PLUS, 80 PLUS Bronze, itp.)
    private double price;

}

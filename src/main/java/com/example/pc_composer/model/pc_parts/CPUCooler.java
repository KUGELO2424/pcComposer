package com.example.pc_composer.model.pc_parts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cpuCoolers")
public class CPUCooler {

    @Id
    private String id;
    private String name;
    private String socketCompatibility;
    private int fanSize; // mm
    private boolean liquidCooling;
    private double price;
}

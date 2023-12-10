package com.example.pc_composer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cases")
public class Case {

    @Id
    private String id;
    private String name;
    private String supportedMotherboardFormFactor; // ATX, MicroATX, Mini-ITX, etc.
    private int maxGPUSize; // max length of gpu in mm
    private boolean hasRGB;
    private double price;

}

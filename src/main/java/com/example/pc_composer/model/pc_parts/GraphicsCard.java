package com.example.pc_composer.model.pc_parts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "graphicsCards")
public class GraphicsCard {

    @Id
    private String id;
    private String name;
    private String chipset;
    private String manufacturer;
    private int vram;
    private int powerConsumption; // in watts
    private int length; // mm
    private double price;
}

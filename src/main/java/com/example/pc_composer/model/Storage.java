package com.example.pc_composer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "storage")
public class Storage {

    @Id
    private String id;
    private String name;
    private String manufacturer; // Samsung, Seagate
    private int capacity; // GB
    private String type; // HDD, SSD
    private int rotationSpeed; // Rotation speed for HDDs in revolutions per minute (RPM)
    private String connectionType; // SATA, m2
    private double price;
}

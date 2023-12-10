package com.example.pc_composer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "processors")
public class Processor {

    @Id
    private String id;
    private String name;
    private String manufacturer; // eg. Intel, AMD
    private String socket;
    private int cores;
    private int threads;
    private double baseClock; // GHz
    private double maxClock; // GHz
    private double price;
}

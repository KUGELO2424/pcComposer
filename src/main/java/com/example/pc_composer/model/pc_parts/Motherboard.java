package com.example.pc_composer.model.pc_parts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "motherboards")
public class Motherboard {

    @Id
    private String id;
    private String name;
    private String chipset;
    private String socket;
    private int maxMemory; // in GB
    private int pciExpressX16Slots;
    private int pciExpressX8Slots;
    private int pciExpressX4Slots;
    private int pciExpressX1Slots;
    private String formFactor; // ATX, MicroATX, Mini-ITX, etc.
    private int sataPorts; // Number of SATA ports
    private int m2Slots; // Number of M.2 slots
    private double price;

}

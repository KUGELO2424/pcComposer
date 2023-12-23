package com.example.pc_composer.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ComputerSetFilters {

    private String motherboardFormFactor;
    private String cpuManufacturer;
    private String cpuModel;
    private Integer cpuCores;
    private double minPrice;
    private double maxPrice;
    private String sortDirection;
    private long page;
    private long pageSize;

}

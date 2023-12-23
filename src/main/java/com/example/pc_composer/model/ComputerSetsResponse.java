package com.example.pc_composer.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ComputerSetsResponse {

    private int size;
    private List<ComputerSet> sets;

}

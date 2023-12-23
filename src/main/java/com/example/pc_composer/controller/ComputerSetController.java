package com.example.pc_composer.controller;

import com.example.pc_composer.model.ComputerSet;
import com.example.pc_composer.model.ComputerSetFilters;
import com.example.pc_composer.model.ComputerSetsResponse;
import com.example.pc_composer.service.ComputerSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ComputerSetController {

    private final ComputerSetService computerSetService;

    @GetMapping("/sets")
    public ResponseEntity<ComputerSetsResponse> getCompatibleComputerSets(@RequestParam(value = "motherboardFormFactor", required = false) String motherboardFormFactor,
                                                                       @RequestParam(value = "cpuManufacturer", required = false) String cpuManufacturer,
                                                                       @RequestParam(value = "cpuModel", required = false) String cpuModel,
                                                                       @RequestParam(value = "cpuCores", required = false) Integer cpuCores,
                                                                       @RequestParam(value = "minPrice", required = false, defaultValue = "0") double minPrice,
                                                                       @RequestParam(value = "maxPrice", required = false, defaultValue = "99999999999") double maxPrice,
                                                                       @RequestParam(value = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "0") long page,
                                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") long size) {
        ComputerSetFilters filters = ComputerSetFilters.builder()
                .motherboardFormFactor(motherboardFormFactor)
                .cpuManufacturer(cpuManufacturer)
                .cpuModel(cpuModel)
                .cpuCores(cpuCores)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .sortDirection(sortDirection)
                .page(page)
                .pageSize(size)
                .build();
        List<ComputerSet> computerSets = computerSetService.getCompatibleComputerSets(filters);
        ComputerSetsResponse response = ComputerSetsResponse.builder()
                .sets(computerSets)
                .size(computerSets.size())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

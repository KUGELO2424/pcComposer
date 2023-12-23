package com.example.pc_composer.service;

import com.example.pc_composer.model.ComputerSet;
import com.example.pc_composer.model.ComputerSetFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerSetService {

    private final MongoTemplate mongoTemplate;

    public List<ComputerSet> getCompatibleComputerSets(ComputerSetFilters filters) {

        Aggregation aggregation = new AggregationBuilder()
                .moveRootObjectToChild("motherboard")
                // AGGREGATE TABLES
                .addLookupOperation("cases", "motherboard.formFactor", "supportedMotherboardFormFactor", "compatibleCases")
                .addLookupOperation("processors", "motherboard.socket", "socket", "compatibleProcessors")
                .addLookupOperationWithoutCondition("powerSupplies",  "powerSupplies")
                .addUnwindOperationFor("compatibleCases")
                .addUnwindOperationFor("compatibleProcessors")
                .addUnwindOperationFor("powerSupplies")
                // PROJECTION
                .addProjectOperation()
                // ADDITIONAL FIELDS
                .addPriceField()
                // FILTERING
                .addExactMatchOperation("motherboard.formFactor", filters.getMotherboardFormFactor())
                .addExactMatchOperation("cpu.manufacturer", filters.getCpuManufacturer())
                .addPartlyMatchOperation("cpu.name", filters.getCpuModel())
                .addObjectMatchOperation("cpu.cores", filters.getCpuCores())
                .addMatchOperationValueBetween("fullPrice", filters.getMinPrice(), filters.getMaxPrice())
                // PAGINATION
                .sortBy("fullPrice", filters.getSortDirection())
                .addSkipAndLimitOperation(filters.getPage(), filters.getPageSize())
                .build();


        AggregationResults<ComputerSet> result = mongoTemplate.aggregate(aggregation, "motherboards", ComputerSet.class);
        return result.getMappedResults();
    }

    public List<ComputerSet> createCompatibleComputerSets(String collectionName) {

        Aggregation aggregation = new AggregationBuilder()
                .moveRootObjectToChild("motherboard")
                // AGGREGATE TABLES
                .addLookupOperation("cases", "motherboard.formFactor", "supportedMotherboardFormFactor", "compatibleCases")
                .addLookupOperation("processors", "motherboard.socket", "socket", "compatibleProcessors")
                .addLookupOperationWithoutCondition("powerSupplies",  "powerSupplies")
                .addUnwindOperationFor("compatibleCases")
                .addUnwindOperationFor("compatibleProcessors")
                .addUnwindOperationFor("powerSupplies")
                // PROJECTION
                .addProjectOperation()
                // ADDITIONAL FIELDS
                .addPriceField()
                .saveToCollection(collectionName)
                .build();

        AggregationResults<ComputerSet> result = mongoTemplate.aggregate(aggregation, "motherboards", ComputerSet.class);
        return result.getMappedResults();
    }
}

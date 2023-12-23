package com.example.pc_composer.service;

import com.example.pc_composer.model.ComputerSet;
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

    public List<ComputerSet> getCompatibleComputerSets(String motherboardFormFactor, String cpuManufacturer, String cpuModel,
                                                       Integer cpuCores,
                                                       double minPrice, double maxPrice,
                                                       String sortDirection,
                                                       long page, long size) {

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
                .addExactMatchOperation("motherboard.formFactor", motherboardFormFactor)
                .addExactMatchOperation("cpu.manufacturer", cpuManufacturer)
                .addPartlyMatchOperation("cpu.name", cpuModel)
                .addObjectMatchOperation("cpu.cores", cpuCores)
                .addMatchOperationValueBetween("fullPrice", minPrice, maxPrice)
                // PAGINATION
                .sortBy("fullPrice", sortDirection)
                .addSkipAndLimitOperation(page, size)
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

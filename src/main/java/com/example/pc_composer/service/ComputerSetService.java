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

    public List<ComputerSet> getCompatibleComputerSets(String motherboardFormFactor,
                                                       double minPrice, double maxPrice,
                                                       String sortDirection,
                                                       long page, long size) {

        Aggregation aggregation = new AggregationBuilder()
                // AGGREGATE TABLES
                .addLookupOperation("cases", "formFactor", "supportedMotherboardFormFactor", "compatibleCases")
                .addLookupOperationWithoutCondition("powerSupplies",  "powerSupplies")
                .addUnwindOperationFor("compatibleCases")
                .addUnwindOperationFor("powerSupplies")
                // PROJECTION
                .addProjectOperation()
                // ADDITIONAL FIELDS
                .addPriceField()
                // FILTERING
                .addMatchOperation("motherboard.formFactor", motherboardFormFactor)
                .addMatchOperationValueBetween("fullPrice", minPrice, maxPrice)
                // PAGINATION
                .sortBy("fullPrice", sortDirection)
                .addSkipAndLimitOperation(page, size)
//                .saveToCollection("builds")
                .build();


        AggregationResults<ComputerSet> result = mongoTemplate.aggregate(aggregation, "motherboards", ComputerSet.class);
        return result.getMappedResults();
    }
}

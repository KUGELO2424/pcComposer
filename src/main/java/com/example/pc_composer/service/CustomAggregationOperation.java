package com.example.pc_composer.service;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class CustomAggregationOperation implements AggregationOperation {

    private final String jsonOperation;

    public CustomAggregationOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public org.bson.Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(org.bson.Document.parse(jsonOperation));
    }
}

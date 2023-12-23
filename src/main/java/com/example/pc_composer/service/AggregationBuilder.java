package com.example.pc_composer.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.CountOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AggregationBuilder {
    private final List<AggregationOperation> aggregationOperations = new ArrayList<>();

    public AggregationBuilder  addLookupOperation(String from, String localField, String foreignField, String as) {
        this.aggregationOperations.add(
                Aggregation.lookup(from, localField, foreignField, as)
        );
        return this;
    }

    public AggregationBuilder addLookupOperationWithoutCondition(String from, String as) {
        String lookUpForQuery = String.format("""
                {$lookup: {
                    from: "%s",
                    let: {},
                    pipeline: [],
                    as: "%s"
                }}""", from, as);
        this.aggregationOperations.add(
                new CustomAggregationOperation(lookUpForQuery)
        );
        return this;
    }

    public AggregationBuilder saveToCollection(String collectionName) {
        String lookUpForQuery = String.format("{$out: '%s'}", collectionName);
        this.aggregationOperations.add(
                new CustomAggregationOperation(lookUpForQuery)
        );
        return this;
    }

    public AggregationBuilder addUnwindOperationFor(String field) {
        this.aggregationOperations.add(Aggregation.unwind(field));
        return this;
    }

    public AggregationBuilder addProjectOperation() {

        this.aggregationOperations.add(
                Aggregation.project()
                        .andExclude("_id")
                        .and("motherboard").as("motherboard")
                        .and("compatibleCases").as("computerCase")
                        .and("compatibleProcessors").as("cpu")
                        .and("powerSupplies").as("powerSupply")
        );
        return this;
    }

    public AggregationBuilder moveRootObjectToChild(String name) {
        this.aggregationOperations.add(
                Aggregation.project()
                        .and("$$ROOT").as(name)
        );
        return this;
    }

    public AggregationBuilder addPriceField() {
        this.aggregationOperations.add(
                Aggregation.addFields()
                        .addField("fullPrice")
                        .withValueOf(
                                AccumulatorOperators.Sum
                                        .sumOf("computerCase.price")
                                        .and("motherboard.price")
                                        .and("powerSupply.price")
                        )
                        .build()
        );
        return this;
    }

    public AggregationBuilder addObjectMatchOperation(String field, Object value) {
        if (value != null) {
            this.aggregationOperations.add(
                    Aggregation.match(Criteria.where(field).is(value))
            );
        }
        return this;
    }

    public AggregationBuilder addExactMatchOperation(String field, String match) {
        if (StringUtils.hasText(match)) {
            this.aggregationOperations.add(
                    Aggregation.match(Criteria.where(field).regex("^" + match + "$", "i"))
            );
        }
        return this;
    }

    public AggregationBuilder addPartlyMatchOperation(String field, String match) {
        if (StringUtils.hasText(match)) {
            this.aggregationOperations.add(
                    Aggregation.match(Criteria.where(field).regex(match, "i"))
            );
        }
        return this;
    }

    public AggregationBuilder addMatchOperationValueBetween(String field, double min, double max) {
        this.aggregationOperations.add(
                Aggregation.match(Criteria.where(field).gte(min).lte(max))
        );
        return this;
    }

    public AggregationBuilder addSkipAndLimitOperation(long page, long size) {
        this.aggregationOperations.add(
                new SkipOperation(page * size)  // Skip documents based on page
        );
        this.aggregationOperations.add(
                new LimitOperation(size)
        );
        return this;
    }

    public AggregationBuilder sortBy(String field, String direction) {
        aggregationOperations.add(Aggregation.sort(Sort.Direction.fromString(direction), field));
        return this;
    }

    public Aggregation build() {
        return Aggregation.newAggregation(aggregationOperations);
    }
}

package com.example.pc_composer.service;

import com.example.pc_composer.model.BulkImportResponse;
import com.example.pc_composer.model.CPUCooler;
import com.example.pc_composer.model.Case;
import com.example.pc_composer.model.GraphicsCard;
import com.example.pc_composer.model.Memory;
import com.example.pc_composer.model.Motherboard;
import com.example.pc_composer.model.PowerSupply;
import com.example.pc_composer.model.Processor;
import com.example.pc_composer.model.Storage;
import com.example.pc_composer.utils.MultipartFileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.bulk.BulkWriteResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataImportService {

    private final MongoTemplate mongoTemplate;

    public DataImportService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public BulkImportResponse importTo(MultipartFile file) throws IOException {
        List<String> lines = MultipartFileUtils.lines(file);
        Class<?> type = getClassFromFilename(file.getOriginalFilename());
        List<Document> mongoDocs = generateMongoDocs(lines, type);
        String collection = type.getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class).collection();

        BulkWriteResult result = insertInto(collection, mongoDocs);
        return BulkImportResponse.builder()
                .filename(file.getOriginalFilename())
                .rows(lines.size())
                .matched(result.getMatchedCount())
                .inserted(result.getUpserts().size())
                .updated(result.getModifiedCount())
                .build();
    }

    private <T> List<Document> generateMongoDocs(List<String> lines, Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<Document> docs = new ArrayList<>();
        for (String json : lines) {
            if (type != null) {
                mapper.readValue(json, type);
            }
            docs.add(Document.parse(json));
        }
        return docs;
    }

    private BulkWriteResult insertInto(String collection, List<Document> mongoDocs) {
        Instant start = Instant.now();
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collection);
        for (Document document : mongoDocs) {
            Query query = new Query(Criteria.where("id").is(document.get("id")));
            Document updateDoc = new Document();
            updateDoc.append("$set", document);
            Update update = Update.fromDocument(updateDoc);
            bulkOps.upsert(query, update);
        }

        BulkWriteResult bulkWriteResult = bulkOps.execute();
        log.info("Bulk upsert of {}. Rows: {}, Matched: {}, Inserted: {}. Updated: {}. Completed in {} milliseconds",
                collection,
                mongoDocs.size(),
                bulkWriteResult.getMatchedCount(),
                bulkWriteResult.getUpserts().size(),
                bulkWriteResult.getModifiedCount(),
                Duration.between(start, Instant.now()).toMillis());
        return bulkWriteResult;
    }

    private List<String> lines(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().toList();
    }

    private Class<?> getClassFromFilename(String filename) {
        return switch (filename) {
            case "cases" -> Case.class;
            case "motherboards" -> Motherboard.class;
            case "cpucoolers" -> CPUCooler.class;
            case "graphics" -> GraphicsCard.class;
            case "memories" -> Memory.class;
            case "powersupplies" -> PowerSupply.class;
            case "processors" -> Processor.class;
            case "storages" -> Storage.class;
            default -> throw new IllegalArgumentException(String.format("Class not found for name %s", filename));
        };
    }
}
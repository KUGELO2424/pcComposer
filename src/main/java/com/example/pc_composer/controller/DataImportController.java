package com.example.pc_composer.controller;

import com.example.pc_composer.model.BulkImportResponse;
import com.example.pc_composer.service.DataImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/import")
public class DataImportController {

    private final DataImportService dataImportService;

    public DataImportController(DataImportService dataImportService) {
        this.dataImportService = dataImportService;
    }

    @PostMapping()
    public ResponseEntity<List<BulkImportResponse>> importDataFromCSV(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<BulkImportResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            BulkImportResponse result = dataImportService.importTo(file);
            responses.add(result);
        }

        return ResponseEntity.ok(responses);
    }
}

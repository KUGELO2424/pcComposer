package com.example.pc_composer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulkImportResponse {

    private String filename;
    private int rows;
    private int matched;
    private int inserted;
    private int updated;

}

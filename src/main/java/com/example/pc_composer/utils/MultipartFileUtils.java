package com.example.pc_composer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MultipartFileUtils {

    private MultipartFileUtils() {}

    public static List<String> lines(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().toList();
    }

}

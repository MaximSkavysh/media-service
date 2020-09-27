package com.lobster.ecommerce.media.controller;

import com.lobster.ecommerce.media.dto.ResponseDto;
import com.lobster.ecommerce.media.service.FilesStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class FilesController {
    private final FilesStorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                storageService.save(file);
                fileNames.add(file.getOriginalFilename());
            });
            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(message));
        }
    }
}

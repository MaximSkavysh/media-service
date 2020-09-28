package com.lobster.ecommerce.media.controller;

import com.lobster.ecommerce.media.dto.RequestDto;
import com.lobster.ecommerce.media.dto.ResponseDto;
import com.lobster.ecommerce.media.service.FilesStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
@Validated
public class FilesController {
    private final FilesStorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFiles(@Valid @ModelAttribute RequestDto requestDto) {
        String message = "";

        try {
            storageService.save(requestDto);
            message = "Uploaded the files successfully!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(message));
        }
    }
}

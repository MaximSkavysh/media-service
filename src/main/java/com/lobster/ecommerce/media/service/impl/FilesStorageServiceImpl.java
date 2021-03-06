package com.lobster.ecommerce.media.service.impl;

import com.lobster.ecommerce.media.dto.RequestDto;
import com.lobster.ecommerce.media.exception.FileNotFoundException;
import com.lobster.ecommerce.media.exception.StorageException;
import com.lobster.ecommerce.media.model.FileDb;
import com.lobster.ecommerce.media.repository.FileDbRepository;
import com.lobster.ecommerce.media.service.FilesStorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path rootLocation = Paths.get("uploads");

    private final FileDbRepository fileDbRepository;

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public void save(RequestDto requestDto) {
        MultipartFile[] files = requestDto.getFiles();
        Arrays.asList(files).stream().forEach(file -> {
            if (file != null && file.getOriginalFilename() != null) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                try {
                    if (file.isEmpty()) {
                        throw new StorageException(
                                "Failed to store empty file " + filename);
                    }
                    if (filename.contains("..")) {
                        // This is a security check
                        throw new StorageException(
                                "Cannot store file with relative path outside current directory "
                                        + filename);
                    }
                    try (InputStream inputStream = file.getInputStream()) {
                        Path url = this.rootLocation.resolve(filename);
                        FileDb fileDb = new FileDb(requestDto.getProduct_id(), filename, file.getContentType(), url.toString());
                        fileDbRepository.save(fileDb);
                        Files.copy(inputStream, url,
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new StorageException(
                            "Failed to store file " + filename, e);
                }
            }
        });

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(
                    "Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
}

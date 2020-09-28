package com.lobster.ecommerce.media.service;

import com.lobster.ecommerce.media.dto.RequestDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    public void init();

    public void save(RequestDto requestDtos);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}

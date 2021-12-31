package com.enigmacamp.evening.service;

import com.enigmacamp.evening.payload.response.FileResponse;
import com.enigmacamp.evening.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResponse create(MultipartFile multipartFile);
    File get(String id);
}

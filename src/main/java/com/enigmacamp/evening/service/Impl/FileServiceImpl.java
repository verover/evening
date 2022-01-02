package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.File;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.FileRepository;
import com.enigmacamp.evening.payload.response.FileResponse;
import com.enigmacamp.evening.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileResponse create(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileResponse fileResponse = null;

        try {
            File file = new File();
            file.setName(fileName);
            file.setType(multipartFile.getContentType());
            file.setData(multipartFile.getBytes());

            File save = fileRepository.save(file);

            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(save.getFileId())
                    .toUriString();

            fileResponse = new FileResponse(save.getName(), fileDownloadUri, save.getType(), save.getData().length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public File get(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not Found"));
    }
}
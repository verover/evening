package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.response.FileResponse;
import com.enigmacamp.evening.entity.File;
import com.enigmacamp.evening.service.FileService;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE,
            },
            produces = "application/json"
    )
    public ResponseEntity<WebResponse<FileResponse>> createNewFile(
            @RequestPart(name = "file",required = true) MultipartFile multipartFile) {
        FileResponse fileResponse = fileService.create(multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(new WebResponse<>("Success save image",fileResponse));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
        File file = fileService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attacment; filename \"" + file.getName() + "\"")
                .body(file.getData());
    }
}

package com.newgen.storage.controller;

import com.newgen.storage.entity.StorageFile;
import com.newgen.storage.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public StorageFile saveFile(@RequestParam MultipartFile file, String userId) throws  Exception {
        StorageFile storageFile = new StorageFile();
        storageFile.setMultipartFile(file);
        storageFile.setUserId(userId);
        storageFile.setId(UUID.randomUUID().toString());

        return storageService.saveFileData(storageFile, file.getInputStream()) ? storageFile : null;


    }
}


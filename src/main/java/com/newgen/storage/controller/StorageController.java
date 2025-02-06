package com.newgen.storage.controller;

import com.newgen.storage.entity.StorageFile;
import com.newgen.storage.service.StorageFileService;
import com.newgen.storage.service.StorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/storage")

public class StorageController {

    private final StorageFileService storageService;
    private final StorageService fileService;

    public StorageController(StorageFileService storageService, StorageService fileService) {
        this.storageService = storageService;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> saveFileByFormData(@ModelAttribute StorageFile storageFile) throws Exception {

        return storageService.saveStorageFile(storageFile) ?
                ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getFileInfo(@PathVariable String id) {
        return
                ResponseEntity.ok(storageService.getStorageFileById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getFilesByUserId(@PathVariable String id) {
        return ResponseEntity.ok(storageService.getAllFleByUserId(id));
    }

    @GetMapping("/public")
    public List<StorageFile> getAllPublicFiles() {
        return storageService.getAllPublicFile();
    }

    @GetMapping()
    public List<StorageFile> getAllFiles() {
        return storageService.getAllFiles();
    }


    @GetMapping("/data/{id}")
    public ResponseEntity<?> getFileData(@PathVariable String id) {
        Optional<StorageFile> storageFile = storageService.getStorageFileById(id);
        byte[] fileData = null;
        if (storageFile.isPresent() && (fileData = fileService.getFileData(storageFile.get())) != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(getMediaType(storageFile.get().getFileType()));
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFileById(@PathVariable String id) {
        return storageService.deleteStorageFile(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @PutMapping
    public ResponseEntity<?> updateFile(@RequestBody StorageFile storageFile) {
        return storageService.updateStorageFile(storageFile)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String id ) {
        // Example: Simulating an InputStream (Replace this with your actual InputStream)
        StorageFile storageFile=storageService.getStorageFileById(id ).orElse(null);
        InputStream inputStream =fileService.getInputStream(storageFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+storageFile.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

    private MediaType getMediaType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "mp4":
                return MediaType.parseMediaType("video/mp4");
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // For generic file types
        }
    }

}


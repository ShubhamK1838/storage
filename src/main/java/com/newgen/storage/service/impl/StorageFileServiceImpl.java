package com.newgen.storage.service.impl;

import com.newgen.storage.entity.StorageFile;
import com.newgen.storage.repository.StorageFileRepository;
import com.newgen.storage.service.StorageFileService;
import com.newgen.storage.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageFileServiceImpl implements StorageFileService {

    private final StorageFileRepository storageRepo;
    private final StorageService storageService;

    public StorageFileServiceImpl(StorageFileRepository repo, StorageService storageService) {
        storageRepo = repo;
        this.storageService = storageService;
    }

    @Override
    public boolean saveStorageFile(StorageFile storageFile) {
        storageFile.setId(UUID.randomUUID().toString());
        if (storageService.saveFileData(storageFile)) {
            storageRepo.save(storageFile);
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteStorageFile(String id) {
        Optional<StorageFile> storageFileOptional = getStorageFileById(id);
        if (storageFileOptional.isPresent()) {
            var file = storageFileOptional.get();
            if (storageService.deleteFile(file)) ;
            {
                storageRepo.deleteById(file.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStorageFile(StorageFile storageFile) {

        var fileOptional = getStorageFileById(storageFile.getId());
        if (fileOptional.isPresent()) {
            var file = fileOptional.get();
            file.setName(storageFile.getName());
            storageRepo.save(file);
            return true;
        }
        return false;
    }

    @Override
    public List<StorageFile> getAllFiles() {
        return storageRepo.findAll();
    }

    @Override
    public Optional<StorageFile> getStorageFileById(String id) {
        return storageRepo.findById(id);
    }

    @Override
    public List<StorageFile> getAllFleByUserId(String id) {
        return storageRepo.findByUserid(id);
    }
}

package com.newgen.storage.service;


import com.newgen.storage.entity.StorageFile;

import java.util.List;
import java.util.Optional;

public interface StorageFileService {

    boolean saveStorageFile(StorageFile storageFile);

    boolean deleteStorageFile(String  storageFile);

    boolean updateStorageFile(StorageFile storageFile);

    List<StorageFile> getAllFiles();

    Optional<StorageFile> getStorageFileById(String id);

    List<StorageFile> getAllFleByUserId(String id);


}

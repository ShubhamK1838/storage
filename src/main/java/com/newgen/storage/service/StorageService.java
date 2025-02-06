package com.newgen.storage.service;

import com.newgen.storage.entity.StorageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    boolean saveFileData(StorageFile file , InputStream ios);
    boolean saveFileData(StorageFile file);
    boolean deleteFile(StorageFile file );
    InputStream getInputStream(StorageFile file );
    boolean saveFileByMultipart(MultipartFile part ,  StorageFile file );
    byte[] getFileData(StorageFile file );

}

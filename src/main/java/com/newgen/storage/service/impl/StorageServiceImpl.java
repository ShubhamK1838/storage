package com.newgen.storage.service.impl;

import com.newgen.storage.entity.StorageFile;
import com.newgen.storage.service.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final String path;

    public StorageServiceImpl() {
        path = System.getProperty("user.home") + File.separator + "project-storage";
    }

    @PostConstruct
    public void init() {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
                log.info("Storage File is Created: " + path);
            } else {
                log.info("Storage File is Exits: " + path);
            }

        } catch (Exception error) {
            log.warn("Storage File Is Not Exits");
        }
    }

    @Override
    public boolean saveFileData(StorageFile file, InputStream ios) {
        try (FileOutputStream out = new FileOutputStream(getUserFile(file))) {
            byte[] buffer = new byte[1024 * 1024 * 1024];
            while (ios.read(buffer) != -1) {
                out.write(buffer);
            }
            ios.close();
            return true;
        } catch (Exception error) {
            log.error(error.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveFileData(StorageFile file) {
        return saveFileByMultipart(file.getMultipartFile(), file );
    }

    @Override
    public boolean deleteFile(StorageFile storageFile) {
        try {
            File file = getUserFile(storageFile);
            return file.delete();
        } catch (Exception error) {
            log.error(error.getMessage());
        }
        return false;

    }

    @Override
    public InputStream getInputStream(StorageFile storageFile) {
        try {
            File file = getUserFile(storageFile);
            return new FileInputStream(file);

        } catch (Exception error) {
            log.error(error.getMessage());
        }

        return null;
    }

    @Override
    public boolean saveFileByMultipart(MultipartFile part, StorageFile storageFile) {
        File file = getUserFile(storageFile);
        try {
            part.transferTo(file);
            return true;
        } catch (Exception error) {
            log.error(error.getMessage());
        }
        return false;
    }

    private File getUserFile(StorageFile storageFile) {
        try {
            File file = new File(getUserDir(storageFile).toString() + File.separator + storageFile.getId() + "." + storageFile.getFileType());
            if (!file.exists()) {
                file.createNewFile();
                return file;
            } else {
                return file;
            }
        } catch (Exception error) {
            log.error(error.getMessage());
        }
        return null;

    }

    public File getUserDir(StorageFile storageFile) {
        try {
            File file = new File(path + File.separator + storageFile.getUserid());
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    throw new NullPointerException("The User Directory is Null");
                }
                return file;
            } else {
                return file;
            }
        } catch (Exception error) {
            log.error(error.getMessage());
        }
        return null;
    }

}

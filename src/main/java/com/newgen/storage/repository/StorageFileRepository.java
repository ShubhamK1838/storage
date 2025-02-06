package com.newgen.storage.repository;

import com.newgen.storage.entity.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageFileRepository extends JpaRepository<StorageFile, String> {

    List<StorageFile> findByUserid(String userId);

    List<StorageFile> findByVisible(boolean visible);
}

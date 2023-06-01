package com.jun.plugin.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.plugin.upload.entity.UploadFile;

public interface FileRepository extends JpaRepository<UploadFile, Integer> {
    public UploadFile findOneByFileName(String fileName);
}

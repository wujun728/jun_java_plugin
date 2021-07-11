package kr.hwb.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hwb.example.entity.UploadFile;

public interface FileRepository extends JpaRepository<UploadFile, Integer> {
    public UploadFile findOneByFileName(String fileName);
}

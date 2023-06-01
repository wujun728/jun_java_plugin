package com.jun.plugin.FileUpload.Java.Service;

import org.springframework.stereotype.Service;

import com.jun.plugin.FileUpload.Java.Dao.Query;
import com.jun.plugin.FileUpload.Java.Model.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class FileService extends BaseService<File> {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isMd5Exist(String md5) {
        Query query = new Query(entityManager);
        @SuppressWarnings("unchecked") List<File> result = query.from(File.class)
                .select()
                .whereEqual("MD5", md5)
                .createTypedQuery()
                .getResultList();

        return !result.isEmpty();

    }
}

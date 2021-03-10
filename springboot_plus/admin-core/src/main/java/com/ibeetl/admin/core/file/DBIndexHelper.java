package com.ibeetl.admin.core.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeetl.admin.core.dao.CoreFileDao;
import com.ibeetl.admin.core.entity.CoreFile;

/**
 * 从数据库搜索文档信息，对应core_file,和core_file_tag,目前先忽略tag功能
 * @author Wujun
 *
 */

public  class DBIndexHelper {
    
    CoreFileDao fileDao;
    
    public DBIndexHelper(CoreFileDao fileDao) {
        this.fileDao = fileDao;
    }
    
    public CoreFile getFileItemByPath(String path) {
        CoreFile t = new CoreFile();
        t.setPath(path);
        CoreFile dbData = fileDao.templateOne(t);
       return dbData;
    }
    
    public CoreFile getFileItemById(Long id) {
        CoreFile dbData =  fileDao.unique(id);
        return dbData;
    }
    
    
    public void createFileItem(CoreFile file,List<FileTag> tags) {
         fileDao.insert(file,true);
         if(tags==null||tags.isEmpty()) {
             return ;
         }
         Long fileId = file.getId();
         for(FileTag tag:tags) {
             tag.setFileId(fileId);
         }
         fileDao.getSQLManager().insertBatch(FileTag.class, tags);
    }
    
    
    public List<CoreFile> queryByUserId(Long userId,List<FileTag> tags){
        List<CoreFile> dbDatas = fileDao.createQuery().lambda().andEq(CoreFile::getUserId, userId).select();
        return dbDatas;
    }
    
    public List<CoreFile> queryByBiz(String bizType,String bizId){
        CoreFile template=new CoreFile();
        template.setBizType(bizType);
        template.setBizId(bizId);
        List<CoreFile> dbDatas = fileDao.template(template);
        return  dbDatas;
    }
    
    public List<CoreFile> queryByBatchId(String batchId){
        CoreFile template=new CoreFile();
        template.setFileBatchId(batchId);
        List<CoreFile> dbDatas = fileDao.template(template);
        return  dbDatas;
    }
    
   
    
}

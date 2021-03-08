package com.ibeetl.admin.core.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.ibeetl.admin.core.dao.CoreFileDao;
import com.ibeetl.admin.core.entity.CoreFile;
import com.ibeetl.admin.core.util.DateUtil;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.UUIDUtil;
/**
 * 一个本地文件系统，管理临时文件和用户文件
 * @author Wujun
 *
 */
public class LocalFileService   implements FileService {
    Log log = LogFactory.getLog(this.getClass());
    DBIndexHelper dbHelper =  null;
	String root = null;

	public LocalFileService(ApplicationContext ctx,String root) {
		this.root = root;
		new File(root,"temp").mkdir();
		dbHelper = new DBIndexHelper(ctx.getBean(CoreFileDao.class));
	}

	@Override
	public FileItem loadFileItemByPath(String path) {
	    CoreFile coreFile = dbHelper.getFileItemByPath(path);
	    if(coreFile!=null) {
	        return getFileItem(coreFile);
	    }
	    LocalFileItem item = new LocalFileItem(root);
		item.setPath(path);
		item.setName(parseTempFileName(path));
		item.setTemp(true);
		return item;
	}

	@Override
	public FileItem createFileTemp(String name) {
		FileItem item = new LocalFileItem(root);
		String fileName = "temp"+File.separator+name + "." + this.suffix();
		item.setPath(fileName);
		item.setName(name);
		item.setTemp(true);
		return item;
	}

	@Override
    public FileItem createFileItem(String name, String bizType, String bizId, Long userId, Long orgId, String fileBatchId,List<FileTag> tags) {
	    CoreFile coreFile = new CoreFile();
	    coreFile.setBizId(bizId);
	    coreFile.setBizType(bizType);
	    coreFile.setUserId(userId);
	    coreFile.setOrgId(orgId);
	    coreFile.setName(name);
	    coreFile.setCreateTime(new Date());
	    coreFile.setFileBatchId(fileBatchId);
	    String dir = DateUtil.now("yyyyMMdd");
	    File dirFile = new File(root + File.separator + dir);
	    if(!dirFile.exists()) {
	        dirFile.mkdirs();
	    }
	    String fileName = name+"."+UUIDUtil.uuid();
	    String path =  dir+File.separator+fileName;
	    coreFile.setPath(path);
	    //目前忽略tags
	    dbHelper.createFileItem(coreFile,tags);
	    return this.getFileItem(coreFile);
	    
    }
	
	

	private String suffix() {
		// TODO,改成唯一算法
		return DateUtil.now("yyyyMMddhhmm")+ "-" + UUIDUtil.uuid();
	}
	
	private String parseTempFileName(String path) {
		File file = new File(path);
		String name =  file.getName();
		//去掉最后的临时标记
		int index = name.lastIndexOf(".");
		return name.substring(0, index);
	}
	
	protected  FileItem getFileItem(CoreFile file) {
	    LocalFileItem item = new LocalFileItem(root);
	    item.setName(file.getName());
	    item.setPath(file.getPath());
	    item.setBizId(file.getBizId());
	    item.setBizType(file.getBizType());
	    item.setId(file.getId());
	    item.setOrgId(file.getOrgId());
	    item.setId(file.getId());
	    return item;
	 }
	
	protected  List<FileItem> getFileItem(List<CoreFile> files) {
	    List<FileItem> items = new ArrayList<>(files.size());
	    for(CoreFile file:files) {
	        items.add(this.getFileItem(file));
	    }
	    return items;
       
     }

    

    @Override
    public FileItem getFileItemById(Long id) {
        return this.getFileItem(dbHelper.getFileItemById(id));
    }

    @Override
    public List<FileItem> queryByUserId(Long userId, List<FileTag> tags) {
        return this.getFileItem(dbHelper.queryByUserId(userId, tags));
    }

    @Override
    public List<FileItem> queryByBiz(String bizType, String bizId) {
        return this.getFileItem(dbHelper.queryByBiz(bizType, bizId));
    }

    @Override
    public List<FileItem> queryByBatchId(String fileBatchId) {
        return this.getFileItem(dbHelper.queryByBatchId(fileBatchId));
    }

    @Override
    public void removeFile(Long id, String fileBatchId) {
        CoreFile file = dbHelper.getFileItemById(id);
        if(!file.getFileBatchId().equals(fileBatchId)){
            return ;
        }
        
        FileItem item = this.getFileItem(file);
        boolean success = item.delete();
        if(!success) {
            log.warn("删除文件失败 "+file.getName()+ ",id="+file.getId()+" path="+file.getPath());
            throw new PlatformException("删除文件失败 "+file.getName());
        }
        dbHelper.fileDao.deleteById(id);
        return ;
      
        
    }

    @Override
    public void updateFile(String fileBatchId, String bizType, String bizId) {
       dbHelper.fileDao.updateBatchIdInfo(bizType, bizId, fileBatchId);
    }

    @Override
    public FileItem getFileItemById(Long id, String fileBatchId) {
        CoreFile file = dbHelper.getFileItemById(id);
        if(!file.getFileBatchId().equals(fileBatchId)){
            return  null;
        }
        return this.getFileItem(file);
    }
	
	
	
	

}

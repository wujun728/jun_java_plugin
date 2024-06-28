package io.github.wujun728.file.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.file.entity.SysFilesEntity;
import io.github.wujun728.file.mapper.SysFilesMapper;
import io.github.wujun728.file.utils.QiniuUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wujun728.file.config.FileProperties;
import io.github.wujun728.file.service.SysFilesService;

/**
 * 文件上传 服务类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@EnableConfigurationProperties(FileProperties.class)
@Service("sysFilesService")
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesEntity> implements SysFilesService {
    @Resource
    private FileProperties fileUploadProperties;
    
    @Resource
    private SysFilesMapper sysFilesMapper;
    
    @Override
    public Result saveFile(MultipartFile file) {
        //存储文件夹
//        String createTime = DateUtils.format(new Date(), DateUtils.DATEPATTERN);
        String createTime = DateUtil.now();
        String newPath = fileUploadProperties.getPath() + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        try {
            String fileName = file.getOriginalFilename();
            //id与filename保持一直，删除文件
            String fileNameNew = UUID.randomUUID().toString().replace("-", "") + getFileType(fileName);
            String newFilePathName = newPath + fileNameNew;
            String url = fileUploadProperties.getUrl() + "/" + createTime + "/" + fileNameNew;
            //创建输出文件对象
            File outFile = new File(newFilePathName);
            //拷贝文件到输出文件对象
            FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
            //保存文件记录
            saveFilesEntity(fileName, newFilePathName, url, "filemanager", "","");
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("src", url);
            return Result.success(resultMap);
        } catch (Exception e) {
            throw new BusinessException("上传文件失败");
        }
    }


	private void saveFilesEntity(String fileName, String newFilePathName, String url,String filesize, String biztype, String bizid) {
		SysFilesEntity entity = new SysFilesEntity();
		entity.setFileName(fileName);
		entity.setFilePath(newFilePathName);
		entity.setUrl(url);
        entity.setFileSize(filesize);
		entity.setDictBiztype(biztype);
		entity.setRefBizid(bizid);
		this.save(entity);
	}


//	@Override
//	public Result saveFile(MultipartFile file, String biztype, String bizid) {
////        Map map = Maps.newHashMap();
////        map.put("biztype",biztype);
////        map.put("bizid", bizid);
//		//this.saveOssFile(file);
//		return this.saveFile(file,biztype,bizid);
//	}

//    @Override
//    public Result saveFile(MultipartFile file, String biztype, String bizid) {
//        mapTmp.set(ImmutableMap.builder().put("biztype",biztype).put("bizid", bizid).build());
////		this.saveOssFile(file);
//        return this.saveOssFile(file);
//    }





//    private static final ThreadLocal<Map<Object, Object>> mapTmp = new ThreadLocal();




//    @Override
//    @Deprecated
//    public Result saveOssFile(MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            String fileNameNew = QiniuUtils.getFileNameByDate(fileName);
//            QiniuUtils.uploadFile(file, fileNameNew);
//            String URL = QiniuUtils.domain + "/" + fileName;
//            String downUrl = QiniuUtils.downloadURL(fileNameNew);;
//            //保存文件记录
//            saveFilesEntity(fileNameNew, URL, downUrl, "filemanager", "");
//            Map<String, String> resultMap = new HashMap<>();
//            resultMap.put("src", URL);
//            return Result.success(resultMap);
//        } catch (Exception e) {
//            throw new BusinessException("上传文件失败");
//        }
//    }


    @Override
    public Result saveFile(MultipartFile file,String bizType,String bizid) {
        try {
            String username = "admin";
            String fileName = file.getOriginalFilename();
            String fileNameNew = QiniuUtils.getFileNameByDate(username,fileName);
//    		QiniuUtils.uploadFile(file, fileNameNew);
            String downUrl = QiniuUtils.uploadFileV2(file, fileNameNew);
            String URL = QiniuUtils.domain + "/" + fileName;
//    		String downUrl = QiniuUtils.download(fileNameNew);;
            //保存文件记录
            saveFilesEntity(fileNameNew, downUrl, "filemanager", QiniuUtils.FormetFileSize(file.getSize()),bizType,bizid);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("src", downUrl);
            return Result.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("上传文件失败");
        }
    }
    @Override
    public Result saveFile(File file,String bizType,String bizid) {
        try {
            String username = "sessionService.getCurrentUsername()";
            String fileName =  file.getName();
            String fileNameNew = QiniuUtils.getFileNameByDate(username,fileName);
//    		QiniuUtils.uploadFile(file, fileNameNew);
            String downUrl = QiniuUtils.uploadFileV2(file, fileNameNew);
            String URL = QiniuUtils.domain + "/" + fileName;
//    		String downUrl = QiniuUtils.download(fileNameNew);;
            //保存文件记录
            saveFilesEntity(fileNameNew,  downUrl, "filemanager",
                    QiniuUtils.FormetFileSize(FileUtil.size(file)),"","");
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("src", downUrl);
            return Result.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("上传文件失败");
        }
    }


    @Override
    public void removeByIdsAndFiles(List<String> ids) {
        List<SysFilesEntity> list = this.listByIds(ids);
        list.forEach(entity -> {
            QiniuUtils.deleteFileFromQiniu(entity.getFileName());
            //如果之前的文件存在，删除
            File file = new File(entity.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        });
        this.removeByIds(ids);

    }

//    @Override
//    public void removeByIdsAndFiles(List<String> ids) {
//        List<SysFilesEntity> list = this.listByIds(ids);
//        list.forEach(entity -> {
//            //如果之前的文件存在，删除
//            File file = new File(entity.getFilePath());
//            if (file.exists()) {
//                file.delete();
//            }
//        });
//        this.removeByIds(ids);
//
//    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }




}
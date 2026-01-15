package io.github.wujun728.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.file.entity.SysFilesEntity;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 文件上传 服务类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public interface SysFilesService extends IService<SysFilesEntity> {

    Result saveFile(MultipartFile file);

    void removeByIdsAndFiles(List<String> ids);

	Result saveFile(MultipartFile file, String biztype, String bizid);
	
    Result saveFile(File file, String biztype, String bizid);


}


package com.chentongwei.core.common.biz;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文件（图片）上传Biz
 **/
@Service
public class UploadBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Value("${upload.img.path}")
    private String uploadPath;

    /**
     * 图片上传
     *
     * @param file：文件
     * @param path：上传路径；比如tucao、chuiniube、doutu等
     * @return
     */
    public Result uploadImg(MultipartFile file, String path) {
        try {
            // 图片原始名称
            String orginName = file.getOriginalFilename();
            // 取得图片后缀名
            int index = orginName.lastIndexOf(".");
            String suffix = orginName.substring(index);
            // 对上传文件进行图片校验
            if (!file.getContentType().startsWith("image")) {
                throw new Exception("不能上传非图片格式的文件");
            }
            String fileName = System.currentTimeMillis() + suffix;
            // 取出yyyy-MM
            String date = DateUtil.getYearMonth(new Date());
            String targetPath = uploadPath + path + "/" + date;
            File targetFile = new File(targetPath, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 保存
            file.transferTo(targetFile);

            String rtnFileName = path + "/" + date + "/" + fileName;
            return ResultCreator.getSuccess(rtnFileName);
        } catch (Exception e) {
            LOG.info("图片上传失败");
            e.printStackTrace();
        }
        return null;
    }
}

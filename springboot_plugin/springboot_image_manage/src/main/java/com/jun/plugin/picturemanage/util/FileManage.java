package com.jun.plugin.picturemanage.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jun.plugin.picturemanage.conf.Constant;
import com.jun.plugin.picturemanage.conf.CustomException;
import com.jun.plugin.picturemanage.entity.FileResult;
import com.jun.plugin.picturemanage.entity.FileResultVo;
import com.jun.plugin.picturemanage.service.ConfService;

import sun.security.tools.PathList;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:21
 */
@Component
public class FileManage {

    @Autowired
    private ConfService confService;

    /**
     * 创建一个文件夹
     *
     * @param folderName
     * @param path
     * @return
     */
    public boolean createFolder(String folderName, String path) {
        if (StringUtils.isBlank(folderName) || StringUtils.isBlank(path)) {
            return false;
        }
        File root = handlerPath(path);
        File newFile = new File(root, folderName);
        if (!newFile.exists()) {
            boolean state = newFile.mkdir();
            return state;
        } else {
            throw new CustomException("此文件名已经存在");
        }
    }

    /**
     * 上传文件到服务器
     *
     * @param path
     * @param file
     * @return
     */
    public boolean uploadFile(String path, MultipartFile file) {
        if (file == null || StringUtils.isBlank(path) || file.isEmpty()) {
            return false;
        }
        File root = handlerPath(path);
        String fileName = file.getOriginalFilename();
        //读取是否重写文件名
        if (Boolean.valueOf(confService.get(Constant.UUID_FILE_NAME_SWITCH_KEY_NAME, "false"))) {
            //需要重写文件名
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUIDUtils.generateShortUuid() + prefix;
        }
        File target = new File(root, fileName);
        while (target.exists()) {
            StringBuffer sb = new StringBuffer();
            sb.append(UUIDUtils.getOrderNoByUUID())
                    .append("-")
                    .append(fileName);
            fileName = sb.toString();
            target = new File(root, fileName);
        }

        target.setReadable(true);
        target.setWritable(true);
        try {
            file.transferTo(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 删除一个文件
     *
     * @param path
     * @return
     */
    public boolean delete(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }

        File root = handlerPath(path);

        //判断是否是根路径
        if (root.getAbsolutePath().equals(Constant.ROOT_DIR)) {
            throw new CustomException("不可删除根路径");
        }

        if (root.exists()) {
            //存在
            return FileUtils.deleteQuietly(root);
        }
        return false;
    }


    /**
     * 遍历文件夹中所有的文件 core
     *
     * @param path
     * @return
     */
    public FileResultVo openDirectory(String path) {
        //在Path上统一加上前缀
        if (StringUtils.isBlank(path)) {
            path = File.separator;
        }
        File root = handlerPath(path);
        if (!root.isDirectory()) {
            throw new CustomException("此文件不是文件夹");
        }
        String urlPrefix = confService.get(Constant.URL_PREFIX_KEY_NAME);
        if (urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1);
        }

        FileResultVo vo = new FileResultVo();
        List<FileResult> resultList = new LinkedList<>();
        List<FileResultVo.PathPackage> pathList = new LinkedList<>();
        vo.setFiles(resultList);
        vo.setCurrentPath(pathList);

        File[] child = root.listFiles();
        for (File file : child) {
            FileResult item = new FileResult();
            item.setFileName(file.getName());
            item.setUpdateDate(new Date(file.lastModified()));
            if (file.isDirectory()) {
                item.setFileType("文件夹");
                item.setType(Constant.FileType.FOLDER);
            } else {
                //获取大小
                //获取详细类型
                item.setFileType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                item.setType(Constant.FileType.FILE);
                item.setSize(getFileSizeForCore(file.length()));
                String suffix = removeStartPrefix(file);
                item.setUrl(urlPrefix + suffix);
            }
            resultList.add(item);
        }
        //分离Path
        pathList.add(new FileResultVo.PathPackage("/", "/"));
        while (root != null && root.exists() && root.isDirectory() && !root.getAbsolutePath().equals(Constant.ROOT_DIR)) {
            String itemPath = removeStartPrefix(root);
            pathList.add(1, new FileResultVo.PathPackage(root.getName(), itemPath));
            root = root.getParentFile();
        }
        return vo;
    }

    /**
     * 返回文件夹的大小
     *
     * @param path
     * @return
     */
    public String computerFolderSize(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        File root = handlerPath(path);
        if (!root.isDirectory()) {
            throw new CustomException("此文件不是文件夹");
        }
        long size = FileUtils.sizeOfDirectory(root);
        return getFileSizeForCore(size);
    }

    /**
     * 重命名文件名称
     *
     * @param name
     * @param path
     * @return
     */
    public boolean rename(String name, String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        File root = handlerPath(path);

        return root.renameTo(new File(root.getParent(), name));
    }


    /**
     * 文件移动
     *
     * @param files
     * @param targetPath
     * @return
     */
    public JsonResult moveFile(List<String> files, String targetPath) {
        //检查目标路径
        File targetFile = handlerPath(targetPath);
        if (!targetFile.isDirectory()) {
            return JsonResult.error("目标路径不是文件夹");
        }
        List<File> fileList = new ArrayList<>(files.size());
        for (String file : files) {
            //检查是否存在
            File f = handlerPath(file);
            fileList.add(f);
        }

        if (!fileList.get(0).getParentFile().getAbsolutePath().equals(targetFile.getAbsolutePath())) {
            for (File f : fileList) {
                try {
                    FileUtils.moveFileToDirectory(f, targetFile, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JsonResult.actionSuccess();
    }


    private static String removeStartPrefix(File file) {
        return file.getAbsolutePath().substring(Constant.ROOT_DIR.length()).replaceAll("\\\\", "/");
    }


    /**
     * 路径处理
     *
     * @param path
     * @return
     */
    private File handlerPath(String path) {
        if (path.startsWith(Constant.ROOT_DIR)) {
            path = path.substring(Constant.ROOT_DIR.length());
        }

        if (!path.startsWith("/")) {
            path = File.separator + path;
        }

        path = Constant.ROOT_DIR + path;

        File root = new File(path);
        if (root == null || !root.exists()) {
            throw new CustomException("文件不存在");
        }

        return root;
    }

    /**
     * 返回文件的大小 格式化后的
     *
     * @param fileS
     * @return
     */
    private static String getFileSizeForCore(long fileS) {
        String size = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            size = df.format((double) fileS) + "BT";
        } else if (fileS < 1048576) {
            size = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            size = df.format((double) fileS / 1048576) + "MB";
        } else {
            size = df.format((double) fileS / 1073741824) + "GB";
        }
        return size;
    }

}

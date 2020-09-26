package com.kancy.emailplus.core;

import java.io.File;
import java.util.*;

/**
 * AbstractEmail
 *
 * @author kancy
 * @date 2020/2/21 2:18
 */
public abstract class AbstractEmail implements Email {

    private String id;
    private Date sendDate;
    private Map<String, File> files;
    private boolean html;

    public AbstractEmail() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * 消息id
     *
     * @return
     */
    @Override
    public String getId() {
        return id;
    }


    /**
     * html格式
     *
     * @return
     */
    @Override
    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    /**
     * 附件
     *
     * @return
     */
    @Override
    public Map<String, File> getFiles() {
        return files;
    }

    /**
     * 添加附件
     * @param fileName
     * @param file
     */
    public void addFile(String fileName, File file) {
        if (Objects.isNull(files)){
            files = new HashMap<>();
        }
        files.put(fileName, file);
    }

    /**
     * 添加附件
     * @param fileName
     * @param filePath
     */
    public void addFile(String fileName, String filePath) {
        if (Objects.isNull(files)){
            files = new HashMap<>();
        }
        files.put(fileName, new File(filePath));
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    @Override
    public void setSentDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

}

package com.zhangzhihao.FileUpload.Java.Model;


public class UploadInfo {
    private String md5;
    private String chunks;
    private String chunk;
    private String path;
    private String fileName;
    private String ext;

    public UploadInfo() {
    }

    public UploadInfo(String md5, String chunks, String chunk, String path, String fileName, String ext) {
        this.md5 = md5;
        this.chunks = chunks;
        this.chunk = chunk;
        this.path = path;
        this.fileName = fileName;
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "md5='" + md5 + '\'' +
                ", chunks='" + chunks + '\'' +
                ", chunk='" + chunk + '\'' +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getChunks() {
        return chunks;
    }

    public void setChunks(String chunks) {
        this.chunks = chunks;
    }

    public String getChunk() {
        return chunk;
    }

    public void setChunk(String chunk) {
        this.chunk = chunk;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}

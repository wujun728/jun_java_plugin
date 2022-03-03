package org.springrain.ueditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UeditorConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	
    public static final String ACTION_UPLOAD_IMAGE = "uploadimg";
    public static final String ACTION_UPLOAD_SCRAWL = "uploadscrawl";
    public static final String ACTION_UPLOAD_VIDEO = "uploadvideo";
    public static final String ACTION_UPLOAD_FILE = "uploadfile";
    public static final String ACTION_CATCHIMAGE = "catchimage";
    public static final String ACTION_LISTFILE = "listfile";
    public static final String ACTION_LISTIMAGE = "listimage";

    public static final String FIELD_NAME = "file";
    public static final String SCRAWL_TYPE = ".jpg";

    private static  List<String> CATCHER_DOMAIN = new ArrayList<>(Arrays.asList( "127.0.0.1", "localhost","mp.weixin.qq.com"));
    
    private static  List<String> IMAGE_ALLOW_FILES =new ArrayList<>( Arrays.asList( ".png", ".jpg", ".jpeg", ".gif", ".bmp",".webp" ));

    private static  List<String> VIDEO_ALLOW_FILES = new ArrayList<>(Arrays.asList(".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg",
            ".mpg", ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid" ));
    private static  List<String> ALLOW_FILES = new ArrayList<>(Arrays.asList( ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso", ".doc", ".docx", ".xls", ".xlsx", ".ppt",
                    ".pptx", ".pdf", ".txt", ".md", ".xml" ));
  
    
    static{
 	   ALLOW_FILES.addAll(IMAGE_ALLOW_FILES);
 	   ALLOW_FILES.addAll(VIDEO_ALLOW_FILES);
    }
    
   public UeditorConfig(){
   }
   
   public UeditorConfig(String urlPrefix){
	   this.setImageUrlPrefix(urlPrefix+"image/");
       this.setScrawlUrlPrefix(urlPrefix+"image/");
       this.setSnapscreenUrlPrefix(urlPrefix+"image/");
       this.setCatcherUrlPrefix(urlPrefix+"image/");
       this.setVideoUrlPrefix(urlPrefix+"video/");
       this.setFileUrlPrefix(urlPrefix+"file/");
       this.setImageManagerUrlPrefix(urlPrefix+"image/");
       this.setImageManagerListPath(urlPrefix+"image/");
       this.setFileManagerUrlPrefix(urlPrefix+"file/");
       this.setFileManagerListPath(urlPrefix+"file/");
	   
   }
   
    //上传图片配置项
    private String imageActionName=ACTION_UPLOAD_IMAGE;
    private String imageFieldName=FIELD_NAME;
    private Integer imageMaxSize=2048000;
    private List<String> imageAllowFiles=IMAGE_ALLOW_FILES;
    private boolean imageCompressEnable=true;
    private Integer imageCompressBorder=1600;
    private String imageInsertAlign="none";
    private String imageUrlPrefix;

    //截图工具上传
    private String snapscreenActionName=ACTION_UPLOAD_IMAGE;
    private String snapscreenUrlPrefix;
    private String snapscreenFieldName=FIELD_NAME;
    private String snapscreenInsertAlign="none";
    
    //抓取远程图片配置
    private String catcherActionName=ACTION_CATCHIMAGE;
    private String catcherFieldName=FIELD_NAME;
    private String catcherUrlPrefix;
    private Integer catcherMaxSize=2048000;
    private List<String> catcherLocalDomain=CATCHER_DOMAIN;
    private List<String> catcherAllowFiles=IMAGE_ALLOW_FILES;
    
    
    //上传视频配置
    private String videoActionName=ACTION_UPLOAD_VIDEO;
    private String videoFieldName=FIELD_NAME;
    private String videoUrlPrefix;
    private Integer videoMaxSize=1024000000;
    private List<String> videoAllowFiles=VIDEO_ALLOW_FILES;
    
    
    
    //上传文件配置
    private String fileActionName=ACTION_UPLOAD_FILE;
    private String fileFieldName=FIELD_NAME;
    private String fileUrlPrefix;
    private Integer fileMaxSize=51200000;
    private List<String> fileAllowFiles=ALLOW_FILES;
    
    
    
    // 列出指定目录下的图片 
    private String imageManagerActionName=ACTION_LISTIMAGE;
    private String imageManagerUrlPrefix;
    private String imageManagerListPath;
    private Integer imageManagerListSize=20;
    private String imageManagerInsertAlign="none";
    private List<String> imageManagerAllowFiles=IMAGE_ALLOW_FILES;
    
    
    //列出指定目录下的图文件
    private String fileManagerActionName=ACTION_LISTFILE;
    private String fileManagerUrlPrefix;
    private String fileManagerListPath;
    private List<String> fileManagerAllowFiles=ALLOW_FILES;
    private Integer fileManagerListSize=imageManagerListSize;
    
    
    //涂鸦
    private String scrawlActionName=ACTION_UPLOAD_SCRAWL;
    private String scrawlUrlPrefix;
    private String scrawlFieldName=FIELD_NAME;
    
   
    public String getImageActionName() {
        return imageActionName;
    }

    public void setImageActionName(String imageActionName) {
        this.imageActionName = imageActionName;
    }

    public String getSnapscreenActionName() {
        return snapscreenActionName;
    }

    public void setSnapscreenActionName(String snapscreenActionName) {
        this.snapscreenActionName = snapscreenActionName;
    }

    public String getScrawlActionName() {
        return scrawlActionName;
    }

    public void setScrawlActionName(String scrawlActionName) {
        this.scrawlActionName = scrawlActionName;
    }

    public String getVideoActionName() {
        return videoActionName;
    }

    public void setVideoActionName(String videoActionName) {
        this.videoActionName = videoActionName;
    }

    public String getFileActionName() {
        return fileActionName;
    }

    public void setFileActionName(String fileActionName) {
        this.fileActionName = fileActionName;
    }

    public String getCatcherActionName() {
        return catcherActionName;
    }

    public void setCatcherActionName(String catcherActionName) {
        this.catcherActionName = catcherActionName;
    }

    public String getImageManagerActionName() {
        return imageManagerActionName;
    }

    public void setImageManagerActionName(String imageManagerActionName) {
        this.imageManagerActionName = imageManagerActionName;
    }

    public String getFileManagerActionName() {
        return fileManagerActionName;
    }

    public void setFileManagerActionName(String fileManagerActionName) {
        this.fileManagerActionName = fileManagerActionName;
    }

    public String getImageFieldName() {
        return imageFieldName;
    }

    public void setImageFieldName(String imageFieldName) {
        this.imageFieldName = imageFieldName;
    }

    public String getScrawlFieldName() {
        return scrawlFieldName;
    }

    public void setScrawlFieldName(String scrawlFieldName) {
        this.scrawlFieldName = scrawlFieldName;
    }

    public String getCatcherFieldName() {
        return catcherFieldName;
    }

    public void setCatcherFieldName(String catcherFieldName) {
        this.catcherFieldName = catcherFieldName;
    }

    public String getVideoFieldName() {
        return videoFieldName;
    }

    public void setVideoFieldName(String videoFieldName) {
        this.videoFieldName = videoFieldName;
    }

    public String getFileFieldName() {
        return fileFieldName;
    }

    public void setFileFieldName(String fileFieldName) {
        this.fileFieldName = fileFieldName;
    }

    public String getImageUrlPrefix() {
        return imageUrlPrefix;
    }

    public void setImageUrlPrefix(String imageUrlPrefix) {
        this.imageUrlPrefix = imageUrlPrefix;
    }

    public String getScrawlUrlPrefix() {
        return scrawlUrlPrefix;
    }

    public void setScrawlUrlPrefix(String scrawlUrlPrefix) {
        this.scrawlUrlPrefix = scrawlUrlPrefix;
    }

    public String getSnapscreenUrlPrefix() {
        return snapscreenUrlPrefix;
    }

    public void setSnapscreenUrlPrefix(String snapscreenUrlPrefix) {
        this.snapscreenUrlPrefix = snapscreenUrlPrefix;
    }

    public String getCatcherUrlPrefix() {
        return catcherUrlPrefix;
    }

    public void setCatcherUrlPrefix(String catcherUrlPrefix) {
        this.catcherUrlPrefix = catcherUrlPrefix;
    }

    public String getVideoUrlPrefix() {
        return videoUrlPrefix;
    }

    public void setVideoUrlPrefix(String videoUrlPrefix) {
        this.videoUrlPrefix = videoUrlPrefix;
    }

    public String getFileUrlPrefix() {
        return fileUrlPrefix;
    }

    public void setFileUrlPrefix(String fileUrlPrefix) {
        this.fileUrlPrefix = fileUrlPrefix;
    }

    public String getImageManagerUrlPrefix() {
        return imageManagerUrlPrefix;
    }

    public void setImageManagerUrlPrefix(String imageManagerUrlPrefix) {
        this.imageManagerUrlPrefix = imageManagerUrlPrefix;
    }

    public String getFileManagerUrlPrefix() {
        return fileManagerUrlPrefix;
    }

    public void setFileManagerUrlPrefix(String fileManagerUrlPrefix) {
        this.fileManagerUrlPrefix = fileManagerUrlPrefix;
    }

    public List<String> getImageAllowFiles() {
        return imageAllowFiles;
    }

    public void setImageAllowFiles(List<String> imageAllowFiles) {
        this.imageAllowFiles = imageAllowFiles;
    }

    public List<String> getCatcherAllowFiles() {
        return catcherAllowFiles;
    }

    public void setCatcherAllowFiles(List<String> catcherAllowFiles) {
        this.catcherAllowFiles = catcherAllowFiles;
    }

    public List<String> getVideoAllowFiles() {
        return videoAllowFiles;
    }

    public void setVideoAllowFiles(List<String> videoAllowFiles) {
        this.videoAllowFiles = videoAllowFiles;
    }

    public List<String> getFileAllowFiles() {
        return fileAllowFiles;
    }

    public void setFileAllowFiles(List<String> fileAllowFiles) {
        this.fileAllowFiles = fileAllowFiles;
    }

    public List<String> getImageManagerAllowFiles() {
        return imageManagerAllowFiles;
    }

    public void setImageManagerAllowFiles(List<String> imageManagerAllowFiles) {
        this.imageManagerAllowFiles = imageManagerAllowFiles;
    }

    public List<String> getFileManagerAllowFiles() {
        return fileManagerAllowFiles;
    }

    public void setFileManagerAllowFiles(List<String> fileManagerAllowFiles) {
        this.fileManagerAllowFiles = fileManagerAllowFiles;
    }



	public Integer getImageManagerListSize() {
		return imageManagerListSize;
	}


	public void setImageManagerListSize(Integer imageManagerListSize) {
		this.imageManagerListSize = imageManagerListSize;
	}


	public Integer getFileManagerListSize() {
		return fileManagerListSize;
	}


	public void setFileManagerListSize(Integer fileManagerListSize) {
		this.fileManagerListSize = fileManagerListSize;
	}


	public Integer getImageMaxSize() {
		return imageMaxSize;
	}


	public void setImageMaxSize(Integer imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}


	public boolean isImageCompressEnable() {
		return imageCompressEnable;
	}


	public void setImageCompressEnable(boolean imageCompressEnable) {
		this.imageCompressEnable = imageCompressEnable;
	}


	public Integer getImageCompressBorder() {
		return imageCompressBorder;
	}


	public void setImageCompressBorder(Integer imageCompressBorder) {
		this.imageCompressBorder = imageCompressBorder;
	}


	public String getImageInsertAlign() {
		return imageInsertAlign;
	}


	public void setImageInsertAlign(String imageInsertAlign) {
		this.imageInsertAlign = imageInsertAlign;
	}


	public String getSnapscreenFieldName() {
		return snapscreenFieldName;
	}


	public void setSnapscreenFieldName(String snapscreenFieldName) {
		this.snapscreenFieldName = snapscreenFieldName;
	}


	public String getSnapscreenInsertAlign() {
		return snapscreenInsertAlign;
	}


	public void setSnapscreenInsertAlign(String snapscreenInsertAlign) {
		this.snapscreenInsertAlign = snapscreenInsertAlign;
	}


	public Integer getCatcherMaxSize() {
		return catcherMaxSize;
	}


	public void setCatcherMaxSize(Integer catcherMaxSize) {
		this.catcherMaxSize = catcherMaxSize;
	}


	public List<String> getCatcherLocalDomain() {
		return catcherLocalDomain;
	}


	public void setCatcherLocalDomain(List<String> catcherLocalDomain) {
		this.catcherLocalDomain = catcherLocalDomain;
	}


	public Integer getVideoMaxSize() {
		return videoMaxSize;
	}


	public void setVideoMaxSize(Integer videoMaxSize) {
		this.videoMaxSize = videoMaxSize;
	}


	public Integer getFileMaxSize() {
		return fileMaxSize;
	}


	public void setFileMaxSize(Integer fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}


	public String getImageManagerInsertAlign() {
		return imageManagerInsertAlign;
	}


	public void setImageManagerInsertAlign(String imageManagerInsertAlign) {
		this.imageManagerInsertAlign = imageManagerInsertAlign;
	}


	public String getFileManagerListPath() {
		return fileManagerListPath;
	}


	public void setFileManagerListPath(String fileManagerListPath) {
		this.fileManagerListPath = fileManagerListPath;
	}


	public String getImageManagerListPath() {
		return imageManagerListPath;
	}


	public void setImageManagerListPath(String imageManagerListPath) {
		this.imageManagerListPath = imageManagerListPath;
	}
}

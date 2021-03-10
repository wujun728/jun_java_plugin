package org.typroject.tyboot.component.cache.pipeline;

import org.typroject.tyboot.core.foundation.enumeration.StorageMode;

/**
 * Created by yaohelang on 2018/7/2.
 */
public class PipelineOperation {

    private String key;
    private String hkey;
    private StorageMode storageMode;
    private ResultCallBack resultCallBack;
    private Object result;

    public PipelineOperation(String key, String hkey, StorageMode storageMode) {
        this.key = key;
        this.hkey = hkey;
        this.storageMode = storageMode;
    }

    public PipelineOperation(String key, String hkey, StorageMode storageMode, ResultCallBack resultCallBack) {
        this.key = key;
        this.hkey = hkey;
        this.storageMode = storageMode;
        this.resultCallBack = resultCallBack;
    }

    public String getHkey() {
        return hkey;
    }

    public void setHkey(String hkey) {
        this.hkey = hkey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public StorageMode getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(StorageMode storageMode) {
        this.storageMode = storageMode;
    }

    public ResultCallBack getResultCallBack() {
        return resultCallBack;
    }

    public void setResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

package com.battcn.boot.extend.configuration.oss.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应结果
 *
 * @author Levin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StorageResponse extends BaseResponse implements java.io.Serializable {

    private static final long serialVersionUID = -498222912510624959L;

    private StorageItem storageItem;

    public static StorageResponse success(StorageItem storageItem) {
        StorageResponse response = new StorageResponse();
        response.setSuccessful(true);
        response.setStorageItem(storageItem);
        return response;
    }

    public static StorageResponse error(String message) {
        StorageResponse response = new StorageResponse();
        response.setSuccessful(false);
        response.setStorageItem(null);
        response.setMessage(message);
        return response;
    }
}

package com.ibeetl.admin.core.util.beetl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.file.FileItem;
import com.ibeetl.admin.core.file.FileService;

@Component
public class FileFunction implements Function {

    @Autowired
    FileService fileService;
    @Override
    public List<FileItem> call(Object[] paras, Context arg1) {
        String attachmentId = (String)paras[0];
        if(StringUtils.isEmpty(attachmentId)) {
            return Collections.EMPTY_LIST;
        }
        return  fileService.queryByBatchId(attachmentId);
    }

}

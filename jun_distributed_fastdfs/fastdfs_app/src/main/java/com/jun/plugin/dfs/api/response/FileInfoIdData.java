package com.jun.plugin.dfs.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoIdData {

    private FileInfoId body;

    private int result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class FileInfoId {

        public Integer fileInfoId;
    }
}

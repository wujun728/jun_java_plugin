package com.jun.plugin.dfs.core.fastdfs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.OutputStream;

@Data
@AllArgsConstructor
public class DFSDownloadFileTask implements Runnable {

    private String fileId;

    private OutputStream out;

    private boolean isClose;

    @Override
    public void run() {

    }

}

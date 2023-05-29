package com.jun.plugin.resources.core.properties;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.core.AutoResources;
import com.jun.plugin.resources.repository.RemoteRepository;
import com.jun.plugin.resources.repository.git.GitCore;
import com.jun.plugin.resources.utils.FileUtils;

/**
 * Created By Hong on 2018/8/13
 **/
public class GitProperties extends Hashtable<Object, Object> {

    /**
     * Git远程仓库
     */
    private static final RemoteRepository remoteRepository = new GitCore();

    static {
        //初始化时，克隆仓库
        remoteRepository.cloneRepository();
    }

    {
        //对象创建时，从仓库重新拉取一下最新的资源
        remoteRepository.pullRepository();
    }

    /**
     * 创建没有默认资源的空资源
     */
    public GitProperties() {
        super();
    }

    /***
     * 创建有默认资源的非空资源
     * @param defaults  默认
     */
    public GitProperties(Properties defaults) {
        super(defaults);
    }

    public synchronized Object setProperty(String key, String value) {
        return super.put(key, value);
    }

    public synchronized void load() {
        List<String> list = remoteRepository.listFileAll();
        for (String var1 : list) {
            this.load(var1);
        }
    }

    public synchronized void load(String... name) {
        List<String> list = remoteRepository.listFileAll();
        for (String var1 : name) {
            for (String var2 : list) {
                if (FileUtils.getFileName(var2).contains(var1)) {
                    this.load(var2);
                    break;
                }
            }
        }
    }

    private synchronized void load(String filePath) {
        try {
            Resources resources = new AutoResources("file:" + filePath);
            super.putAll(resources.getResources());
        } catch (IOException e) {

        }
    }

}

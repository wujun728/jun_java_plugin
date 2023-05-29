package com.jun.plugin.resources.repository;

import java.util.List;

/**
 * Created By Hong on 2018/8/13
 **/
public interface RemoteRepository {

    /**
     * 克隆仓库
     *
     * @return true成功
     */
    boolean cloneRepository();

    /**
     * 拉取最新的仓库资源
     *
     * @return true成功
     */
    boolean pullRepository();

    /**
     * 仓库全部文件
     *
     * @return 文件集合
     */
    List<String> listFileAll();
}

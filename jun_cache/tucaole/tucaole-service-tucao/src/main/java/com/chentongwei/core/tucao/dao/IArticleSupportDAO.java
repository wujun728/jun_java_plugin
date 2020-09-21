package com.chentongwei.core.tucao.dao;

import com.chentongwei.core.tucao.entity.io.support.ArticleSupportSaveIO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author TongWei.Chen 2017-12-18 17:34:58
 * @Project tucaole
 * @Description: 文章点赞DAO
 */
public interface IArticleSupportDAO {

    /**
     * 保存
     *
     * @param articleSupportList：参数
     * @return
     */
    boolean save(@Param("articleSupportList") List<ArticleSupportSaveIO> articleSupportList);

}

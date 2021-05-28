package com.chentongwei.core.tucao.entity.io.article;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章保存IO
 */
@Data
public class ArticleSaveIO extends TokenIO {

    /** 主键 id */
    private Long id;
    /** 分类id */
    @NotNull
    private Integer catalogId;
    /** 标题 */
    @NotNull
    @NotBlank
    private String title;
    /** 内容 */
    @NotNull
    @NotBlank
    private String content;
    /** 内容app端用【JSON格式】 */
    @NotNull
    @NotBlank
    private String contentApp;
    /** 是否匿名：0：匿名；1：正常 */
    @NotNull
    private Integer isAnonymous;
}
package com.chentongwei.core.tucao.entity.io.article;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章更新IO
 */
@Data
public class ArticleUpdateIO extends TokenIO {

    /** 主键 id */
    @NotNull
    private Long id;
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
    /** 修改时间 */
    private Date modifyTime;
}
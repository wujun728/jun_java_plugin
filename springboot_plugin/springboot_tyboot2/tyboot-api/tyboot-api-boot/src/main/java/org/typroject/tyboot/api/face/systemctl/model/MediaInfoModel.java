package org.typroject.tyboot.api.face.systemctl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

/**
 * <p>
 * 多媒体信息关联 model
 * </p>
 *
 * @author Wujun
 * @since 2018-12-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MediaInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 关联对象类型(用户头像，帖子，帖子评论，帖子语音....）
     */
    @JsonIgnore
    private String entityType;
    /**
     * 关联实体id
     */
    @JsonIgnore
    private String entityId;
    /**
     * 媒体类型(图片，视频，音频...）
     */
    @JsonIgnore
    private String mediaType;
    /**
     * 媒体文件名称
     */
    private String mediaFilename;
    /**
     * 媒体文件链接地址
     */
    private String mediaUrl;
    /**
     * 媒体别名
     */
    private String mediaAlias;

    @JsonIgnore
    private Integer orderNum;

}

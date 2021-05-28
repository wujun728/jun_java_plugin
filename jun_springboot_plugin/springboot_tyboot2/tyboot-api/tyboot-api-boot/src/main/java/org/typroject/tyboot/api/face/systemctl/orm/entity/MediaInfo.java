package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * <p>
 * 多媒体信息关联
 * </p>
 *
 * @author Wujun
 * @since 2018-12-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("systemctl_media_info")
public class MediaInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关联对象类型(用户头像，帖子，帖子评论，帖子语音....）
     */
	@TableField("ENTITY_TYPE")
	private String entityType;
    /**
     * 关联实体id
     */
	@TableField("ENTITY_ID")
	private String entityId;
    /**
     * 媒体类型(图片，视频，音频...）
     */
	@TableField("MEDIA_TYPE")
	private String mediaType;
    /**
     * 媒体文件名称
     */
	@TableField("MEDIA_FILENAME")
	private String mediaFilename;
    /**
     * 媒体文件链接地址
     */
	@TableField("MEDIA_URL")
	private String mediaUrl;
    /**
     * 媒体别名
     */
	@TableField("MEDIA_ALIAS")
	private String mediaAlias;
	@TableField("ORDER_NUM")
	private Integer orderNum;

}

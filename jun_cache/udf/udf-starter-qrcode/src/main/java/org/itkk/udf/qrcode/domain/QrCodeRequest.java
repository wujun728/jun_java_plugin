package org.itkk.udf.qrcode.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.itkk.udf.qrcode.option.BgImgStyle;
import org.itkk.udf.qrcode.option.DrawStyle;
import org.itkk.udf.qrcode.option.LogoStyle;
import org.itkk.udf.qrcode.option.QrCodeOptions;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * QrCodeRequest
 */
@Data
@ToString
@ApiModel(description = "二维码生成参数")
@Validated
public class QrCodeRequest implements Serializable {
    /**
     * 描述 : id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true, dataType = "string")
    @NotEmpty(message = "内容不能为空")
    private String content;

    /**
     * 尺寸(默认200)
     */
    @ApiModelProperty(value = "尺寸(默认200)", required = false, dataType = "int")
    private Integer size = QrCodeOptions.DEF_SIZE;

    /**
     * 绘制二维码的背景色(默认0xffffffff)
     */
    @ApiModelProperty(value = "绘制二维码的背景色(默认0xffffffff)", required = false, dataType = "string")
    private String bgColor = "0xffffffff";

    /**
     * 绘制二维码的前置色(0xff000000)
     */
    @ApiModelProperty(value = "绘制二维码的前置色(0xff000000)", required = false, dataType = "string")
    private String preColor = "0xff000000";

    /**
     * 绘制二维码的图片
     */
    @ApiModelProperty(value = "绘制二维码的图片", required = false, dataType = "string")
    private String drawImg;

    /**
     * 同一行相邻的两个着色点对应绘制的图片
     */
    @ApiModelProperty(value = "同一行相邻的两个着色点对应绘制的图片", required = false, dataType = "string")
    private String drawRow2Img;

    /**
     * 同一列相邻的两个着色点对应绘制的图片
     */
    @ApiModelProperty(value = "同一列相邻的两个着色点对应绘制的图片", required = false, dataType = "string")
    private String drawCol2Img;

    /**
     * 以(x,y)为左定点的四个着色点对应绘制的图片
     */
    @ApiModelProperty(value = "以(x,y)为左定点的四个着色点对应绘制的图片", required = false, dataType = "string")
    private String drawSize4Img;

    /**
     * 绘制二维码的样式(默认:RECT)
     */
    @ApiModelProperty(value = "绘制二维码的样式(默认:RECT)", required = false, dataType = "string")
    private String drawStyle = DrawStyle.RECT.name();

    /**
     * 二维码边框留白(取值 [0 到 4])(默认:1)
     */
    @ApiModelProperty(value = "二维码边框留白(取值 [0 到 4])(默认:1)", required = false, dataType = "int")
    private Integer padding = 1;

    /**
     * 二维码文本信息的编码格式(默认 : UTF-8)
     */
    @ApiModelProperty(value = "二维码文本信息的编码格式(默认 : UTF-8)", required = false, dataType = "string")
    private String charset = "UTF-8";

    // 探测图形

    /**
     * 探测图形外边框颜色
     */
    @ApiModelProperty(value = "探测图形外边框颜色", required = false, dataType = "string")
    private String detectOutColor;

    /**
     * 探测图形内边框颜色
     */
    @ApiModelProperty(value = "探测图形内边框颜色", required = false, dataType = "string")
    private String detectInColor;

    /**
     * 位置探测图形图片
     */
    @ApiModelProperty(value = "位置探测图形图片", required = false, dataType = "string")
    private String detectImg;

    // logo 相关

    /**
     * logo的http格式地址
     */
    @ApiModelProperty(value = "logo的http格式地址", required = false, dataType = "string")
    private String logo;

    /**
     * logo 占二维码大小的比例
     */
    @ApiModelProperty(value = "logo 占二维码大小的比例", required = false, dataType = "int")
    private Integer logoRate;

    /**
     * logo的样式(默认 : NORMAL)
     */
    @ApiModelProperty(value = "logo的样式(默认 : NORMAL)", required = false, dataType = "string")
    private String logoStyle = LogoStyle.NORMAL.name();

    /**
     * logo边框是否存在(默认 : false)
     */
    @ApiModelProperty(value = "logo边框是否存在(默认 : false)", required = false, dataType = "boolean")
    private boolean logoBorder = false;

    /**
     * logo边框颜色
     */
    @ApiModelProperty(value = "logo边框颜色", required = false, dataType = "string")
    private String logoBorderColor;

    // 背景相关

    /**
     * 背景图
     */
    @ApiModelProperty(value = "背景图", required = false, dataType = "string")
    private String bgImg;

    /**
     * 背景图宽
     */
    @ApiModelProperty(value = "背景图宽", required = false, dataType = "string")
    private Integer bgw;

    /**
     * 背景图高
     */
    @ApiModelProperty(value = "背景图高", required = false, dataType = "string")
    private Integer bgh;

    /**
     * 填充模式时，二维码在背景上的起始x坐标
     */
    @ApiModelProperty(value = "填充模式时，二维码在背景上的起始x坐标", required = false, dataType = "int")
    private Integer bgx;

    /**
     * 填充模式时，二维码在背景上的起始y坐标
     */
    @ApiModelProperty(value = "填充模式时，二维码在背景上的起始y坐标", required = false, dataType = "int")
    private Integer bgy;

    /**
     * 全覆盖模式时，二维码的透明度
     */
    @ApiModelProperty(value = "全覆盖模式时，二维码的透明度", required = false, dataType = "float")
    private Float bgOpacity;


    /**
     * 背景样式(默认 : OVERRIDE)
     */
    @ApiModelProperty(value = "背景样式(默认 : OVERRIDE)", required = false, dataType = "float")
    private String bgStyle = BgImgStyle.OVERRIDE.name();
}

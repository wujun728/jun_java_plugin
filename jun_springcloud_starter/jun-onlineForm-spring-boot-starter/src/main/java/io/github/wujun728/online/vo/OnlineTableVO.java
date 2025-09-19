package io.github.wujun728.online.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 在线表VO类
 * 用于表示在线表单的完整表结构信息
 */
//@Schema(description="Online表单开发")
@Data
public class OnlineTableVO {
    //@Schema(description="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //@Schema(description="id")
    private String id;
    //@Schema(description="是否树  0：否   1：是")
    private Integer tree;
    //@Schema(description="表类型  0：单表")
    private Integer tableType;
    //@Schema(description="表单布局  1：一列   2：两列   3：三列    4：四列")
    private Integer formLayout;
    //@Schema(description="树父id")
    private String treePid;
    //@Schema(description="树展示列")
    private String treeLabel;
    //@Schema(description="版本号")
    private Integer version;
    //@Schema(description="表名")
    private String name;
    //@Schema(description="表字段")
    private List<OnlineTableColumnVO> columnList;
    //@Schema(description="表描述")
    private String comments;
}


package com.springboot.springbootmybatisplus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/28
 * @Time: 23:28
 * @email: inwsy@hotmail.com
 * Description:
 */
@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("nick_name")
    private String nickName;

    private int age;

    @TableField("create_date")
    private Date createDate;
}

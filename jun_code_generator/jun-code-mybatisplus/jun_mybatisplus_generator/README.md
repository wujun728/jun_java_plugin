<p align="center">
	<img src="https://images.gitee.com/uploads/images/2020/0221/150233_9bdf1936_538536.jpeg" ></img>
</p>

<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=com.gitee.flying-cattle">
        <img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.gitee.flying-cattle/mybatis-dsc-generator.svg">
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.html">
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" ></img>
    </a>
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
    </a>
</p>

# mybatis-dsc-generator
完美集成lombok，swagger的代码生成工具，让你不再为繁琐的注释和简单的接口实现而烦恼：entity集成，格式校验，swagger; dao自动加@ mapper，service自动注释和依赖; 控制器实现单表的增副改查，并集成swagger实现api文档。如果有缘看见，期望得到你的star，very thx.
# 源码地址
- gitee:https://gitee.com/flying-cattle/mybatis-dsc-generator
- 码云：https://gitee.com/flying-cattle/mybatis-dsc-generator

# MAVEN地址
2.1.0版本是未集成Mybatis-plus版本——源码分支master
``` xml
<dependency>
    <groupId>com.gitee.flying-cattle</groupId>
    <artifactId>mybatis-dsc-generator</artifactId>
    <version>2.1.0.RELEASE</version>
</dependency>
```
3.0.0后的版本是集成了Mybatis-plus版本——源码分支mybatisPlus/3.0.7之前的版本groupId是github
``` xml
<dependency>
  <groupId>com.gitee.flying-cattle</groupId>
  <artifactId>mybatis-dsc-generator</artifactId>
  <version>3.0.7.RELEASE</version>
</dependency>
```
# 数据表结构样式
``` sql
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(40) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '秘密',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `type` int(10) unsigned DEFAULT NULL COMMENT '类型',
  `state` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '状态：-1失败，0等待,1成功',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_uid` bigint(20) DEFAULT '0' COMMENT '修改人用户ID',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录IP地址',
  `login_addr` varchar(100) DEFAULT NULL COMMENT '登录地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
要求必须有表注释，要求必须有主键为id,所有字段必须有注释（便于生成java注释swagger等）。

# 生成的实体类
生成方法参考源码中的：https://gitee.com/flying-cattle/mybatis-dsc-generator/blob/mybatisPlus/src/test/java/com/flying/cattle/mdg/MyGenerator.java

# 生成的实体类
``` java
/**
 * @filename:Order 2018年7月5日
 * @project deal-center  V1.0
 * Copyright(c) 2018 BianP Co. Ltd. 
 * All right reserved. 
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户实体类</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version      Description
 *---------------------------------------------------------------*
 * 2019年4月9日         flying-cattle         V1.0         initialize
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name = "id" , value = "用户ID")
    private Long id;

    @ApiModelProperty(name = "loginName" , value = "登录账户")
    private String loginName;

    @ApiModelProperty(name = "password" , value = "登录密码")
    private String password;

    @ApiModelProperty(name = "nickname" , value = "用户昵称")
    private String nickname;

    @ApiModelProperty(name = "type" , value = "用户类型")
    private Integer type;

    @ApiModelProperty(name = "state" , value = "用户状态")
    private Integer state;

    @ApiModelProperty(name = "note" , value = "备注")
    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createTime" , value = "用户创建时间")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "updateTime" , value = "修改时间")
    private Date updateTime;
	
    @ApiModelProperty(name = "updateUid" , value = "修改人用户ID")
    private Long updateUid;

    @ApiModelProperty(name = "loginIp" , value = "登录IP")
    private String loginIp;

    @ApiModelProperty(name = "loginIp" , value = "登录地址")
    private String loginAddr;
	
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
```
# 生成的DAO
``` java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.xin.usercenter.entity.User;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户数据访问层</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version       Description
 *---------------------------------------------------------------*
 * 2019年4月9日         flying-cattle         V1.0         initialize
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
	
}

```
# 生成的XML
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xin.usercenter.dao.UserDao">

	<resultMap id="BaseResultMap" type="com.xin.usercenter.entity.User">
		<id column="id" property="id" />
		<id column="login_name" property="loginName" />
		<id column="password" property="password" />
		<id column="nickname" property="nickname" />
		<id column="type" property="type" />
		<id column="state" property="state" />
		<id column="note" property="note" />
		<id column="create_time" property="createTime" />
		<id column="update_time" property="updateTime" />
		<id column="update_uid" property="updateUid" />
		<id column="login_ip" property="loginIp" />
		<id column="login_addr" property="loginAddr" />
	</resultMap>
	<sql id="Base_Column_List">
		id, login_name, password, nickname, type, state, note, create_time, update_time, update_uid, login_ip, login_addr
	</sql>
</mapper>
```
# 生成的SERVICE
``` java
import com.xin.usercenter.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户服务层</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version       Description
 *---------------------------------------------------------------*
 * 2019年4月9日         flying-cattle         V1.0         initialize
 */
public interface UserService extends IService<User> {
	
}
```
# 生成的SERVICE_IMPL
``` java
import com.xin.usercenter.entity.User;
import com.xin.usercenter.dao.UserDao;
import com.xin.usercenter.service.UserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户服务实现层</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version       Description
 *---------------------------------------------------------------*
 * 2019年4月9日         flying-cattle         V1.0         initialize
 */
@Service
public class UserServiceImpl  extends ServiceImpl<UserDao, User> implements UserService  {
	
}
```
# 生成的CONTROLLER，父类实现了增删改查接口，分页查询
``` java
/**
 * @filename:UserController 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2020 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.web;

import com.flying.cattle.aid.AbstractController;
import com.flying.cattle.entity.User;
import com.flying.cattle.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户API接口层</P>
 * @version: V1.0
 * @author: flying-cattle
 *
 */
@Api(description = "用户",value="用户" )
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService,User>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
```

看到这里，大家应该能看出，这个代码生成只适合一些特定的项目，不过确实为了那些喜欢lombok，swagger的猿们减少了很多不必要的工作。
一些朋友在问我JsonResult类，都在包内部。

使用场景，整合的应用项目：https://gitee.com/flying-cattle/infrastructure



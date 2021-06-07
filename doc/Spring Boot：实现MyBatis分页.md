# [Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 综合概述

想必大家都有过这样的体验，在使用Mybatis时，最头痛的就是写分页了，需要先写一个查询count的select语句，然后再写一个真正分页查询的语句，当查询条件多了之后，会发现真的不想花双倍的时间写 count 和 select，幸好我们有 pagehelper 分页插件，pagehelper 是一个强大实用的 MyBatis 分页插件，可以帮助我们快速的实现MyBatis分页功能，而且pagehelper有个优点是，分页和Mapper.xml完全解耦，并以插件的形式实现，对Mybatis执行的流程进行了强化，这有效的避免了我们需要直接写分页SQL语句来实现分页功能。那么，接下来我们就来一起体验下吧。

## 实现案例

接下来，我们就通过实际案例来讲解如何使用pagehelper来实现MyBatis分页，为了避免重复篇幅，此篇教程的源码基于《[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)》一篇的源码实现，读者请先参考并根据教程链接先行获取基础源码和数据库内容。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615143801453-1098533012.png)

### 添加相关依赖

首先，我们需要在 pom.xml 文件中添加分页插件依赖包。

pom.xml

```
<!-- pagehelper -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.5</version>
</dependency>
```

### 添加相关配置

然后在 application.yml 配置文件中添加分页插件有关的配置。

application.yml

```
# pagehelper   
pagehelper:
    helperDialect: mysql
    reasonable: true
    supportMethodsArguments: true
    params: count=countSql
```

### 编写分页代码

首先，在 DAO 层添加一个分页查找方法。这个查询方法跟查询全部数据的方法除了名称几乎一样。

SysUserMapper.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.dao;

import java.util.List;
import com.louis.springboot.demo.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    /**
     * 查询全部用户
     * @return
     */
    List<SysUser> selectAll();
    
    /**
     * 分页查询用户
     * @return
     */
    List<SysUser> selectPage();
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

然后在 SysUserMapper.xml 中加入selectPage的实现，当然你也可以直接用@Select注解将查询语句直接写在DAO代码，但我们这里选择写在XML映射文件，这是一个普通的查找全部记录的查询语句，并不需要写分页SQL，分页插件会拦截查询请求，并读取前台传来的分页查询参数重新生成分页查询语句。

SysUserMapper.xml

```
<select id="selectPage"  resultMap="BaseResultMap">
  select 
  <include refid="Base_Column_List" />
  from sys_user
</select>
```

服务层通过调用DAO层代码完成分页查询，这里统一封装分页查询的请求和结果类，从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会影响服务层以上的分页接口，起到了解耦的作用。

SysUserService.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service;
import java.util.List;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.util.PageRequest;
import com.louis.springboot.demo.util.PageResult;

public interface SysUserService {

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    SysUser findByUserId(Long userId);

    /**
     * 查找所有用户
     * @return
     */
    List<SysUser> findAll();

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

服务实现类通过调用分页插件完成最终的分页查询，关键代码是 PageHelper.startPage(pageNum, pageSize)，将前台分页查询参数传入并拦截MyBtis执行实现分页效果。

SysUserServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.louis.springboot.demo.dao.SysUserMapper;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.service.SysUserService;
import com.louis.springboot.demo.util.PageRequest;
import com.louis.springboot.demo.util.PageResult;
import com.louis.springboot.demo.util.PageUtils;

@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Override
    public SysUser findByUserId(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectAll();
    }
    
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }
    
    /**
     * 调用分页插件完成分页
     * @param pageQuery
     * @return
     */
    private PageInfo<SysUser> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysMenus = sysUserMapper.selectPage();
        return new PageInfo<SysUser>(sysMenus);
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

在控制器SysUserController中添加分页查询方法，并调用服务层的分页查询方法。

SysUserController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.louis.springboot.demo.service.SysUserService;
import com.louis.springboot.demo.util.PageRequest;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    
    @GetMapping(value="/findByUserId")
    public Object findByUserId(@RequestParam Long userId) {
        return sysUserService.findByUserId(userId);
    }
    
    @GetMapping(value="/findAll")
    public Object findAll() {
        return sysUserService.findAll();
    }
    
    @PostMapping(value="/findPage")
    public Object findPage(@RequestBody PageRequest pageQuery) {
        return sysUserService.findPage(pageQuery);
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

分页查询请求封装类。

PageRequest.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.util;
/**
 * 分页请求
 */
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

分页查询结果封装类。

PageResult.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.util;
import java.util.List;
/**
 * 分页返回结果
 */
public class PageResult {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public long getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<?> getContent() {
        return content;
    }
    public void setContent(List<?> content) {
        this.content = content;
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

分页查询相关工具类。

PageUtils.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.util;
import com.github.pagehelper.PageInfo;

public class PageUtils {

    /**
     * 将分页信息封装到统一的接口
     * @param pageRequest 
     * @param page
     * @return
     */
    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 编译测试运行

启动应用，访问：localhost:8088/swagger-ui.html，找到对应接口，模拟测试，结果如下。

参数：pageNum: 1, pageSize: 5

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615155434404-435531170.png)

参数：pageNum: 2, pageSize: 4

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615155551717-761098272.png)

## 胡言乱语

传统分页有点老，select和count都得搞。

分页SQL写不好，内容耦合还不小。

pagehelper帮你搞，使用起来有点屌。

## 参考资料

PageHelper：https://pagehelper.github.io/

PageHelper手册：https://pagehelper.github.io/docs/howtouse/

## 相关导航

[Spring Boot 系列教程目录导航](https://www.cnblogs.com/xifengxiaoma/p/11116330.html)

[Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

[Spring Boot：整合Swagger文档](https://www.cnblogs.com/xifengxiaoma/p/11022146.html)

[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)

[Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 源码下载

码云：https://gitee.com/liuge1988/spring-boot-demo.git
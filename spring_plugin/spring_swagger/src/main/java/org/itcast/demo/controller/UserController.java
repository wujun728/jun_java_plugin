package org.itcast.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.itcast.demo.entity.User;
import org.itcast.demo.exception.MyException;
import org.itcast.demo.service.IUserService;
import org.itcast.demo.util.ConstUtils;
import org.itcast.demo.util.ResponseCode;
import org.itcast.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author Wujun
 * @since 2019-06-24
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户管理接口Api",tags = "用户管理接口Api")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "添加用户",notes = "用户注册，状态默认0启用1禁用，生日格式：yyyy-MM-dd")
    @PostMapping("/save")
    public ResponseData save(User user){
        if(Objects.isNull(user)){
            return ResponseData.error(ResponseCode.PARAME_ERROR);
        }
        boolean flg = userService.save(user);
        if(flg){
            return ResponseData.ok("添加用户成功~");
        }
        return ResponseData.fail("添加用户失败~");
    }

    @ApiOperation("根据ID删除用户")
    @ApiImplicitParam(name = "id",value = "用户ID",dataType = "Integer" ,required = true,paramType = "path")
    @DeleteMapping("/del/{id}")
    public ResponseData delById(@PathVariable("id") Integer id){
        if(Objects.isNull(id)){
            return ResponseData.error(ResponseCode.PARAME_ERROR);
        }
        boolean flg = userService.removeById(id);
        if(flg){
            return ResponseData.ok("删除用户成功~");
        }
        return ResponseData.fail("删除用户失败~");
    }

    @ApiOperation("根据ID编辑用户")
    @PostMapping("/edit")
    public ResponseData edit(User user){
        if(Objects.isNull(user)){
            return ResponseData.error(ResponseCode.PARAME_ERROR);
        }
        boolean flg = userService.updateById(user);
        if(flg){
            return ResponseData.ok("修改用户成功~");
        }
        return ResponseData.fail("修改用户失败~");
    }

    @ApiOperation("自定义异常")
    @GetMapping("/exception")
    public ResponseData exception(){
        throw new MyException("异常测试");
    }

    @ApiOperation("系统异常")
    @GetMapping("/sys-error")
    public ResponseData sysException(){
        int i = 1/0;
        return ResponseData.ok();
    }

    @ApiOperation("显示用户列表")
    @GetMapping("/list")
    public ResponseData list(){
        List<User> list = userService.list();
        return ResponseData.ok(list);
    }

    @ApiOperation("根据ID获取用户信息")
    @ApiImplicitParam(name = "id",value = "用户ID",dataType = "Integer" ,required = true,paramType = "path")
    @GetMapping("/find/{id}")
    public ResponseData findById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return ResponseData.ok(user);
    }

    @ApiOperation("分页显示用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "页码",dataType = "Integer",required = true,paramType = "path",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示条数",dataType = "Integer",required = true,paramType = "path",defaultValue = "10")
    })
    @GetMapping("/page/{pageIndex}/{pageSize}")
    public ResponseData page(@PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize){
        Page<User> page = new Page<>(pageIndex,pageSize);
        IPage<User> userPage = userService.page(page);
        return ResponseData.okPage(userPage.getTotal(), userPage.getRecords());
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseData login(HttpSession session,String logName, String logPwd){
        //LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        //LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getLogName, logName).eq(User::getLogPwd, logPwd);
        //User user = userService.getOne(queryWrapper);
        ResponseData responseData = userService.login(logName, logPwd);
        if(responseData.isSuccess()){
            session.setAttribute(ConstUtils.CURRENT_LOGIN_USER,responseData.getData());
        }
        return responseData;
    }

    @ApiOperation("自定义MP查询方法")
    //@ApiImplicitParam(name = "relName", value = "真实姓名",dataType = "String",paramType = "form")
    @GetMapping("/findByRelName")
    public ResponseData findByRelName(@RequestParam("relName") String relName){
        return userService.findByRelName(relName);
    }

    @ApiOperation("自定义MP分页查询")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name="search",value="查询条件",required=true,paramType="form"),
//        @ApiImplicitParam(name = "pageIndex", value = "当前查询的页码",defaultValue = "1",paramType = "form"),
//        @ApiImplicitParam(name="pageSize",value = "每页显示的条数",defaultValue = "10",paramType = "form")
//    })
    @GetMapping("/userPage")
    public ResponseData userPage(@RequestParam(value = "search",required = false,defaultValue = "") String search,
                                 @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        return userService.userPage(pageIndex,pageSize,search);
    }

}


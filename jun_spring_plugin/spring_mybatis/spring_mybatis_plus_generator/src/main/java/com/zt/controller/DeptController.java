package com.zt.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zt.entity.Dept;
import com.zt.entity.vo.LayerJson;
import com.zt.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wujun
 * @since 2018-04-06
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired private IDeptService deptService;

    @RequestMapping(value = "/add-edit/{id}",method = RequestMethod.GET)
    public String addOrEdit(@PathVariable Integer id,Model model){
        if(id!=-1){
            Dept dept = deptService.selectById(id);
            model.addAttribute("dept",dept);
        }
        return "dept/add-edit";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "dept/list";
    }

    /**
     * 使用Layui table完成分页功能
     * @param pageIndex Layui table 默认提交当前页码的key 是page
     * @param pageSize Layui table 默认提交每页显示条数的key 是limit
     * @param search  查询条件
     * @return  返回自己组装符合Layui table格式的Json数据
     * @throws JsonProcessingException
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String list(@RequestParam(value = "page",defaultValue = "1") Integer pageIndex, @RequestParam(value = "limit" , defaultValue = "10") Integer pageSize, String search) throws JsonProcessingException {
        //查询页码和每页显示的条数
        Page page = new Page(pageIndex,pageSize);
        //查询条件
        EntityWrapper<Dept> ew = new EntityWrapper<>();
        ew.orderBy("deptno").or().like("deptno",search).or().like("dname",search).or().like("loc",search);

        //根据查询条件查询符合的数据
        Page<Dept> mapPage = deptService.selectPage(page, ew);

        //组装JSON对象数据
        LayerJson layerJson = LayerJson.getInstance(mapPage.getRecords(),mapPage.getTotal());
        //转为JSON字符串
        return new ObjectMapper().writeValueAsString(layerJson);
    }

    @RequestMapping(value = "/add-edit",method = RequestMethod.POST)
    public ResponseEntity addOrEdit(Dept dept){
        boolean flg = deptService.insertOrUpdate(dept);
        if(flg){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST)
    public ResponseEntity del(@PathVariable Integer id){
        boolean flg = deptService.deleteById(id);
        if(flg){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}


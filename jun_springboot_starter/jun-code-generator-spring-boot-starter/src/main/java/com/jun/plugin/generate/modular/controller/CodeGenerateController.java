package com.jun.plugin.generate.modular.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
//import com.jun.plugin.common.util.ConstantContextHolder;
import com.jun.plugin.generate.core.ref.PageResult;
import com.jun.plugin.generate.core.ref.ResponseData;
import com.jun.plugin.generate.core.ref.SuccessResponseData;
import com.jun.plugin.generate.core.util.TreeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.jun.plugin.generate.modular.entity.CodeGenerate;
import com.jun.plugin.generate.modular.param.CodeGenerateParam;
import com.jun.plugin.generate.modular.service.CodeGenerateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 代码生成器
 *
 * @auther yubaoshan
 * @date 12/15/20 11:20 PM
 */
@Controller
@RequestMapping({"/","/api"})
public class CodeGenerateController {

    private static String PATH_PREFIX = "generate/";

    @Resource
    private CodeGenerateService codeGenerateService;

    /**
     * 代码生成器页面
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @GetMapping("/codeGenerate/index")
    public String index() {
        return PATH_PREFIX + "index.html";
    }

    /**
     * 代码生成器表单页面
     *
     * @auther yubaoshan
     * @date 12/18/20 1:20 AM
     */
    @GetMapping("/codeGenerate/form")
    public String form() {
        return PATH_PREFIX + "form.html";
    }

    /**
     * 代码生成基础数据
     *
     * @author yubaoshan
     * @date 2020年12月16日20:58:48
     */
    //@Permission
    @ResponseBody
    @GetMapping("/codeGenerate/page")
    //@BusinessLog(title = "代码生成配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<CodeGenerate> page(CodeGenerateParam codeGenerateParam) {
        return codeGenerateService.page(codeGenerateParam);
    }

    /**
     * 代码生成基础配置保存
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    //@Permission
    @ResponseBody
    @PostMapping("/codeGenerate/add")
    //@BusinessLog(title = "代码生成配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        this.codeGenerateService.add(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置编辑
     *
     * @auther yubaoshan
     * @date 2020年12月16日20:56:19
     */
    //@Permission
    @ResponseBody
    @PostMapping("/codeGenerate/edit")
    //@BusinessLog(title = "代码生成配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        codeGenerateService.edit(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 删除代码生成基础配置
     *
     * @author yubaoshan
     * @date 2020年12月16日22:13:32
     */
    //@Permission
    @ResponseBody
    @PostMapping("/codeGenerate/delete")
    //@BusinessLog(title = "代码生成配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(CodeGenerateParam.delete.class) List<CodeGenerateParam> codeGenerateParamList) {
        codeGenerateService.delete(codeGenerateParamList);
        return new SuccessResponseData();
    }

    /**
     * 查询当前数据库用户下的所有表
     *
     * @author yubaoshan
     * @date 2020-12-16 01:55:48
     */
    //@Permission
    @ResponseBody
    @GetMapping("/codeGenerate/InformationList")
    //@BusinessLog(title = "数据库表列表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData InformationList() {
        return ResponseData.success(codeGenerateService.InformationTableList());
    }

    /**
     * 代码生成基础配置生成
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    //@Permission
    @ResponseBody
    @PostMapping("/codeGenerate/runLocal")
    //@BusinessLog(title = "代码生成_本地项目", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData runLocal(@RequestBody @Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam) {
        // 演示环境开启，则不允许操作
        this.codeGenerateService.runLocal(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置生成
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    //@Permission
    @GetMapping("/codeGenerate/runDown")
    //@BusinessLog(title = "代码生成_下载方式", opType = LogAnnotionOpTypeEnum.OTHER)
    public void runDown(@Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        // 演示环境开启，则不允许操作
        this.codeGenerateService.runDown(codeGenerateParam, response);
    }


    /**
     * 系统字典类型与字典值构造的树
     *
     * @author yubaoshan
     * @date 2020/4/30 22:20
     */
    @ResponseBody
    @GetMapping("/sysDictType/tree")
//    @BusinessLog(title = "系统字典类型_树", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree() {
        List list = SqlRunner.db().selectList(" SELECT id,dict_id as pid,value as code ,label as name from sys_dict_detail\n" +
                "union\n" +
                "SELECT  id,0 as pid,code,name from sys_dict ");
        TreeUtil.buildTree(list,"id","pid","children");
//        TreeUtil.buildTreeByMap(list, "id", "pid", "children", "0");
        return new SuccessResponseData(list);
    }


}

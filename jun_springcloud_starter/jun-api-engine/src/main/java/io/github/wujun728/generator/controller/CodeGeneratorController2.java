//package io.github.wujun728.generator.controller;
//
//import freemarker.template.TemplateException;
//import io.github.wujun728.common.base.Result;
//import io.github.wujun728.generator.entity.ClassInfo;
//import io.github.wujun728.generator.util.FreemarkerTool;
//import io.github.wujun728.generator.util.StringUtils;
//import io.github.wujun728.generator.util.TableParseUtil;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.zip.ZipOutputStream;
//
//@Controller
//public class CodeGeneratorController2 {
//    private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorController2.class);
//
//    @Resource
//    private FreemarkerTool freemarkerTool;
//
//    @RequestMapping("/")
//    public String index() {
//        return "index";
//    }
//    @RequestMapping("/generate")
//    public String generate() {
//        return "generate";
//    }
//
//    @RequestMapping("/codeGenerate")
//    @ResponseBody
//    public Result codeGenerate(String tableSql) {
//
//        try {
//
//            if (tableSql==null || tableSql.trim().length()==0) {
//                return new Result(500, "表结构信息不可为空");
//            }
//
//            // parse table
//            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(tableSql);
//
//            // code genarete
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("classInfo", classInfo);
//         // genProcessStringWriter(datas, result);
//			params.put("packageController", "io.github.wujun728.biz.controller");
//			params.put("packageService", "io.github.wujun728.biz.service");
//			params.put("packageServiceImpl", "io.github.wujun728.biz.service.impl");
//			params.put("packageDao", "io.github.wujun728.biz.dao");
//			params.put("packageMybatisXML", "io.github.wujun728.biz.model");
//			params.put("packageModel", "io.github.wujun728.biz.model");
//
//            // result
//            Map<String, String> result = new HashMap<String, String>();
//
//            result.put("controller_code", freemarkerTool.processString("code-generator/controller.ftl", params));
//            result.put("service_code", freemarkerTool.processString("code-generator/service.ftl", params));
//            result.put("service_impl_code", freemarkerTool.processString("code-generator/service_impl.ftl", params));
//
//            result.put("dao_code", freemarkerTool.processString("code-generator/dao.ftl", params));
//            result.put("mybatis_code", freemarkerTool.processString("code-generator/mybatis.ftl", params));
//            result.put("model_code", freemarkerTool.processString("code-generator/model.ftl", params));
//
//            // 计算,生成代码行数
//            int lineNum = 0;
//            for (Map.Entry<String, String> item: result.entrySet()) {
//                if (item.getValue() != null) {
//                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
//                }
//            }
//            logger.info("生成代码行数：{}", lineNum);
//
//            return  Result.ok(result);
//        } catch (IOException | TemplateException e) {
//            logger.error(e.getMessage(), e);
//            return new Result(500, "表结构解析失败");
//        }
//
//    }
//
//    @GetMapping("/codeGenerate/runDown")
//    //@BusinessLog(title = "代码生成_下载方式", opType = LogAnnotionOpTypeEnum.OTHER)
//    public void runDown(HttpServletResponse response) {
//
//    }
//
//
//}
package com.jun.plugin.codegenerator.admin;

import freemarker.template.TemplateException;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.generator.ClassInfo;
import io.github.wujun728.common.generator.util.FreemarkerTool;
import io.github.wujun728.common.generator.util.StringUtils;
import io.github.wujun728.common.generator.util.TableParseUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CodeGeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorController.class);

    @Resource
    private FreemarkerTool freemarkerTool;

    @RequestMapping("/")
    public String index() {
        return "index" ;
    }

    @RequestMapping("/generate")
    public String generate() {
        return "generate" ;
    }

    @RequestMapping("/codeGenerate")
    @ResponseBody
    public Result codeGenerate(String tableSql) {

        try {

            if (tableSql == null || tableSql.trim().length() == 0) {
                return new Result(500, "表结构信息不可为空");
            }

            // parse table
            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(tableSql);
            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);
            // genProcessStringWriter(datas, result);
            params.put("packageController", "com.jun.plugin.biz.controller");
            params.put("packageService", "com.jun.plugin.biz.service");
            params.put("packageServiceImpl", "com.jun.plugin.biz.service.impl");
            params.put("packageDao", "com.jun.plugin.biz.dao");
            params.put("packageMybatisXML", "com.jun.plugin.biz.model");
            params.put("packageModel", "com.jun.plugin.biz.model");

            // result
            Map<String, String> result = new HashMap<String, String>();

            result.put("controller_code", freemarkerTool.processString("code-generator/controller.ftl", params));
            result.put("service_code", freemarkerTool.processString("code-generator/service.ftl", params));
            result.put("service_impl_code", freemarkerTool.processString("code-generator/service_impl.ftl", params));

            result.put("dao_code", freemarkerTool.processString("code-generator/dao.ftl", params));
            result.put("mybatis_code", freemarkerTool.processString("code-generator/mybatis.ftl", params));
            result.put("model_code", freemarkerTool.processString("code-generator/model.ftl", params));

            // 计算,生成代码行数
            int lineNum = 0;
            for (Map.Entry<String, String> item : result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            logger.info("生成代码行数：{}", lineNum);

            return Result.ok(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new Result(500, "表结构解析失败");
        }

    }


    /**
     * 代码生成基础配置生成
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    //@Permission
    @GetMapping("/runDown")
    public void runDown(String tableSql, HttpServletResponse response) {

        try {
            tableSql = " CREATE  TABLE  `acountinfo`  (\n" +
                    "`user_id`  int(11)  NOT  NULL  AUTO_INCREMENT  COMMENT  '用户ID',\n" +
                    "`username`  varchar(255)  NOT  NULL  COMMENT  '用户名',\n" +
                    "`addtime`  datetime  NOT  NULL  COMMENT  '创建时间',\n" +
                    "PRIMARY  KEY  (`user_id`)\n" +
                    ")  ENGINE=InnoDB  DEFAULT  CHARSET=utf8  COMMENT='用户信息'  ";

            // parse table
            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(tableSql);
            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);
            // genProcessStringWriter(datas, result);
            params.put("packageController", "com.jun.plugin.biz.controller");
            params.put("packageService", "com.jun.plugin.biz.service");
            params.put("packageServiceImpl", "com.jun.plugin.biz.service.impl");
            params.put("packageDao", "com.jun.plugin.biz.dao");
            params.put("packageMybatisXML", "com.jun.plugin.biz.model");
            params.put("packageModel", "com.jun.plugin.biz.model");

            // result
            Map<String, String> result = new HashMap<String, String>();

            result.put("controller_code", freemarkerTool.processString("code-generator/controller.ftl", params));
            result.put("service_code", freemarkerTool.processString("code-generator/service.ftl", params));
            result.put("service_impl_code", freemarkerTool.processString("code-generator/service_impl.ftl", params));

            result.put("dao_code", freemarkerTool.processString("code-generator/dao.ftl", params));
            result.put("mybatis_code", freemarkerTool.processString("code-generator/mybatis.ftl", params));
            result.put("model_code", freemarkerTool.processString("code-generator/model.ftl", params));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            result.entrySet().stream().forEach(i -> {
                zipOutputStream(i.getValue(), i.getKey()+"/"+i.getKey()+".java", zipOutputStream);
            });
            IOUtils.closeQuietly(zipOutputStream);
            outputStream.toByteArray();
            try {
                byte[] bytes = outputStream.toByteArray();
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"CodeGenerator111.zip\"");
                response.addHeader("Content-Length", "" + bytes.length);
                response.setContentType("application/octet-stream; charset=UTF-8");
                IOUtils.write(bytes, response.getOutputStream());
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void zipOutputStream(String codeContent, String fileBaseName, ZipOutputStream zipOutputStream) {
        try {
            // 添加到zip
            zipOutputStream.putNextEntry(new ZipEntry(fileBaseName));
            IOUtils.write(codeContent, zipOutputStream, "UTF-8");
//            IOUtils.closeQuietly(sw);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
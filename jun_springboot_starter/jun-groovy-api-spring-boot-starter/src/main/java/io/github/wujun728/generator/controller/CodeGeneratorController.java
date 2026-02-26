package io.github.wujun728.generator.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.wujun728.generator.CodeUtil;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.sql.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 代码生成 REST 控制器
 * 提供表列表查询、代码在线生成、代码文件生成、代码打包下载等接口
 * 兼容 amis 前端：所有接口返回 {status: 0, msg: "ok", data: {...}} 格式
 */
@Slf4j
@RestController
@RequestMapping("${platform.path:}/generator")
public class CodeGeneratorController {

    /**
     * 获取数据库所有表名
     * GET /generator/tables?datasource=main
     */
    @GetMapping(path = "/tables", produces = "application/json")
    public Result tables(@RequestParam(value = "datasource", defaultValue = "main") String datasource) {
        try {
            DataSource ds = getDataSource(datasource);
            List<String> tableNames = MetaUtil.getTables(ds);
            return Result.success(tableNames);
        } catch (Exception e) {
            log.error("获取表列表失败", e);
            return Result.fail("获取表列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取表的元数据（字段信息）
     * GET /generator/classInfo?tableName=xxx&datasource=main
     */
    @GetMapping(path = "/classInfo", produces = "application/json")
    public Result classInfo(@RequestParam("tableName") String tableName,
                            @RequestParam(value = "datasource", defaultValue = "main") String datasource) {
        try {
            DataSource ds = getDataSource(datasource);
            ClassInfo classInfo = CodeUtil.getClassInfo(ds, tableName);
            if (classInfo == null) {
                return Result.fail("表 " + tableName + " 不存在或无字段信息");
            }
            return Result.success(classInfo);
        } catch (Exception e) {
            log.error("获取表元数据失败", e);
            return Result.fail("获取表元数据失败: " + e.getMessage());
        }
    }

    /**
     * 生成代码（返回内容，不写文件）
     * POST /generator/code
     * body: {tableName, templateGroup, packageName, authorName}
     * templateGroup: DB_RECORD / MYBATIS_PLUS / MYBATIS / JPA / BEETLSQL / JDBCTEMPLATE / UI / UTIL
     */
    @PostMapping(path = "/code", produces = "application/json")
    public Result generateCode(@RequestBody Map<String, Object> body) {
        try {
            String tableName = MapUtil.getStr(body, "tableName");
            String templateGroup = MapUtil.getStr(body, "templateGroup", "DB_RECORD");
            String pkgName = MapUtil.getStr(body, "packageName");
            String author = MapUtil.getStr(body, "authorName");
            String datasource = MapUtil.getStr(body, "datasource", "main");

            if (StrUtil.isBlank(tableName)) {
                return Result.fail("tableName 不能为空");
            }

            if (StrUtil.isNotBlank(pkgName)) {
                CodeUtil.customeConfig.put(CodeUtil.packageName, pkgName);
            }
            if (StrUtil.isNotBlank(author)) {
                CodeUtil.customeConfig.put(CodeUtil.authorName, author);
            }

            DataSource ds = getDataSource(datasource);
            List<String> templates = getTemplatesByGroup(templateGroup);
            Map<String, String> codeMap = CodeUtil.genCodeReturnMap(ds, tableName, templates);
            return Result.success(codeMap);
        } catch (Exception e) {
            log.error("代码生成失败", e);
            return Result.fail("代码生成失败: " + e.getMessage());
        }
    }

    /**
     * 生成代码文件到指定目录
     * POST /generator/code/file
     * body: {tableName, templateGroup, packageName, authorName, outputDir, datasource}
     */
    @PostMapping(path = "/code/file", produces = "application/json")
    public Result generateCodeFile(@RequestBody Map<String, Object> body) {
        try {
            String tableName = MapUtil.getStr(body, "tableName");
            String templateGroup = MapUtil.getStr(body, "templateGroup", "DB_RECORD");
            String outputDir = MapUtil.getStr(body, "outputDir");
            String pkgName = MapUtil.getStr(body, "packageName");
            String author = MapUtil.getStr(body, "authorName");
            String datasource = MapUtil.getStr(body, "datasource", "main");

            if (StrUtil.isBlank(tableName) || StrUtil.isBlank(outputDir)) {
                return Result.fail("tableName 和 outputDir 不能为空");
            }

            if (StrUtil.isNotBlank(pkgName)) {
                CodeUtil.customeConfig.put(CodeUtil.packageName, pkgName);
            }
            if (StrUtil.isNotBlank(author)) {
                CodeUtil.customeConfig.put(CodeUtil.authorName, author);
            }

            DataSource ds = getDataSource(datasource);
            List<String> templates = getTemplatesByGroup(templateGroup);
            CodeUtil.genCodeFile(ds, tableName, outputDir, templates);
            return Result.success("代码文件已生成到: " + outputDir);
        } catch (Exception e) {
            log.error("代码文件生成失败", e);
            return Result.fail("代码文件生成失败: " + e.getMessage());
        }
    }

    /**
     * 生成代码并打包下载
     * POST /generator/code/download
     * body: {tableName, templateGroup, packageName, authorName, datasource}
     */
    @PostMapping(path = "/code/download")
    public void downloadCode(@RequestBody Map<String, Object> body,
                             HttpServletResponse response) {
        try {
            String tableName = MapUtil.getStr(body, "tableName");
            String templateGroup = MapUtil.getStr(body, "templateGroup", "DB_RECORD");
            String pkgName = MapUtil.getStr(body, "packageName");
            String author = MapUtil.getStr(body, "authorName");
            String datasource = MapUtil.getStr(body, "datasource", "main");

            if (StrUtil.isBlank(tableName)) {
                response.setStatus(400);
                response.getWriter().write("{\"status\":500,\"msg\":\"tableName is required\"}");
                return;
            }

            if (StrUtil.isNotBlank(pkgName)) {
                CodeUtil.customeConfig.put(CodeUtil.packageName, pkgName);
            }
            if (StrUtil.isNotBlank(author)) {
                CodeUtil.customeConfig.put(CodeUtil.authorName, author);
            }

            DataSource ds = getDataSource(datasource);
            List<String> templates = getTemplatesByGroup(templateGroup);
            Map<String, String> codeMap = CodeUtil.genCodeReturnMap(ds, tableName, templates);

            // Write code to temp directory
            File tempDir = FileUtil.createTempFile("codegen_", "", null, true);
            FileUtil.del(tempDir);
            FileUtil.mkdir(tempDir);
            for (Map.Entry<String, String> entry : codeMap.entrySet()) {
                String fileName = entry.getKey().replace("/", "_").replace("${classInfo.className}", tableName);
                if (fileName.endsWith(".ftl")) {
                    fileName = fileName.substring(0, fileName.length() - 4);
                }
                File codeFile = new File(tempDir, fileName);
                FileUtil.writeString(entry.getValue(), codeFile, StandardCharsets.UTF_8);
            }

            // Zip and download
            File zipFile = ZipUtil.zip(tempDir);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + "_code.zip\"");
            try (InputStream is = new FileInputStream(zipFile);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }

            // Cleanup temp files
            FileUtil.del(tempDir);
            FileUtil.del(zipFile);
        } catch (Exception e) {
            log.error("代码下载失败", e);
            try {
                response.setStatus(500);
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":500,\"msg\":\"" + e.getMessage() + "\"}");
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 获取可用的模板组列表
     * GET /generator/templateGroups
     */
    @GetMapping(path = "/templateGroups", produces = "application/json")
    public Result templateGroups() {
        List<String> groups = Arrays.asList(
                "DB_RECORD", "MYBATIS_PLUS", "MYBATIS", "JPA",
                "BEETLSQL", "JDBCTEMPLATE", "UI", "UTIL",
                "MYBATIS_PLUS_NO1"
        );
        return Result.success(groups);
    }

    private DataSource getDataSource(String datasource) {
        if ("main".equals(datasource) || StrUtil.isBlank(datasource)) {
            return SpringUtil.getBean(DataSource.class);
        }
        try {
            return io.github.wujun728.sql.DataSourcePool.get(datasource);
        } catch (Exception e) {
            return SpringUtil.getBean(DataSource.class);
        }
    }

    private List<String> getTemplatesByGroup(String group) {
        if (group == null) {
            return CodeUtil.GROUP_DB_RECORD;
        }
        switch (group.toUpperCase()) {
            case "DB_RECORD":
                return CodeUtil.GROUP_DB_RECORD;
            case "MYBATIS_PLUS":
                return CodeUtil.GROUP_MYBATISPLUS;
            case "MYBATIS":
                return CodeUtil.GROUP_MYBATIS;
            case "JPA":
                return CodeUtil.GROUP_JPA;
            case "BEETLSQL":
                return CodeUtil.GROUP_BEETLSQL;
            case "JDBCTEMPLATE":
                return CodeUtil.GROUP_JDBCTEMPLATE;
            case "UI":
                return CodeUtil.GROUP_UI;
            case "UTIL":
                return CodeUtil.GROUP_UTIL;
            case "MYBATIS_PLUS_NO1":
                return CodeUtil.GROUP_MYBATIS_PLUG_NO1;
            default:
                return CodeUtil.GROUP_DB_RECORD;
        }
    }
}

package com.ruoyi.generate.controller;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.generate.pojo.GenTable;
import com.ruoyi.generate.pojo.GenTableColumn;
import com.ruoyi.generate.pojo.dto.Result;
import com.ruoyi.generate.service.IGenTableColumnService;
import com.ruoyi.generate.service.IGenTableService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 */
@RequestMapping("/gen")
@RestController
public class GenController extends BaseController {
    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;


    /**
     * 查询代码生成列表
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:list')")
    @GetMapping("/list")
    public TableDataInfo genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 修改代码生成业务
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:query')")
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable Long id) {
        GenTable table = genTableService.selectGenTableById(id);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return Result.ok().data(map);
    }

    /**
     * 查询数据库列表
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:list')")
    @GetMapping("/db/list")
    public TableDataInfo dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 查询数据表字段列表
     */
    @GetMapping(value = "/column/{id}")
    public TableDataInfo columnList(Long id) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(id);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:import')")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public Result importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return Result.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return Result.ok();
    }

    /**
     * 删除代码生成
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:remove')")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        genTableService.deleteGenTableByIds(ids);
        return Result.ok();
    }

    /**
     * 预览代码
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:preview')")
    @GetMapping("/preview/{id}")
    public Result preview(@PathVariable("id") Long id) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(id);
        return Result.ok().data(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public Result genCode(@PathVariable("tableName") String tableName) {
        genTableService.generatorCode(tableName);
        return Result.ok();
    }

    /**
     * 同步数据库
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableName}")
    public Result synchDb(@PathVariable("tableName") String tableName) {
        genTableService.synchDb(tableName);
        return Result.ok();
    }

    /**
     * 批量生成代码
     */
    @PreAuthorize("@xy.hasAuthority('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}

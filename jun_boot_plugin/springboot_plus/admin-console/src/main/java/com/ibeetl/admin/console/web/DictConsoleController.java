package com.ibeetl.admin.console.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.engine.PageQuery;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReadMessage;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.console.service.DictConsoleService;
import com.ibeetl.admin.console.web.dto.DictExcelImportData;
import com.ibeetl.admin.console.web.query.CoreDictQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.file.FileItem;
import com.ibeetl.admin.core.file.FileService;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.ConvertUtil;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.web.JsonResult;

/**
 * CoreDict 接口
 */
@Controller
public class DictConsoleController{

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/admin/dict";


    @Autowired private DictConsoleService dictService;
    @Autowired
    FileService fileService;
    
    @Autowired
    CorePlatformService platformService = null;
    /* 页面 */

    @GetMapping(MODEL + "/index.do")
    @Function("dict.query")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/admin/dict/index.html") ;
        view.addObject("search", CoreDictQuery.class.getName());
        return view;
    }

    @GetMapping(MODEL + "/edit.do")
    @Function("dict.edit")
    public ModelAndView edit(Long id) {
        ModelAndView view = new ModelAndView("/admin/dict/edit.html");
        CoreDict dict = dictService.queryById(id);
        view.addObject("dict", dict);
        return view;
    }

    @GetMapping(MODEL + "/add.do")
    @Function("dict.add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("/admin/dict/add.html");
        return view;
    }

    /* ajax json */

    @PostMapping(MODEL + "/list.json")
    @Function("dict.query")
    @ResponseBody
    public JsonResult<PageQuery> list(CoreDictQuery condtion)
    {
        PageQuery page = condtion.getPageQuery();
        dictService.queryByCondition(page);
        return JsonResult.success(page);
    }

    @PostMapping(MODEL + "/add.json")
    @Function("dict.add")
    @ResponseBody
    public JsonResult add(@Validated(ValidateConfig.ADD.class)CoreDict dict)
    {
        dict.setCreateTime(new Date());
        dictService.save(dict);
        platformService.clearDictCache();
        return new JsonResult().success();
    }

    @PostMapping(MODEL + "/update.json")
    @Function("dict.update")
    @ResponseBody
    public JsonResult<String> update(@Validated(ValidateConfig.UPDATE.class)  CoreDict dict) {
        boolean success = dictService.update(dict);
        if (success) {
        	platformService.clearDictCache();
            return new JsonResult().success();
        } else {
            return JsonResult.failMessage("保存失败");
        }
    }


   
    @GetMapping(MODEL + "/view.json")
    @Function("dict.query")
    @ResponseBody
    public JsonResult<CoreDict>queryInfo(Long id) {
        CoreDict dict = dictService.queryById(id);
        return  JsonResult.success(dict);
    }
    

    @PostMapping(MODEL + "/delete.json")
    @Function("dict.delete")
    @ResponseBody
    public JsonResult delete(String ids) {
    	List<Long> dels = ConvertUtil.str2longs(ids);
        dictService.batchDelCoreDict(dels);
        platformService.clearDictCache();
        return new JsonResult().success();
    }
    
    @PostMapping(MODEL + "/excel/export.json")
    @Function("dict.export")
    @ResponseBody
    public JsonResult<String> export(HttpServletResponse response,CoreDictQuery condtion) {
        String excelTemplate ="excelTemplates/admin/dict/dict_collection_template.xls";
        PageQuery<CoreUser> page = condtion.getPageQuery();
        //取出全部符合条件的
        page.setPageSize(Integer.MAX_VALUE);
        page.setPageNumber(1);
        page.setTotalRow(Integer.MAX_VALUE);
        List<CoreDict> dicts =dictService.queryExcel(page);
        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(excelTemplate)) {
            if(is==null) {
                throw new PlatformException("模板资源不存在："+excelTemplate);
            }
            FileItem item = fileService.createFileTemp("dict_collection.xls");
            OutputStream os = item.openOutpuStream();
            Context context = new Context();
            context.putVar("dicts", dicts);
            JxlsHelper.getInstance().processTemplate(is, os, context);
            os.close();
            //下载参考FileSystemContorller
            return  JsonResult.success(item.getPath());
        } catch (IOException e) {
            throw new PlatformException(e.getMessage());
        }
        
    }
    
    @PostMapping(MODEL + "/excel/import.do")
    @Function("dict.import")
    @ResponseBody
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
           return JsonResult.fail();
        }
        InputStream ins = file.getInputStream();
        
        InputStream inputXML = Thread.currentThread().getContextClassLoader().getResourceAsStream("excelTemplates/admin/dict/dict_mapping.xml");  
        XLSReader mainReader = ReaderBuilder.buildFromXML( inputXML );  
        InputStream inputXLS = ins;  
        List<DictExcelImportData> dicts = new ArrayList<DictExcelImportData>();  
        Map beans = new HashMap();  
        beans.put("list", dicts);
        ReaderConfig.getInstance().setSkipErrors( true );
        XLSReadStatus readStatus = mainReader.read( inputXLS, beans); 
        List<XLSReadMessage>  errors = readStatus.getReadMessages();
        if(!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(XLSReadMessage msg:errors) {
                sb.append(parseXLSReadMessage(msg));
                sb.append(",");
            }
            sb.setLength(sb.length()-1);
            return JsonResult.failMessage("解析excel出错:"+sb.toString());
        }
//        this.dictService.batchInsert(dicts);//layui对话框不能正确处理http 500错误，改成下面方式
//        return JsonResult.success();
        try {
            this.dictService.batchInsert(dicts);
            return JsonResult.success();
        }catch(Exception ex) {
            return JsonResult.failMessage(ex.getMessage());
        }
        
    }
    
    /*xlsReader 设计有问题，还需要通过解析msg知道错误位置在哪里*/
    private String parseXLSReadMessage(XLSReadMessage msg) {
//        String message = "Can't read cell " + getCellName(mapping, rowShift) + " on " + cursor.getSheetName() + " spreadsheet";
        String str = msg.getMessage();
        int start = "Can't read cell ".length();
        int end = str.indexOf("on");
        return str.substring(start,end);
    }

}

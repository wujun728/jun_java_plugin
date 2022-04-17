package com.dcits.tool.resume;

import com.dcits.tool.resume.tool.ExcelUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportMain {
    static File mappingFile=new File(".","映射关系.xlsx");
    static File dataFile=new File(".","人员清单.xlsx");
    static File templateFile=new File(".","简历模板.docx");
    static File exportFile=new File(".","简历.docx");

    public static void main(String[] args) {
        String mappingPath="C:\\Users\\yanwlb\\Desktop\\task\\mapping.xlsx";
        mappingFile=new File(mappingPath);
        List<Map<String,String>> mapping= ExcelUtil.readExcelMapping(mappingFile.getAbsolutePath(),0);
        System.out.println("解析映射表完成......");
        Map<String,String> mappingHead=mapping.get(0);
        String path="C:\\Users\\yanwlb\\Desktop\\task\\人员清单样例.xlsx";
        dataFile=new File(path);
        final List<Map<String,Object>> data= ExcelUtil.readExcel(dataFile.getAbsolutePath(),0,mappingHead);
        if(data==null || data.size()==0){
            System.out.println("没有数据可生成，请检查人员清单数据......");
            return;

        }
        System.out.println("检测发现"+data.size()+"条职员信息");
        for(int i=1;i<=data.size();i++){
            int index=i-1;
            Map<String,Object> item=data.get(index);
            String desc= (String) item.get("desc");
            item.put("experience",desc);
            item.put("breakPage",i<data.size());
        }


        Configure config = Configure.newBuilder()
                .bind("breakPage",new BreakPagePolicy())
                       .bind("experience",new ExperiencePolicy())
                .build();
        String modulePath="C:\\Users\\yanwlb\\Desktop\\task\\resume-module.docx";
        templateFile=new File(modulePath);
        XWPFTemplate template = XWPFTemplate.compile(templateFile.getAbsolutePath(), config).render(
                new HashMap<String, Object>() {{
                    put("resume",data);

                }}
        );
        System.out.println("解析导出简历模板样式完成.........");
        try {
            //生成简历
            String exportPath="C:\\Users\\yanwlb\\Desktop\\task\\简历template.docx";
            exportFile=new File(exportPath);
            FileOutputStream out = new FileOutputStream(exportFile.getAbsolutePath());//要导出的文件名
            template.writeAndClose(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("导出简历模板完成");
    }


}

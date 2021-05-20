/*
 * Copyright 2018 kiwipeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.report;

import cn.kiwipeach.demo.SpringJunitBase;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 快速生成报表案例
 *
 * @author Wujun
 * @create 2018/07/24
 */
public class JaspersoftReportTest extends SpringJunitBase {

    @Autowired
    private DataSource dataSource;

    /**
     * 注意点:jasperstudio-all-fonts 中文字体为
     */
    @Test
    public void testJdbcPrint() throws SQLException, JRException {
        //1.获取表模数据
        Connection connection = dataSource.getConnection();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("QrCode", "1099501218@qq.com");
        //2.获取模板文件
        String sourceFile = "jasperstudio/base-report.jrxml";
        InputStream resourceAsStream = JaspersoftReportTest.class.getClassLoader().getResourceAsStream(sourceFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(resourceAsStream);
        //3.填充数据，生成打印文件
        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,connection);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource(1));
        //4.生成PDF文件
        // 暂且放在项目路径下面
        String output_url = "ireport_out/base-report.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, output_url);
        //5.生成html文件
        //JasperExportManager.exportReportToHtmlFile(jasperPrint, "ireport_out/hello-report.html");
    }

    @Test
    public void testJavaBeanPrint() throws JRException {
        //1.获取表模数据
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("empNo", "4444");
        String filePath = "jasperstudio/font-style-demo.jrxml";
        //String filePath = "jasperstudio/font-style-demo.jasper";
        InputStream resourceAsStream = JaspersoftReportTest.class.getClassLoader().getResourceAsStream(filePath);
        //2.封装成JasperPrint打印对象
        JasperPrint jasperPrint = null;
        //若果为jrxml，则需要先编译成JasperReport对象，然后在获取JasperPrint输出对象
        if (filePath.endsWith(".jrxml")) {
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceAsStream);
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        } else if (filePath.endsWith(".jasper")) {
            //如果为jasper,则直接获取JasperPrint对象
            jasperPrint = JasperFillManager.fillReport(resourceAsStream, params, new JREmptyDataSource());
        } else {
            System.out.println("文件名不合法");
        }
        //3.创建pdf对象
        JRPdfExporter exporter = new JRPdfExporter();
        //4.设置输入项，封装JasperPrint
        ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
        exporter.setExporterInput(exporterInput);
        //5.设置输出项,设置出处文件
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("font-style-demo.pdf");
        exporter.setExporterOutput(exporterOutput);
        //6.其他输出属性设置
        exporter.exportReport();
    }
}

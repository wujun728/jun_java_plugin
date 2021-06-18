/*
 * Copyright 2017 KiWiPeach.
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
package cn.kiwipeach.demo.controller;

import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.factory.BeanListDemoFactory;
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.math.BigDecimal;

/**
 * Create Date: 2018/01/08
 * Description: Demo控制器
 *
 * @author Wujun
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);


    @Autowired
    private EmployService employService;


    @RequestMapping(value = "toEmpDetail", method = RequestMethod.GET)
    public String toEmployDetailPage(@RequestParam("empno") String empno, Model model) {
        logger.debug("DemoController控制器接受入参:{}", empno);
        Employ employ = employService.queryEmploy(new BigDecimal(empno));
        logger.debug("DemoController控制器返回出参:{}", JSON.toJSONString(employ));
        model.addAttribute("employ", employ);
        logger.debug("DemoController控制器跳转视图:{}", "emp_detail");
        return "emp_detail";
    }

    @RequestMapping(value = "queryEmpDetail", method = RequestMethod.GET)
    @ResponseBody
    public Employ queryDetailData(@RequestParam("empno") String empno, Model model) {
        logger.debug("DemoController控制器接受入参:{}", empno);
        Employ employ = employService.queryEmploy(new BigDecimal(empno));
        logger.debug("DemoController控制器返回出参:{}", JSON.toJSONString(employ));
        model.addAttribute("employ", employ);
        logger.debug("DemoController控制器跳转视图:{}", "emp_detail");
        return employ;
    }

    /**
     * 基本的报表组件
     */
    @RequestMapping(value = "report/base", method = RequestMethod.GET)
    public String baseREport(Model model) {
        model.addAttribute("jrDatasource", new JREmptyDataSource(1));
        model.addAttribute("format", "pdf");
        model.addAttribute("isDownload", "true");
        model.addAttribute("attachmentFileName", "test.pdf");
        return "base-report";
    }


    @RequestMapping(value = "report/javabean", method = RequestMethod.GET)
    public String javaBeanReport(Model model) {
//        报表相关设计内容，这篇博客不错：http://blog.csdn.net/xht555/article/details/50434772
        JRDataSource empJrDataSource = new JRBeanCollectionDataSource(BeanListDemoFactory.createBeanListCollection());
//        Map<String, Object> parameterMap = new HashMap<String, Object>();
//        parameterMap.put("jrDatasource", empJrDataSource);
//        必须指定format格式，告诉视图控制器该渲染成何种视图
//        parameterMap.put("format", "pdf");
//        ModelAndView modelAndView = new ModelAndView("report-provice-city", parameterMap);
//        报表数据源，在视图控制器中指定了
        model.addAttribute("jrDatasource", empJrDataSource);
        model.addAttribute("format", "pdf");
        return "report-provice-city";
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 1.其他通过输出流重写到页面可以参照：http://blog.csdn.net/tzdwsy/article/details/50595313
     * 2.这里只提供更加简洁的操作，酷炫！
     *
     * @param empNo  员工编号
     * @param format 格式
     * @param model  模型
     * @return 返回jrxml视图
     * @throws Exception 视图解析异常
     */
        @RequestMapping(value = "report/jdbc/{fileName}", method = RequestMethod.GET)
    public String jdbcReport2(
            @RequestParam(value = "empNo", required = true) Integer empNo,
            @RequestParam(value = "format", required = true, defaultValue = "pdf") final String format,
            HttpServletResponse response,
            final Model model
    ) throws Exception {
        /**
         his.formatMappings.put("csv", JasperReportsCsvView.class);
         this.formatMappings.put("html", JasperReportsHtmlView.class);
         this.formatMappings.put("pdf", JasperReportsPdfView.class);
         this.formatMappings.put("xls", JasperReportsXlsView.class);
         this.formatMappings.put("xlsx", JasperReportsXlsxView.class);
         */
        //报表数据源，在视图控制器中指定了
        model.addAttribute("empNo", empNo);
        model.addAttribute("format", format);
        model.addAttribute("jrDatasource", dataSource);
        return "jdbc-report";
    }

    @RequestMapping(value = "report/chinese-font/{fileName}", method = RequestMethod.GET)
    public String chineseFontReport(
            @RequestParam(value = "format", required = true, defaultValue = "pdf") final String format,
            final Model model
    ) throws Exception {
        model.addAttribute("format", format);
        model.addAttribute("jrDatasource", new JREmptyDataSource(1));
        return "chinese-font-report";
    }

    /**
     * Html视图是需要改地址解析图片请求路径
     * @param image 图片参数名
     * @return 直接访问图片位置信息
     */
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public String showJasperIcons(@RequestParam("image") String image) {
        System.out.println("imaeg show " + image);
        return "redirect:/images/jasper/icons/" + image + ".png";
    }


}

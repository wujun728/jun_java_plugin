package com.chentongwei.controller.common;

import com.chentongwei.core.common.biz.InitRegionBiz;
import com.chentongwei.core.system.biz.report.InitReportTypeBiz;
import com.chentongwei.core.tucao.biz.catalog.InitArticleCatalogBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 初始化数据的控制器。容器启动时自动执行的方法，无需开发者手动调用。
 */
@RestController
@RequestMapping("/common/initData")
public class InitDataController {

    @Autowired
    private InitArticleCatalogBiz initArticleCatalogBiz;
    @Autowired
    private InitRegionBiz initRegionBiz;
    @Autowired
    private InitReportTypeBiz initReportTypeBiz;

    /**
     * 将省市区下拉框放到redis的baseCache中
     *
     * @return
     */
    @PostConstruct
    public void initRegion() {
        initRegionBiz.initRegion();
    }

    /**
     * 将举报类型列表放到redis的baseCache中
     */
    @PostConstruct
    public void initReportType() {
        initReportTypeBiz.initReportType();
    }

    /**
     * 将吐槽分类列表放到redis的baseCache中
     */
    @PostConstruct
    public void initTucaoCatalog() {
        initArticleCatalogBiz.initCatalog();
    }

}

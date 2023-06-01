package com.cosmoplat.controller.sys;

import com.alibaba.fastjson.JSON;
import com.cosmoplat.common.config.GlobalExcelDictHandler;
import com.cosmoplat.common.utils.ExcelUtils;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.service.sys.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/11/10
 * Description: No Description
 */
@Slf4j
public class ExcelExampleController {

    @Resource
    private UserService userService;

    /**
     * 导出， 暂不使用
     */
    @GetMapping("/user/export")
    public void export(SysUser vo, HttpServletResponse httpServletResponse) {
        List<SysUser> list = userService.listAll(vo);
        ExcelUtils.exportExcel(list, "用户信息", "用户信息", SysUser.class, "用户信息.xls"
                , new GlobalExcelDictHandler(), httpServletResponse);
    }

    /**
     * 导入， 暂不使用
     */
    @PostMapping("/importExcel")
    public void importExcel(@RequestParam(required = false) MultipartFile file) {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            //INFO 应该通过获取文件名称来判断
        }
        List<SysUser> list = ExcelUtils.importExcel(file, 1, 1, SysUser.class);
        log.info(JSON.toJSONString(list));
    }
}

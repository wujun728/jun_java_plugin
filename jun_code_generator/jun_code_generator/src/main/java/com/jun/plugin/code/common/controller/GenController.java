package com.jun.plugin.code.common.controller;

import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jun.plugin.code.common.service.IGenService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@RequestMapping("/gen")
public class GenController {
    @Autowired
    private IGenService genService;
    /**
     * http://192.168.199.101:8011/generator/gen/genCode/area
     * @param response
     * @param tableName
     * @throws IOException
     */
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genService.generatorCode(tableName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\""+tableName+".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}

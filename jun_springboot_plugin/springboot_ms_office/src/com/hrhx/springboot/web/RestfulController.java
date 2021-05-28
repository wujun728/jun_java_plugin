package com.hrhx.springboot.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hrhx.springboot.util.IdGen;
import com.hrhx.springboot.util.Jacob2Html;
import com.hrhx.springboot.util.Jacob2PDF;

import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value="/office")
public class RestfulController { 
	
	@Autowired 
	private HttpServletRequest request;
	
	@Value("${web.upload-path}")
    private String path;
	
    @ApiOperation(value="Office(Word/Excel/PPT)转成HTML", notes="支持Office2003以及Office2007和Office2010")
    @RequestMapping(value="html", method=RequestMethod.GET) 
    public String html(String officePath) {
    	init();	
    	String uuid = IdGen.uuid();
    	String htmlPath = uuid+".html";  
    	Jacob2Html.convert2Html(officePath,path+htmlPath);
        return htmlPath; 
    } 
   
    
    @ApiOperation(value="Office(Word/Excel/PPT)转成PDF", notes="支持Office2003以及Office2007和Office2010")
    @RequestMapping(value="pdf", method=RequestMethod.GET) 
    public String pdf(String officePath) {
    	init();	
    	String uuid = IdGen.uuid();
    	String pdfPath = uuid+".pdf";
    	Jacob2PDF.convert2PDF(officePath,path+pdfPath);
        return pdfPath; 
    } 
    
    private void init(){
    	File file = new File(path);
    	if(!file.isDirectory()){
    		file.mkdirs();
    	}
    }
    
    @SuppressWarnings("unused")
	private String basePath(){
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	return basePath;
    }
    
} 
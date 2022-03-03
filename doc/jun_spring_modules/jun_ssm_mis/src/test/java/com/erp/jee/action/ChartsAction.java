package com.erp.jee.action;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.controller.BaseController;
import com.erp.page.Data;
import com.erp.page.Pie;
import com.jun.plugin.utils.FileUtil;

/**
 * Charts Action
 */
//@Action(value = "chartsAction", results = { @Result(name = "demoline", location = "/sun/test/demoline.jsp"),
//											@Result(name = "demopie", location = "/sun/test/demopie.jsp")})
@Controller
@RequestMapping("/chartsAction.do")
public class ChartsAction extends BaseController {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> charData = new HashMap<String, Object>();
	
	@RequestMapping(params = "goLine")
	public String goLine(Model model, HttpServletRequest request, HttpServletResponse response){
		String xAxisCategories  = "['一月', '二月', '三月', '四月','五月','六月','七月','八月','九月','十月','十一月','十二月']";
//		String series = 
//			"["+
//			"{name: '中国',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]}, "+
//			"{name: '美国',data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}, "+
//			"{name: '德国',data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]}, "+
//			"{name: '英国',data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]}"+
//			"]";
		Data d = new Data();
		d.setName("中国");
		List<Double> list = new ArrayList<Double>();
		list.add(7.0);
		list.add(6.9);
		list.add(9.5);
		list.add(14.5);
		list.add(18.2);
		list.add(21.5);
		list.add(25.2);
		list.add(26.5);
		list.add(23.3);
		list.add(18.3);
		list.add(13.9);
		list.add(9.6);
		d.setData(list);
		Data d2 = new Data();
		d2.setName("美国");
		List<Double> list2 = new ArrayList<Double>();
		list2.add(-0.2);
		list2.add(0.8);
		list2.add(5.7);
		list2.add(11.3);
		list2.add(17.0);
		list2.add(22.0);
		list2.add(24.8);
		list2.add(24.1);
		list2.add(20.1);
		list2.add(14.1);
		list2.add(8.6);
		list2.add(2.5);
		d2.setData(list2);
		List<Data> datalist = new ArrayList<Data>();
		datalist.add(d);
		datalist.add(d2);
		charData = new HashMap<String, Object>();
		charData.put("xAxisCategories", xAxisCategories);
		charData.put("series", JSON.toJSONString(datalist));
		model.addAttribute("charData", charData);
//		OutputJson(charData, response);
		return "/sun/test/demoline";
	}
	
	@RequestMapping(params = "goPie")
	public String goPie(Model model, HttpServletRequest request, HttpServletResponse response){
		Data d = new Data();
		d.setName("访问数量统计比例");
		List<Pie> list = new ArrayList<Pie>();
		Pie pie1 = new Pie("中国",7.0);
		list.add(pie1);
		Pie pie2 = new Pie("美国",29.0);
		list.add(pie2);
		d.setData(list);
		List<Data> datalist = new ArrayList<Data>();
		datalist.add(d);
		charData = new HashMap<String, Object>();
		charData.put("series", JSON.toJSONString(datalist));
		model.addAttribute("charData", charData);
		return "/sun/test/demopie";
	}

	@RequestMapping(params = "export")
	public void export(Model model, HttpServletRequest request, HttpServletResponse response){
//			HttpServletRequest request = this.getRequest();
//			HttpServletResponse response = this.getResponse();
			ServletOutputStream out = null;
			try {
			  request.setCharacterEncoding("utf-8");
			  String filename = request.getParameter("filename");
			  String type = request.getParameter("type");
			  String svg = request.getParameter("svg");
		
			  out = response.getOutputStream();
			  if (null != type && null != svg){
			  svg = svg.replaceAll(":rect", "rect"); 
			  String ext = "";
			  if (type.equals("image/png")) {
			     ext = "png";
			     
			  } else if (type.equals("image/jpeg")) {
			     ext = "jpg";

			  } else if (type.equals("application/pdf")) {
			     ext = "pdf";

			  } else if (type.equals("image/svg+xml")) {
			     ext = "svg";   
			  }
			  String agent = request.getHeader("USER-AGENT");
			  response.setCharacterEncoding("utf-8");
			  response.addHeader("Content-Disposition", "attachment; filename="+FileUtil.encodeDownloadFileName(filename, agent)+"."+ext);
			  response.addHeader("Content-Type", type);
			     if (ext == "svg"){
			        OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
			    	writer.append(svg);
			    	writer.close();
			     } else {
			        out.print("Invalid type: " + type);
			     }
			  } else {
			     response.addHeader("Content-Type", "text/html");
			     out.println("Usage:\n\tParameter [svg]: The DOM Element to be converted.\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
			  }
			  out.flush();
			  out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public Map<String, Object> getCharData() {
//		return charData;
//	}
//
//	public void setCharData(Map<String, Object> charData) {
//		this.charData = charData;
//	}
	
}

package spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import spring_mvc.model.ajaxtest;
import spring_mvc.model.legend;
import spring_mvc.model.series;
import spring_mvc.model.title;
import spring_mvc.model.tooltip;
import spring_mvc.model.xAxis;
import spring_mvc.model.yAxis;

@Controller
public class AjaxController {
	
	@RequestMapping(value="/ajax",method=RequestMethod.GET)
	public  String ajax(){
		return "ajax";
	}
	@ResponseBody
	@RequestMapping(value="/ajax",method=RequestMethod.POST)
	public ajaxtest json(){
		ajaxtest at=new ajaxtest(new title("ECharts 入门示例"),new tooltip(),
				new legend(new String[]{"销量"})
				,new xAxis(new String[]{"衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"}),
				new yAxis(),new series[]{new series("销量","bar",new Integer[]{5, 25, 36, 10, 50, 20})});
		return at;
	}
	@RequestMapping(value="/qipaotu",method=RequestMethod.GET)
	public  String ajax2(){
		return "qipaotu";
	}
}

package boot.spring.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import boot.spring.pagemodel.ActorGrid;
import boot.spring.po.Actor;
import boot.spring.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "演员接口")
@Controller
public class ActorController {
	@Autowired
	private ActorService actorservice;
	
	private static final Logger LOG = LoggerFactory.getLogger(ActorController.class);
	
	@ApiOperation("获取所有演员列表")
	@RequestMapping(value="/actors",method = RequestMethod.GET)
	@ResponseBody
	public ActorGrid getactorlist(@RequestParam(value="current") int current,@RequestParam(value="rowCount") int rowCount){
		int total=actorservice.getactornum();
		List<Actor> list=actorservice.getpageActors(current,rowCount);
		ActorGrid grid=new ActorGrid();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(list);
		grid.setTotal(total);
		LOG.debug("获取所有演员列表");
		return grid;
	}
	
	@ApiOperation("修改一个演员")
	@RequestMapping(value="/actors",method = RequestMethod.PUT)
	@ResponseBody
	public Actor updateactor(@RequestBody Actor a){
		Actor actor=actorservice.updateactor(a);
		LOG.debug("修改一个演员");
		return actor;
	}
	
	@ApiOperation("获取一个演员")
	@RequestMapping(value="/actors/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Actor getactorbyid(@PathVariable("id") short id){
		Actor a=actorservice.getActorByid(id);
		LOG.debug("获取一个演员");
		return a;
	}
	
	@ApiOperation("添加一个演员")
	@RequestMapping(value="/actors",method = RequestMethod.POST)
	@ResponseBody
	public Actor add(@RequestBody Actor a){
		Actor actor=actorservice.addactor(a);
		LOG.debug("添加一个演员");
		return actor;
	}
	
	@ApiOperation("删除一个演员")
	@RequestMapping(value="/actors/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("id") String id){
		actorservice.delete(Short.valueOf(id));
		LOG.debug("删除一个演员");
		return "success";
	}
	
	@ApiOperation("把演员导出为Excel")
	@RequestMapping(value="/exportactor",method = RequestMethod.GET)
	public void export(HttpServletResponse response) throws Exception{
		InputStream is=actorservice.getInputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}
	
	@RequestMapping(value="/showactor",method = RequestMethod.GET)
	String showactor(){
		return "showactor";
	}
}

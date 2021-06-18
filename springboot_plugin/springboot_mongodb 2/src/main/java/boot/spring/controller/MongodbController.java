package boot.spring.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import boot.spring.pagemodel.DataGrid;
import boot.spring.po.Picture;
import boot.spring.service.PictureService;


@Controller
@RequestMapping(value="/mongodb")
public class MongodbController {
	
	@Resource(name="mongodbServiceImpl")
	private PictureService mongodbService;
	
	@RequestMapping(value="/pictures", method=RequestMethod.GET)
	@ResponseBody
	public DataGrid<Picture> getpiclist(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount
			,@RequestParam(required=false,value="sort[id]")String sortid
			,@RequestParam(required=false,value="searchPhrase")String searchPhrase){
		if(searchPhrase==null){
		List<Picture> list=mongodbService.getpiclist(current,rowCount,sortid);
		int total=mongodbService.getpicturenum();
		DataGrid<Picture> grid=new DataGrid<Picture>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(list);
		grid.setTotal(total);
		return grid;
		}else{
			List<Picture> list=mongodbService.getsearchresult(current, rowCount, sortid, searchPhrase);
			int total=mongodbService.getsearchresulttotal(searchPhrase);
			DataGrid<Picture> grid=new DataGrid<Picture>();
			grid.setCurrent(current);
			grid.setRowCount(rowCount);
			grid.setRows(list);
			grid.setTotal(total);
			return grid;
		}
	}
	
	@RequestMapping(value="/index")
	public String showpic(){
		return "mongodb";
	}
	
	@RequestMapping(value="picture/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public void deletepicture(@PathVariable("id")String id){
		mongodbService.deletePicture(id);
	}
	
	
	@RequestMapping(value="/picture/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Picture getpicture(@PathVariable("id")String id){
		Picture p=mongodbService.getPictureByid(id);
		return p;
	}
	//这个方法被新增和修改请求共用,如果是新增，id必须为空，否则是修改请求
	@RequestMapping(value="/picture",method=RequestMethod.POST)
	public String addpicture(@ModelAttribute("picture")Picture picture){
		mongodbService.SaveorUpdatePicture(picture);
		return "mongodb";
	}
	//创造的测试数据
	@RequestMapping(value="add",method=RequestMethod.GET)
	@ResponseBody
	public void ddd(){
		for(int i=0;i<10000;i++){
			Picture p=new Picture("w","java",(long)i);
			mongodbService.SaveorUpdatePicture(p);
		}
		
	}
}

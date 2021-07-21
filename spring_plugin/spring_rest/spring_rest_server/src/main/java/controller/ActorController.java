package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import po.Actor;
import po.File;
import po.MSG;
import service.ActorService;
import service.FileUploadService;

@Controller
public class ActorController {
	@Autowired
	private ActorService actorservice;
	
	@Autowired
	private FileUploadService fileuploadservice;
	
	@RequestMapping(value="/actors",method = RequestMethod.GET)
	@ResponseBody
	public MSG getactorlist(){
		List<Actor> list=actorservice.getActors();
		return  new MSG("200",list);
	}
	
	@RequestMapping(value="/actors/{id}",method = RequestMethod.PUT,consumes="application/json")
	@ResponseBody
	public Actor updateactor(@PathVariable("id") int id,@RequestBody Actor actor){
		actor.setId(id);
		Actor a=actorservice.UpdateActor(actor);
		return a;
	}
	
	@RequestMapping(value="/actors/{id}",method = RequestMethod.GET)
	@ResponseBody
	public MSG getactorbyid(@PathVariable("id") int id){
		Actor a=actorservice.getActorByid(id);
		return new MSG("200",a);
	}
	
	@RequestMapping(value="/actors",method = RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public Actor add(@RequestBody Actor actor){
			Actor a=actorservice.SaveActor(actor);
			return a;//a即为被保存好的对象，直接返回已经拥有新主键
	}
	
	@RequestMapping(value="/actors/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public MSG delete(@PathVariable("id") int id){
		actorservice.Delete(id);
		MSG msg=new MSG();
		msg.setStatus("200");
		return msg;
	}
	
	@RequestMapping(value="rest",method = RequestMethod.GET)
	public String rest(){
		return "rest";
	}
	
	@RequestMapping(value="/actors/upload",method = RequestMethod.POST)
	@ResponseBody
	public MSG addfile(@ModelAttribute File file,@RequestParam MultipartFile uploadfile,HttpServletRequest request){
		String filename=uploadfile.getOriginalFilename();
		String nowTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		String realfilename=nowTimeStamp+"."+StringUtils.getFilenameExtension(filename);
		String targetDir=request.getSession().getServletContext().getRealPath("uploadfiles");
		fileuploadservice.getUploadFile(uploadfile, targetDir, realfilename);
		file.setFilename(filename);
		file.setRealfilename(realfilename);
		actorservice.insertfile(file);
		return new MSG("upload success");
	}
}

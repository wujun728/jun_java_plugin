package com.ic911.htools.web.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ic911.htools.entity.example.Team;
import com.ic911.htools.service.example.TeamService;

@Controller
@RequestMapping("/example/team")
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		request.setAttribute("teams", teamService.findAll());
		return "/example/team/index";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Team team,HttpServletRequest request){
		if(team!=null){
			teamService.save(team);
		}
		return "redirect:/example/team/index.htm";
	}
	
}

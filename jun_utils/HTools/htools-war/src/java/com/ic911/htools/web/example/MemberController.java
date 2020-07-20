package com.ic911.htools.web.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ic911.htools.entity.example.Member;
import com.ic911.htools.service.example.MemberService;
import com.ic911.htools.service.example.TeamService;

@Controller
@RequestMapping("/example/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/page/{no}")
	public String page(@PathVariable int no,HttpServletRequest request) {
		PageRequest page = new PageRequest(no, 5,Sort.Direction.DESC,"id");
		request.setAttribute("page", memberService.findAll(page));
		request.setAttribute("teams", teamService.findAll());
		return "/example/member/index";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Member memeber,HttpServletRequest request){
		if(memeber!=null && memeber.getTeam()!=null && memeber.getTeam().getId()!=null){
			memeber.setTeam(teamService.findOne(memeber.getTeam().getId()));
			memberService.save(memeber);
		}
		return "redirect:/example/member/page/0";
	}
	
}

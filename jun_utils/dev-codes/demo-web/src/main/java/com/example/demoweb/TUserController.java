package com.example.demoweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.util.MapperUtil;

/**
 * Handles requests for the application home page.
 */
@RestController
public class TUserController {
	
    @Autowired
    private TUserMapper tUserMapper;
    
	@GetMapping("/")
	public Object home(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10")int pageSize) {
	    Query query = new Query().page(pageIndex, pageSize);
	    PageInfo<TUser> pageInfo = MapperUtil.query(this.tUserMapper, query);
		return pageInfo;
	}
	
}

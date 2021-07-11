package com.luo.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.luo.service.LuceneService;
import com.luo.vo.IndexModel;
import com.opensymphony.xwork2.ActionContext;


@Scope("prototype")
@Namespace("/lucene")
@Result(name="list",type="redirectAction",location="search.action")
public class LuceneAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String keyword;
	private String field;
	@Autowired
	private LuceneService luceneService;


	@Action(value="search",results={@Result(name=SUCCESS,location="/WEB-INF/content/luceneList.jsp")})
	@Override
	public String list() throws Exception {
		List<IndexModel> luceneList=luceneService.search(keyword);
		ActionContext.getContext().put("luceneList", luceneList);
		return SUCCESS;
	}	
	
	@Action(value="commitRamIndex")
	public String commitRamIndex() throws Exception {
		luceneService.commitRamIndex();
		return LIST;
	}
	
	@Action(value="commitDBIndex")
	public String commitDBIndex() throws Exception {
		luceneService.commitDBIndex();
		return LIST;
	}
	
	@Action(value="reCreCommitIndex")
	public String reCreCommitIndex() throws Exception {
		luceneService.updateReconstructorIndex();
		return LIST;
	}
	
	@Action(value="deleteIndex")
	public String deleteIndex() throws Exception {
		luceneService.deleteIndex();
		return LIST;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}

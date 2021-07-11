package com.luo.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.luo.service.SolrService;
import com.luo.vo.IndexModel;
import com.taiping.b2b2e.common.page.PageBean;

@Scope("prototype")
@Result(name="list",type="redirectAction",location="search.action")
public class SolrAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String keyword;
	private String field;
	@Autowired
	private SolrService solrService;
	private PageBean<IndexModel> page;
	private final Integer pageSize=5;
	private int pageIndex;
	
	
	/**
	 * 从索引中检索关键词
	 */
	@Action(value="search",results={@Result(name=SUCCESS,location="/WEB-INF/content/luceneList.jsp")})
	@Override
	public String list() throws Exception {
		page = solrService.findByIndex(keyword,field,pageIndex,pageSize);
		return SUCCESS;
	}

	
	@Action(value="commitRamIndex")
	public String commitRamIndex() throws Exception {
		solrService.commitRamIndex();
		return LIST;
	}
	
	@Action(value="commitDBIndex")
	public String commitDBIndex() throws Exception {
		solrService.commitDBIndex();
		return LIST;
	}
	
	@Action(value="reCreCommitIndex")
	public String reCreCommitIndex() throws Exception {
		solrService.updateReconstructorIndex();
		return LIST;
	}
	
	@Action(value="deleteIndex")
	public String deleteIndex() throws Exception {
		solrService.deleteIndex();
		return LIST;
	}

	//get set
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
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public PageBean<IndexModel> getPage() {
		return page;
	}
	public void setPage(PageBean<IndexModel> page) {
		this.page = page;
	}
}

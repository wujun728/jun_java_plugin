package io.github.wujun728.groovy.service;

import cn.hutool.core.lang.Console;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import io.github.wujun728.db.Db;
import io.github.wujun728.db.Record;
import io.github.wujun728.db.Page;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.groovy.common.model.ApiConfig;
import io.github.wujun728.rest.entity.ApiDataSource;
import io.github.wujun728.rest.entity.ApiSql;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils; 
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ApiService {

	@PostConstruct
	public void init(){
	}
	@SuppressWarnings("unchecked")
	public List<ApiConfig> queryApiConfigList() {
		List<Record> lists = Db.use("main").find("select * from "+"api_config"+" where status = 'ENABLE' ");
		// List<Map<String, Object>> lists = jdbcTemplate.queryForList("select * from  "+tablename+"  where status = 'ENABLE' ");
		List<ApiConfig> datas = RecordUtil.recordToBeanList(lists,ApiConfig.class);
		if(!CollectionUtils.isEmpty(datas)) {
			buildApiConifgSubApiSql(datas);
		}
		if(Boolean.TRUE.equals(Boolean.valueOf(SpringUtil.getProperty("project.runApi.enable")))){
			//log.info(JSON.toJSONString(datas));
			queryCountSql();
			queryDatasourceList();
			querySQLList("0");
			getDatasource("0");
		}
		return datas;
	}


	public Integer queryCountSql() {
		//Long aLong = jdbcTemplate.queryForObject("select count(*) from test ", Long.class);
		Integer count = Db.use("main").queryForInt("select count(*) from  "+"api_config"+"  ");
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<ApiDataSource> queryDatasourceList() {
		Page<Record> lists = Db.use("main").paginate(1,2,"select * "," from  "+"api_datasource"+"  where id <> ? ");
		//Console.log(JSON.toJSONString(lists));
		Console.log(JSON.toJSONString(RecordUtil.pageRecordToPageMap(lists,false)));

		String from = "from  "+"api_datasource"+"  where id > ?";
		String totalRowSql = "select count(*) " + from;
		String findSql = "select * " + from + " order by id";
		Db.paginate(1, 10, totalRowSql, findSql);
//		Db.paginate(1,10,findSql);

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> querySQLList(String apiId) {
		List<Record> lists = Db.use("main").find("select * from  "+"api_config"+"  ");
		// List<Map<String, Object>> lists = jdbcTemplate.queryForList("select * from api_sql where api_id = "+apiId);
		List<ApiSql> datas = RecordUtil.recordToBeanList(lists,ApiSql.class);
		List<Map>  datas2 = RecordUtil.recordToMaps(lists,false);
//		List<ApiSql> datas = RecordUtil.mapToBeans(lists,ApiSql.class);
		//log.info(JSON.toJSONString(datas));
		return datas2;
	}
	

	@SuppressWarnings("unchecked")
	public ApiDataSource getDatasource(String id) {
		ApiDataSource info = new ApiDataSource();
		Record record= Db.use("main").findById( "api_datasource" , id);
		return RecordUtil.recordToBean(record,ApiDataSource.class);
	}


	private static void buildApiConifgSubApiSql(List<ApiConfig> datas) {
		datas.stream().map(item->{
					List<ApiSql> sqlList = Lists.newArrayList();
					if("sql".equalsIgnoreCase(item.getScriptType())) {
						String sqls[] = item.getScriptContent().split(";");
						if(sqls.length>0) {
							for(String sql : sqls) {
								if(StringUtils.isEmpty(sql)) {
									continue;
								}
								ApiSql apisql = new ApiSql();
								apisql.setId(item.getId());
								apisql.setSqlText(sql);
								sqlList.add(apisql);
							}
						}
					}
					item.setSqlList(sqlList);

					return item;
				}
		).collect(Collectors.toList());
	}
	

}

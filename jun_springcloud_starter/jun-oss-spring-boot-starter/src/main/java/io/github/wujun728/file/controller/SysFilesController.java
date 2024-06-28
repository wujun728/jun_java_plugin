package io.github.wujun728.file.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import io.github.wujun728.common.Result;
import io.github.wujun728.file.config.FileProperties;
import io.github.wujun728.file.utils.FileUtils;
import io.github.wujun728.file.entity.SysFilesEntity;
import io.github.wujun728.file.service.SysFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@RestController
@RequestMapping("/sysFiles")
@Api(tags = "文件管理")
@Slf4j
public class SysFilesController {
	@Resource
	private SysFilesService sysFilesService;


	@Autowired
	FileProperties fileUploadProperties;

	@ApiOperation(value = "新增")
	@PostMapping("/upload")
	//@RequiresPermissions(value = { "sysFiles:add", "sysContent:update", "sysContent:add" }, logical = Logical.OR)
	public Result add(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "bizid",required = false) String bizid,
					  @RequestParam(value = "biztype",required = false) String biztype) {
		log.info(biztype);
		log.info(bizid);
		// 判断文件是否空
		if (file == null || file.getOriginalFilename() == null
				|| "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
			return Result.fail("文件为空");
		}
		return sysFilesService.saveFile(file, biztype, bizid);
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("/delete")
	//@RequiresPermissions("sysFiles:delete")
	public Result delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
		sysFilesService.removeByIdsAndFiles(ids);
		return Result.success();
	}

//	@ApiOperation(value = "查询分页数据")
//	@PostMapping("/listByPage")
//	//@RequiresPermissions("sysFiles:list")
//	public Result findListByPage(@RequestBody SysFilesEntity sysFiles) {
//		Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
//		IPage<SysFilesEntity> iPage = sysFilesService.page(page,
//				Wrappers.<SysFilesEntity>lambdaQuery().orderByDesc(SysFilesEntity::getCreateDate));
//		return Result.success(iPage);
//	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@ApiOperation(value = "查询分页数据")
//	@PostMapping("/listByPageUser")
//	//@RequiresPermissions("sysFiles:list")
//	public Result listByPageUser(@RequestBody SysFilesEntity sysFiles) {
//		Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
//		IPage<SysFilesEntity> iPage = sysFilesService.page(page,
//				Wrappers.<SysFilesEntity>lambdaQuery()
//				.eq(SysFilesEntity::getRefBizid, sysFiles.getRefBizid())
//				.eq(SysFilesEntity::getDictBiztype, sysFiles.getDictBiztype())
//				.orderByDesc(SysFilesEntity::getCreateDate));
//		return Result.success(iPage);
//	}




	@ApiOperation(value = "生成简历")
	@PostMapping("/genResume")
//	@RequiresPermissions(value = { "sysFiles:add", "sysContent:update", "sysContent:add" }, logical = Logical.OR)
	public Result genResume(@RequestParam(value = "bizid",required = false) String bizid,
							@RequestParam(value = "username",required = false) String username,
							@RequestParam(value = "realName",required = false) String realName,
							@RequestParam(value = "deptName",required = false) String deptName,
							@RequestParam(value = "biztype",required = false) String biztype) throws IOException {
		Map<String, Object> resume = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		org.springframework.core.io.Resource resource1 = resourceLoader.getResource("classpath:files/"+"logo_resume.jpg");
		data.put("pictures", new PictureRenderData(100, 120, resource1.getFile().getAbsolutePath()));
		//SysUser user = userMapper.getUserByName(username);
		Map m = null;//JSON.parseObject(JSON.toJSONString(user), Map.class);
		data.putAll(m);
		List<Map<String,Object>> list11=new ArrayList<>();
		System.err.println(JSON.toJSON(data));
		list11.add(data);
		resume.put("resume",list11);
		org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:files/"+"简历模板2.docx");
		InputStream inputStream =resource.getInputStream() ;
		XWPFTemplate template = XWPFTemplate.compile(inputStream).render( resume);
		String newFile = fileUploadProperties.getPath()+realName+"简历"+username+".docx";
		File file = new File(newFile);
		FileUtils.createFile(file);
		template.writeAndClose(new FileOutputStream(newFile));
		log.info(bizid);
		// 判断文件是否空
		if (file == null) {
			return Result.fail("文件为空");
		}
		return sysFilesService.saveFile(file, biztype, bizid);
	}



	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询分页数据")
	@PostMapping("/listByPage")
//	@RequiresPermissions("sysFiles:list")
	public Result findListByPage(@RequestBody SysFilesEntity sysFiles) {
		Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
		IPage<SysFilesEntity> iPage = sysFilesService.page(page,
				Wrappers.<SysFilesEntity>lambdaQuery().orderByDesc(SysFilesEntity::getCreateDate));
		List<SysFilesEntity> records = iPage.getRecords();
		String userid = "sessionService.getCurrentUsername()";
		records.forEach(item -> {
			if(userid.equalsIgnoreCase(item.getCreator())) {
				item.setIsOwner(1);
			}
		});
		return Result.success(iPage);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "查询分页数据")
	@PostMapping("/listByPageUser")
//	@RequiresPermissions("sysFiles:list")
	public Result listByPageUser(@RequestBody SysFilesEntity sysFiles) {
		Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
		IPage<SysFilesEntity> iPage = sysFilesService.page(page,
				Wrappers.<SysFilesEntity>lambdaQuery()
						.eq(SysFilesEntity::getRefBizid, sysFiles.getRefBizid())
//				.eq(SysFilesEntity::getDictBiztype, sysFiles.getDictBiztype())
						.orderByDesc(SysFilesEntity::getCreateDate));
		List<SysFilesEntity> records = iPage.getRecords();
		String userid = "sessionService.getCurrentUsername()";
		records.forEach(item -> {
			if(userid.equalsIgnoreCase(item.getCreator())) {
				item.setIsOwner(1);
			}
		});
		return Result.success(iPage);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "查询分页数据")
	@PostMapping("/listByPageUserProjectID")
	public Result listByPageUserProjectID(@RequestBody SysFilesEntity sysFiles) {
		String pid = sysFiles.getRefBizid();
		Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
		String tmpSQL = getProjectFilesRefIDS(pid);
		IPage<SysFilesEntity> iPage = sysFilesService.page(page,
				Wrappers.<SysFilesEntity>lambdaQuery()
//				.eq(SysFilesEntity::getRefBizid, sysFiles.getRefBizid())
						.inSql(true, SysFilesEntity::getRefBizid, tmpSQL )
//				.eq(SysFilesEntity::getRefBizid, sysFiles.getRefBizid())
						.orderByDesc(SysFilesEntity::getCreateDate));
		List<SysFilesEntity> records = iPage.getRecords();
		String userid = "sessionService.getCurrentUsername()";
		records.forEach(item -> {
			if(userid.equalsIgnoreCase(item.getCreator())) {
				item.setIsOwner(1);
			}
		});
		return Result.success(iPage);
	}

	@ApiOperation(value = "查询userLogo")
	@PostMapping("/getImgByBizid")
	public Result getImgByBizid(@RequestBody SysFilesEntity sysFiles) {
		LambdaQueryWrapper<SysFilesEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysFilesEntity::getDictBiztype,sysFiles.getDictBiztype());
		queryWrapper.eq(SysFilesEntity::getRefBizid,sysFiles.getRefBizid());
		queryWrapper.orderByDesc(SysFilesEntity::getCreateDate);
		queryWrapper.last(" limit 1 ");
		List<SysFilesEntity> list = sysFilesService.list(queryWrapper);
		String userid = "sessionService.getCurrentUsername()";
		if(list.size()>0){
			return Result.success(list.get(0));
		}else{
			return Result.success();
		}
	}


	private String getProjectFilesRefIDS(String pid) {
		String tmpSQL = " select  pjc.id -- ,pjc.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_contract pjc on t.project_code=pjc.refid_project_code_hide\r\n"
				+ "		where t.id='"+pid+"'  \r\n"
				+ "\r\n  union  SELECT '"+pid+"'  from pj_project"
				+ "		union \r\n"
				+ "		select  pjp.id -- ,pjp.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_plan pjp on t.project_code=pjp.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "\r\n"
				+ "		union  \r\n"
				+ "		select  pjd.id -- ,pjd.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_draft pjd on t.project_code=pjd.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "\r\n"
				+ "		union  \r\n"
				+ "		select  pjr.id -- ,pjr.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_recheck pjr on t.project_code=pjr.ref_pcode\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union  \r\n"
				+ "		select  pja.id -- ,pja.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_appraise pja on t.project_code=pja.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union  \r\n"
				+ "		select  pji.id -- ,pji.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_invoice pji on t.project_code=pji.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union  \r\n"
				+ "		select  pjm.id -- ,pjm.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_member pjm on t.project_code=pjm.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union  \r\n"
				+ "		select  pjt.id -- ,pjt.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_prodess_task pjt on t.project_code=pjt.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union \r\n"
				+ "		select  pjr1.id -- ,pjr1.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_report pjr1 on t.project_code=pjr1.ref_project_code\r\n"
				+ "		where t.id='"+pid+"' \r\n"
				+ "		union \r\n"
				+ "		select  pjrn.id -- ,pjrn.*\r\n"
				+ "				from pj_project t\r\n"
				+ "				left join pj_project_reportnumber pjrn on t.project_code=pjrn.ref_reportnumber_code\r\n"
				+ "		where t.id='"+pid+"'  ";
		return tmpSQL;
	}

}

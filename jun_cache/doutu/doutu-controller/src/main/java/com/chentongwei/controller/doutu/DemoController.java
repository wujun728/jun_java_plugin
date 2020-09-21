package com.chentongwei.controller.doutu;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chentongwei.cache.redis.IListCache;
import com.chentongwei.cache.redis.ISetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.entity.common.io.DownZIPIO;
import com.chentongwei.entity.doutu.io.DemoIO;
import com.chentongwei.entity.doutu.vo.DemoVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.service.doutu.IDemoService;
import com.chentongwei.util.QiniuUtil;
import com.github.pagehelper.PageHelper;

@RestController
@RequestMapping("/doutu/demo")
public class DemoController {
	
	@Resource
	private IDemoService demoService;
	@Resource
	private QiniuUtil qiniuUtil;
	@Resource
	private IListCache listCache;
	@Resource
	private ISetCache setCache;

	@RequestMapping("/getByID/{id}")
	public Result getByID(@PathVariable Integer id) {
		DemoVO demo = demoService.getByID(id);
		return ResultCreator.getSuccess(demo);
	}
	
	@RequestMapping("/list")
	public Result list(@Valid DemoIO demoIO) {
		PageHelper.startPage(demoIO.getPageNum(), demoIO.getPageSize());
		List<DemoVO> list = demoService.list(demoIO);
		return ResultCreator.getSuccess(list);
	}

	@RequestMapping("/testSlowLog")
	public Result testSlowLog() {
		demoService.testSlowLog();
		return ResultCreator.getSuccess();
	}

	@RequestMapping("/save")
	public Result save(DemoIO demoIO) {
		demoService.save(demoIO);
		return ResultCreator.getSuccess();
	}

	@RequestMapping("/testException")
	public void testException() {
		throw new BussinessException(ResponseEnum.UPLOAD_ERROR);
	}

	@RequestMapping(value = "/imgUpload")
	public Object uploadImgage(@RequestParam("file") MultipartFile file) throws IOException {
		JSONObject jsonObject = qiniuUtil.upload(file.getBytes(), "");
		return jsonObject;
	}
	
	@RequestMapping(value = "/getList")
	public Result getHash(Integer key, int pageNum) throws IOException {
		int pageSize = 10;
		//默认（第一页）显示从0到9的10条数据
		int start = 0;
		int end = 9;
		//如果点击了下一页（并非第一页）则运算
		if(pageNum > 1) {
			/*
			 * 因为从redis里读取，而redis下标从0开始，所以start是pageSize * (pageNum - 1)，
			 * 而不是pageSize * (pageNum - 1) + 1
			 * 相对应的end是end = (pageNum * pageSize) - 1;而不是end = pageNum * pageSize;
			 */
			start = pageSize * (pageNum - 1);
			end = (pageNum * pageSize) - 1;
		}
		Object value = listCache.getList("picture_" + key, start, end);
		return ResultCreator.getSuccess(JSON.parseArray(value.toString(), PictureListVO.class));
	}

	@RequestMapping("/testMkZIP")
	public void testMkZIP(@RequestBody DownZIPIO downZIPIO) {
		//在七牛上生成zip
        String zipName = qiniuUtil.mkzip(downZIPIO.getUrls());
        setCache.cacheSet(RedisEnum.MKZIP.getKey(), zipName);
	}
}
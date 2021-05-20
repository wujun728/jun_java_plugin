package com.zhaodui.springboot.buse.controller;

import com.zhaodui.springboot.buse.model.FxjFBiData01;
import com.zhaodui.springboot.buse.service.FxjBiData01Service;
import com.zhaodui.springboot.common.mdoel.Result;
import com.zhaodui.springboot.common.controller.BaseController;
import com.zhaodui.springboot.common.mdoel.Page;
import com.zhaodui.springboot.system.aspect.annotation.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wujun
 */
@Slf4j
@RestController
@RequestMapping("/fxjBiDate01")
public class fxjBiDate01Controller extends BaseController {

@Autowired
private FxjBiData01Service fxjBiData01Service;

	@RequestMapping(value ="/add/{username}",method = RequestMethod.POST)
	@AutoLog(value = "新增")
	public Result<?> doadd(@PathVariable(name = "username")String username,@RequestBody FxjFBiData01 fxjFBiData01) {
		Result<?> result = new Result<>();
		fxjBiData01Service.add(fxjFBiData01);
		return result.success("添加成功");
}

	@RequestMapping(value = "/edit/{username}", method = RequestMethod.PUT)
	@AutoLog(value = "编辑")
	public Result<?> eidt(@PathVariable(name = "username")String username,@RequestBody FxjFBiData01 fxjFBiData01) {
		try {
			fxjBiData01Service.update(fxjFBiData01);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return Result.error("更新失败!");
		}
		return Result.ok("更新成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "删除")
	@RequestMapping(value = "/delete/{username}", method = RequestMethod.GET)
	public Result<?> delete(@PathVariable(name = "username")String username,@RequestParam(name = "id", required = true) String id) {
		String idin = id;

		log.info("idin"+idin);
		FxjFBiData01 fxjFBiData01 = fxjBiData01Service.getById(id);
		if (fxjFBiData01 == null) {
			return Result.error("未找到对应实体");
		}
		fxjBiData01Service.deleteById(id);
		return Result.ok("删除成功!");
	}
	/**
	 * 分页列表查询
	 *
	 * @param quartzJob
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list/{username}", method = RequestMethod.GET)
	public Result<?> queryPageList(@PathVariable(name = "username")String username,
									@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   @RequestParam(name = "query01", required = false) String  query01,
								   @RequestParam(name = "query02", required = false) String  query02,
								   @RequestParam(name = "query03", required = false) String  query03,
								   @RequestParam(name = "query04", required = false) String  query04,
								   @RequestParam(name = "query05", required = false) String  query05,
								   @RequestParam(name = "query06", required = false) String  query06,
								   @RequestParam(name = "query07", required = false) String  query07,
								   @RequestParam(name = "query08", required = false) String  query08,
								   @RequestParam(name = "query09", required = false) String  query09,
								   @RequestParam(name = "query10", required = false) String  query10,
								   HttpServletRequest req) {

		Page<FxjFBiData01> page =  fxjBiData01Service.getPage(query01,query02,query03,query04,query05,query06,query07,query08,query09,query10,pageNo,pageSize);
		return Result.ok(page);

	}
}

package io.github.wujun728.online.controller;

import io.github.wujun728.common.base.Result;
import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.query.OnlineFormQuery;
import io.github.wujun728.online.service.OnlineFormService;
import io.github.wujun728.online.vo.form.OnlineFormVO;
import net.maku.framework.common.exception.ServerException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 在线表单控制器
 */
@RestController
@RequestMapping("sys/online/form/{tableId}")
public class OnlineFormController {

    private final OnlineFormService onlineFormService;

    public OnlineFormController(OnlineFormService onlineFormService) {
        this.onlineFormService = onlineFormService;
    }

    /**
     * 获取表单数据
     */
    @GetMapping("{id}")
    public Result get(@PathVariable("tableId") String tableId, @PathVariable("id") Long id) throws ServerException {
        Map<String, Object> data = onlineFormService.get(tableId, id);
        return Result.ok(data);
    }

    /**
     * 保存表单数据
     */
    @PostMapping
    public Result save(@PathVariable("tableId") String tableId, @RequestBody Map<String, String> params) throws ServerException {
        onlineFormService.save(tableId, params);
        return Result.ok();
    }

    /**
     * 获取表单配置
     */
    @GetMapping
    public Result json(@PathVariable("tableId") String tableId) throws ServerException {
        OnlineFormVO formVO = onlineFormService.getJSON(tableId);
        return Result.ok(formVO);
    }

    /**
     * 分页查询表单数据
     */
    @PostMapping("page")
    public Result page(@PathVariable("tableId") String tableId, @RequestBody @Valid OnlineFormQuery query) throws ServerException {
        PageResult<Map<String, Object>> page = onlineFormService.page(tableId, query);
        return Result.ok(page);
    }

    /**
     * 更新表单数据
     */
    @PutMapping
    public Result update(@PathVariable("tableId") String tableId, @RequestBody Map<String, String> params) throws ServerException {
        onlineFormService.update(tableId, params);
        return Result.ok();
    }

    /**
     * 删除表单数据
     */
    @DeleteMapping
    public Result delete(@PathVariable("tableId") String tableId, @RequestBody List<Long> ids) throws ServerException {
        onlineFormService.delete(tableId, ids);
        return Result.ok();
    }
}


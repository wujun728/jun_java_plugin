package io.github.wujun728.online.controller;

import io.github.wujun728.common.base.Result;
import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.query.OnlineTableQuery;
import io.github.wujun728.online.service.OnlineTableService;
import io.github.wujun728.online.vo.OnlineTableVO;
import net.maku.framework.common.exception.ServerException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 在线表控制器
 */
@RestController
@RequestMapping("sys/online/table")
public class OnlineTableController {

    private final OnlineTableService onlineTableService;

    public OnlineTableController(OnlineTableService onlineTableService) {
        this.onlineTableService = onlineTableService;
    }

    /**
     * 获取表信息
     */
    @GetMapping("{id}")
    public Result get(@PathVariable String id) {
        OnlineTableVO vo = onlineTableService.get(id);
        return Result.ok(vo);
    }

    /**
     * 保存表信息
     */
    @PostMapping
    public Result save(@RequestBody OnlineTableVO vo) {
        try {
            onlineTableService.save(vo);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新表信息
     */
    @PutMapping
    public Result update(@RequestBody OnlineTableVO vo) {
        onlineTableService.update(vo);
        return Result.ok();
    }

    /**
     * 删除表信息
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> ids) {
        onlineTableService.delete(ids);
        return Result.ok();
    }

    /**
     * 分页查询表信息
     */
    @PostMapping("page")
    public Result page(@RequestBody @Valid OnlineTableQuery query) {
        PageResult<OnlineTableVO> page = onlineTableService.page(query);
        return Result.ok(page);
    }
}


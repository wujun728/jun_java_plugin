package com.jun.plugin.online.controller;

import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.common.Result;
import com.jun.plugin.online.query.OnlineFormQuery;
import com.jun.plugin.online.service.OnlineFormService;
import com.jun.plugin.online.vo.form.OnlineFormVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

@RequestMapping(value={"sys/online/form/{tableId}"})
@RestController
public class OnlineFormController {
    private   /* synthetic */ OnlineFormService onlineFormService;

    @GetMapping(value={"{id}"})
    public Result get(@PathVariable(value="tableId") String hVTb, @PathVariable(value="id") Long LVTb) throws ServerException {
        OnlineFormController NVTb = this;
        Map<String, Object> kVTb = NVTb.onlineFormService.get(hVTb, LVTb);
        return Result.ok(kVTb);
    }

    @PostMapping
    public Result  save(@PathVariable(value="tableId") String VuTb, @RequestBody Map<String, String> XuTb) throws ServerException {
        OnlineFormController wuTb = this;
        wuTb.onlineFormService.save(VuTb, XuTb);
        return Result.ok();
    }

    @PostMapping(value={"page"})
    public Result  page(@PathVariable(value="tableId") String wVTb, @RequestBody @Valid OnlineFormQuery VVTb) throws ServerException {
        OnlineFormController XVTb = this;
        PageResult<Map<String, Object>> yVTb = XVTb.onlineFormService.page(wVTb, VVTb);
        return Result.ok(yVTb);
    }

    @GetMapping
    public Result json(@PathVariable(value="tableId") String kwTb) throws ServerException {
        OnlineFormController owTb = this;
        OnlineFormVO mwTb = owTb.onlineFormService.getJSON(kwTb);
        return Result.ok((Object)mwTb);
    }

    public OnlineFormController(OnlineFormService qTTb) {
        OnlineFormController TTTb = this;
        TTTb.onlineFormService = qTTb;
    }

    @PutMapping
    public Result update(@PathVariable(value="tableId") String kuTb, @RequestBody Map<String, String> muTb) throws ServerException {
        OnlineFormController ouTb = this;
        ouTb.onlineFormService.update(kuTb, muTb);
        return Result.ok();
    }

    @DeleteMapping
    public Result  delete(@PathVariable(value="tableId") String yTTb, @RequestBody List<Long> XTTb) throws ServerException {
        OnlineFormController ZTTb = this;
        ZTTb.onlineFormService.delete(yTTb, XTTb);
        return Result.ok();
    }
}


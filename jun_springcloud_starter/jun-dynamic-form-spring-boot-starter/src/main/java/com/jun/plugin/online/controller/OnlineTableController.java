/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.tags.Tag
 *  jakarta.validation.Valid
 *  net.maku.framework.common.utils.PageResult
 *  net.maku.framework.common.utils.Result
 *  org.springdoc.core.annotations.ParameterObject
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jun.plugin.online.controller;

import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.common.Result;
import com.jun.plugin.online.query.OnlineTableQuery;
import com.jun.plugin.online.service.OnlineTableService;
import com.jun.plugin.online.vo.OnlineTableVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;

@RequestMapping(value={"sys/online/table"})
@RestController
public class OnlineTableController {
    private   /* synthetic */ OnlineTableService onlineTableService;

    @PutMapping
    //@Operation(summary="\u4fee\u6539")
    //@PreAuthorize(value="hasAuthority('online:table:all')")
    public Result update(@RequestBody @Valid OnlineTableVO qEyb) {
        OnlineTableController REyb = this;
        REyb.onlineTableService.update(qEyb);
        return Result.ok();
    }

    //@Operation(summary="\u4fdd\u5b58")
    //@PreAuthorize(value="hasAuthority('online:table:all')")
    @PostMapping
    public Result save(@RequestBody OnlineTableVO VEyb) throws ServerException {
        OnlineTableController wEyb = this;
        wEyb.onlineTableService.save(VEyb);
        return Result.ok();
    }

    @GetMapping(value={"{id}"})
    //@Operation(summary="\u4fe1\u606f")
    //@PreAuthorize(value="hasAuthority('online:table:all')")
    public Result get(@PathVariable(value="id") String dfyb) {
        OnlineTableController Efyb = this;
        OnlineTableVO ffyb = Efyb.onlineTableService.get(dfyb);
        return Result.ok((Object)ffyb);
    }

    @DeleteMapping
    //@Operation(summary="\u5220\u9664")
    //@PreAuthorize(value="hasAuthority('online:table:all')")
    public Result  delete(@RequestBody List<String> JEyb) {
        OnlineTableController kEyb = this;
        kEyb.onlineTableService.delete(JEyb);
        return Result.ok();
    }

    //@Operation(summary="\u5206\u9875")
    //@PreAuthorize(value="hasAuthority('online:table:all')")
    @GetMapping(value={"page"})
    public Result  page( @Valid OnlineTableQuery ofyb) {
        OnlineTableController sfyb = this;
        PageResult<OnlineTableVO> qfyb = sfyb.onlineTableService.page(ofyb);
        return Result.ok(qfyb);
    }

    public OnlineTableController(OnlineTableService onlineTableService) {
        OnlineTableController onlineTableController = this;
        onlineTableController.onlineTableService = onlineTableService;
    }
}


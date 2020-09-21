/**
 * MailController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.id.web;

import org.itkk.udf.core.RestResponse;
import org.itkk.udf.id.service.IdWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述 : IdWorkerController
 *
 * @author Administrator
 */
@RestController
public class IdWorkerController implements IIdWorkerController {

    /**
     * idWorkerService
     */
    @Autowired
    private IdWorkerService idWorkerService;

    @Override
    public RestResponse<String> nextId() {
        return new RestResponse<>(idWorkerService.nextId());
    }

    @Override
    public RestResponse<String> nextId(@PathVariable Integer dwId) {
        return new RestResponse<>(idWorkerService.nextId(dwId));
    }

    @Override
    public RestResponse<String> nextId(@PathVariable Integer datacenterId, @PathVariable Integer workerId) {
        return new RestResponse<>(idWorkerService.nextId(datacenterId, workerId));
    }

    @Override
    public RestResponse<List<String>> batchNextId(@PathVariable Integer count) {
        return new RestResponse<>(idWorkerService.batchNextId(count));
    }

    @Override
    public RestResponse<List<String>> batchNextId(@PathVariable Integer dwId, @PathVariable Integer count) {
        return new RestResponse<>(idWorkerService.batchNextId(dwId, count));
    }

    @Override
    public RestResponse<List<String>> batchNextId(@PathVariable Integer datacenterId, @PathVariable Integer workerId, @PathVariable Integer count) {
        return new RestResponse<>(idWorkerService.batchNextId(datacenterId, workerId, count));
    }
}

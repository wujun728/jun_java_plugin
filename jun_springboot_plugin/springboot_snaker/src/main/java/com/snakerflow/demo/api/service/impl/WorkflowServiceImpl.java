package com.snakerflow.demo.api.service.impl;

import com.snakerflow.demo.api.service.IWorkflowService;
import com.snakerflow.demo.utils.SnakerEngineFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements IWorkflowService {
    @Autowired
    private SnakerEngineFacadeImpl snakerEngineFacade;
    @Override
    public Integer deployWf() {

        return null;
    }
}

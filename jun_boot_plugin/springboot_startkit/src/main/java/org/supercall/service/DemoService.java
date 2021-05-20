package org.supercall.service;

import org.springframework.stereotype.Service;
import org.supercall.domainModel.DemoModel;

@Service
public class DemoService implements IDemoService {

    @Override
    public DemoModel getEntity() {
        DemoModel demoModel=new DemoModel();
        demoModel.setName("a");
        demoModel.setAge(1);
        return demoModel;
    }
}

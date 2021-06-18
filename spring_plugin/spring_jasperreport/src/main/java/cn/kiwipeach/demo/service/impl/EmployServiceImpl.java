/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.service.impl;

import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.mapper.EmployMapper;
import cn.kiwipeach.demo.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Create Date: 2018/01/08
 * Description: 员工服务实现类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Service
public class EmployServiceImpl implements EmployService {


    @Autowired
    private EmployMapper employMapper;

    @Override
    public Employ queryEmploy(BigDecimal empno) {
        return employMapper.selectByPrimaryKey(empno);
    }

}

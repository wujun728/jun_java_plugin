package com.bzcoder.test.service.impl;

import com.bzcoder.test.entity.Company;
import com.bzcoder.test.mapper.CompanyMapper;
import com.bzcoder.test.service.ICompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BaoZhou
 * @since 2018-10-03
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

}

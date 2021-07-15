package com.jun.plugin.mybatisplus.biz.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.plugin.mybatisplus.biz.test.entity.Company;
import com.jun.plugin.mybatisplus.biz.test.mapper.CompanyMapper;
import com.jun.plugin.mybatisplus.biz.test.service.ICompanyService;

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

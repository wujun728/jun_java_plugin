package com.wangxin.service.mail.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxin.common.constants.Constants;
import com.wangxin.common.exception.BusinessException;
import com.wangxin.common.util.UUIDUtil;
import com.wangxin.entity.mail.MailAddress;
import com.wangxin.framework.datasource.DataSource;
import com.wangxin.framework.datasource.DataSourceEnum;
import com.wangxin.mapper.mail.MailAddressMapper;
import com.wangxin.service.mail.MailAddressService;

@Service("mailAddressService")
public class MailAddressServiceImpl implements MailAddressService {

    private static final Logger log = LoggerFactory.getLogger(MailAddressServiceImpl.class);

    @Autowired
    private MailAddressMapper mailAddressMapper;

    @Override
    public boolean addMailAddress(MailAddress mailAddress) {
        try {
            if (mailAddress == null)
                return false;
            mailAddress.setId(UUIDUtil.getRandom32PK());
            mailAddress.setCreateTime(Calendar.getInstance().getTime());
            mailAddress.setIsDel(Constants.IS_DEL_U);
            mailAddress.setVersionNum(1l);

            int count = insertMailAddress(mailAddress);
            System.err.println("insert count=" + count);
            int cc = updateMailAddress("01");
            System.err.println("update count=" + cc);
            if (count == 1)
                return true;
            return false;
        } catch (Exception e) {
            log.error("收件配置信息保存异常.{}", e.getLocalizedMessage());
            throw new BusinessException("收件配置信息保存异常");
        }
    }

    @DataSource(DataSourceEnum.SLAVE)
    public int insertMailAddress(MailAddress mailAddress) {
        return mailAddressMapper.insert(mailAddress);
    }

    @DataSource(DataSourceEnum.MASTER)
    public int updateMailAddress(String mailType) {
        List<MailAddress> list = mailAddressMapper.findMailAddressByMailType(mailType);
        if (CollectionUtils.isNotEmpty(list)) {
            MailAddress ma = list.iterator().next();
            int count = mailAddressMapper.updateFail(ma);
            return count;
        }
        return 0;
    }

    @Override
    public boolean editMailAddress(MailAddress mailAddress) {
        if (mailAddress == null || StringUtils.isBlank(mailAddress.getId()))
            return false;
        MailAddress ma = mailAddressMapper.findById(mailAddress.getId());
        if (ma == null)
            return false;
        mailAddress.setIsDel(ma.getIsDel());
        mailAddress.setVersionNum(ma.getVersionNum() + 1);
        mailAddress.setUpdateTime(Calendar.getInstance().getTime());
        int count = mailAddressMapper.update(mailAddress);
        if (count == 1)
            return true;
        return false;
    }

    @Override
    public MailAddress findMailAddressById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        return mailAddressMapper.findById(id);
    }

    @Override
    public MailAddress findMailAddressByMailType(String mailType) {
        if (StringUtils.isBlank(mailType))
            return new MailAddress();
        List<MailAddress> result = mailAddressMapper.findMailAddressByMailType(mailType);
        if (CollectionUtils.isNotEmpty(result))
            return result.iterator().next();
        return new MailAddress();
    }

    @Override
    public PageInfo<MailAddress> findMailAddessByPage(Integer pageNum, String mailType) {
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<MailAddress> result = mailAddressMapper.findMailAddessByPage(mailType);
        // 用PageInfo对结果进行包装
        PageInfo<MailAddress> page = new PageInfo<MailAddress>(result);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.debug("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

}

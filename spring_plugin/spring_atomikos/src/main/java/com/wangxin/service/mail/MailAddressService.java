package com.wangxin.service.mail;

import com.github.pagehelper.PageInfo;
import com.wangxin.entity.mail.MailAddress;

public interface MailAddressService {

    public boolean addMailAddress(MailAddress mailAddress);

    public boolean editMailAddress(MailAddress mailAddress);

    public MailAddress findMailAddressById(String id);

    public MailAddress findMailAddressByMailType(String mailType);

    public PageInfo<MailAddress> findMailAddessByPage(Integer pageNum, String mailType);

}

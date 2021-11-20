package com.jun.plugin.mapper.mail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jun.plugin.entity.mail.MailAddress;
import com.jun.plugin.mapper.SqlMapper;

public interface MailAddressMapper extends SqlMapper {

    List<MailAddress> findMailAddressByMailType(@Param("mailType") String mailType);

    MailAddress findById(String id);

    int update(MailAddress mailAddress);

    int insert(MailAddress mailAddress);

    List<MailAddress> findMailAddessByPage(@Param("mailType") String mailType);

    int updateFail(MailAddress ma);

}

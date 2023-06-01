package cn.springmvc.mybatis.common.utils.fmt.xml;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author Vincent.Wang
 *
 */
public class DateAdapter extends XmlAdapter<Long, Date> {

    @Override
    public Date unmarshal(Long createTime) throws Exception {
        Date date = new Date(createTime * 1000);
        return date;
    }

    @Override
    public Long marshal(Date date) throws Exception {
        Long createTime = date.getTime() / 1000;
        return createTime;
    }

}

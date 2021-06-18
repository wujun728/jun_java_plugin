package cn.springmvc.mybatis.common.utils.fmt.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author Vincent.Wang
 *
 */
public class CDDATAAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<![CDATA[").append(v).append("]]>");
        return sb.toString();
    }

}

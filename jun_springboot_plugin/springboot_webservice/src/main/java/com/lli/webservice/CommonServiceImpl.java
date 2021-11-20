package com.lli.webservice;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.jws.WebService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口实现
 *
 * @author leftso
 */
@WebService(serviceName = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.lli.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.lli.webservice.CommonService"// 接口地址
)
@Component
public class CommonServiceImpl implements CommonService {

    @Override
    public String sayHello(String param) {
        String decodeStr = Base64.decodeStr(param);
        Document document = XmlUtil.parseXml(decodeStr);
        Element rootElement = XmlUtil.getRootElement(document);
//        String ahdm = Base64.decodeStr(XmlUtil.elementText(rootElement, "AHDM"));

        Document doc = XmlUtil.createXml();
        doc.setXmlStandalone(true);

        Element response = doc.createElement("Response");
        doc.appendChild(response);
        Element result = doc.createElement("Result");
        response.appendChild(result);
        Element codeEle = doc.createElement("Code");
        codeEle.setTextContent(Base64.encode("0"));
        result.appendChild(codeEle);
        Element msgEle = doc.createElement("Msg");
        msgEle.setTextContent(Base64.encode("操作成功"));
        result.appendChild(msgEle);

        Element data = doc.createElement("DATA");
        data.setAttribute("Count", Base64.encode("1"));
        result.appendChild(data);

        Element eaj = doc.createElement("EAJ");
        data.appendChild(eaj);

        Element ahdm = doc.createElement("AHDM");
        ahdm.setTextContent(Base64.encode("2"));
        eaj.appendChild(ahdm);

        Element ah = doc.createElement("AH");
        ah.setTextContent(Base64.encode("（2018）沪0107民初12723号"));
        eaj.appendChild(ah);

        Element fydm = doc.createElement("FYDM");
        fydm.setTextContent(Base64.encode("226000"));
        eaj.appendChild(fydm);

        String docStr = XmlUtil.toStr(doc);
        System.out.println(docStr);
        return Base64.encode(docStr);
    }

    private Map<String, User> userMap = new HashMap<String, User>();

    public CommonServiceImpl() {
        System.out.println("向实体类插入数据");
        User user = new User();
        user.setUserId("411001");
        user.setUsername("zhansan");
        user.setAge("20");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("411002");
        user.setUsername("lisi");
        user.setAge("30");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("411003");
        user.setUsername("wangwu");
        user.setAge("40");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);
    }

    @Override
    public String getUser(String param) {
//        Base64.decode(param);
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><name>吹比龙</name>";
//        return xml;
        User user = new User("1", "吹比龙", "18");
        return user.toString();
    }

}
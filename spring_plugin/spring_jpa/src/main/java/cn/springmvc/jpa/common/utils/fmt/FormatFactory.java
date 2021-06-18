package cn.springmvc.jpa.common.utils.fmt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cn.springmvc.jpa.common.utils.fmt.xml.CDDATAEscapeHandler;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

/**
 * 
 * <pre>
 *  model类名上加入xml与json的相关注释【@XmlAccessorType(XmlAccessType.FIELD)、@XmlRootElement(name = "xml")、@JsonInclude(Include.NON_EMPTY)】<br/>
 *  属性名上加【@XmlElement(name = "propertyName")、@JsonProperty("propertyName")】
 * </pre>
 * 
 * 
 * @author Wujun
 *
 */
@SuppressWarnings("restriction")
public class FormatFactory {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static String objectToXml(Object object) {
        String result = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 不生成头信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CDDATAEscapeHandler());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(object, os);
            result = new String(os.toByteArray(), "UTF-8");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T xmlToObject(Class<T> clazz, String xml) {
        T object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = (T) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
        } catch (JAXBException e) {
            e.getMessage();
        }
        return object;
    }

    public static String objectToJson(Object object) {
        String result = null;
        try {
            result = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T jsonToObject(Class<T> clazz, String json) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (!Character.isHighSurrogate(ch) && !Character.isLowSurrogate(ch)) {
                sb.append(ch);
            }
        }
        json = sb.toString();
        T object = null;
        try {
            object = MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

}

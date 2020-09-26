
package org.apache.hello_world_soap_http.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="minor" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="major" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "minor",
    "major"
})
@XmlRootElement(name = "faultDetail")
public class FaultDetail {

    protected short minor;
    protected short major;

    /**
     * 获取minor属性的值。
     * 
     */
    public short getMinor() {
        return minor;
    }

    /**
     * 设置minor属性的值。
     * 
     */
    public void setMinor(short value) {
        this.minor = value;
    }

    /**
     * 获取major属性的值。
     * 
     */
    public short getMajor() {
        return major;
    }

    /**
     * 设置major属性的值。
     * 
     */
    public void setMajor(short value) {
        this.major = value;
    }

}

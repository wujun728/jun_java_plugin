package com.jun.plugin.spring.activemq.main.p2p;


import java.io.Serializable;

/**
 * A Camel Router
 * 
 * @author chenchao
 * @version $Revision: 1.1 $
 */
public class Student implements Serializable {

    private static final long serialVersionUID = -478112958035082239L;

    /**
	 * 
	 */

    private String name = "";

    private int age = 0;

    private String sex = "";

    private String address = "";
    
    private String msg = "";

    public Student(String name, int age, String sex, String address) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getSex() {
        return this.sex;
    }

    public String getAddress() {
        return this.address;
    }
        
    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
    public String toString() {
        return "Student" + "\n" + "name: " + getName() + "\n" + "age: " + getAge() + "\n" + "sex: " + getSex() + "\n"
                + "address: " + getAddress() + "\n" + "message:" + msg + "\n";
    }
}

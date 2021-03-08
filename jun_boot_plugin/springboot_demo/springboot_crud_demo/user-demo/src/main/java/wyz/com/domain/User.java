package wyz.com.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by qinyg on 2017/11/20.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;     // 唯一编码，简写唯一码
    private String username;
    private String pw;
    private Integer age;

    public User() {
    }
    public User(String username, String pw, Integer age) {
        this.username = username;
        this.pw = pw;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pw='" + pw + '\'' +
                ", age=" + age +
                '}';
    }
}

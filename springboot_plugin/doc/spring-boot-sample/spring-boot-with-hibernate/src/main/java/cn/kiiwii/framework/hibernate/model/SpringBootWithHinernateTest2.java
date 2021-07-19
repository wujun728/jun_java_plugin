package cn.kiiwii.framework.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zhong on 2017/1/5.
 */
@Table(name = "spring_boot_with_hinernate_test2")
@Entity
public class SpringBootWithHinernateTest2 implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name",length = 128)
    private String userName;
    @Column(name = "create_time")
    private Timestamp createTime;

    public SpringBootWithHinernateTest2() {
    }

    public SpringBootWithHinernateTest2(String userName, Timestamp createTime) {
        this.userName = userName;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SpringBootWithHinernateTest{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

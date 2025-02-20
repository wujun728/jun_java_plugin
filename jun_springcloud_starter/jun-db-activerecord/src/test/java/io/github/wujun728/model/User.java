package io.github.wujun728.model;

import io.github.wujun728.db.orm.annotation.Column;
import io.github.wujun728.db.orm.annotation.Entity;
import io.github.wujun728.db.orm.annotation.PK;
import io.github.wujun728.db.orm.core.Model;
import io.github.wujun728.db.record.mapper.RowType;

@Entity(table = "c_user")
public class User extends Model<User> {

    @PK
    @Column(name = "id_",type = RowType.LONG)
    private long id;

    @Column(name = "mobile_")
    private String mobile;

    @Column(name = "password_")
    private String password;


//    @Join(refColumn = "id_") //关联的refColumn的值为数据库的关联列
//    @Column(name = "student_id_")
//    private Student student;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }


//    public Student getStudent() {
//        return student;
//    }
//
//    public User setStudent(Student student) {
//        this.student = student;
//        return this;
//    }
}
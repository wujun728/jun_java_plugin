package com.springboot.springbootjpaquerydsl.dto;

import com.springboot.springbootjpaquerydsl.model.LessonModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User: weishiyao
 * @Date: 2019/9/25
 * @Time: 23:41
 * @email: inwsy@hotmail.com
 * Description:
 */
@Data
public class UserDTO {
    private String nickName;
    private int age;
    private Date startDate;
    private String address;
    private String name;
}

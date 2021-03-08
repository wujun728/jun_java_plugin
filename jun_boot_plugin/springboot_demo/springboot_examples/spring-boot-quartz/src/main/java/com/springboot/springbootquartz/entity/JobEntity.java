package com.springboot.springbootquartz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Version: 1.0
 * @Desc:
 */
@Entity
@Table(name = "JOB_ENTITY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;          //job名称
    private String jobGroup;      //job组名
    private String cron;          //执行的cron
    private String parameter;     //job的参数
    private String description;   //job描述信息
    private String vmParam;       //vm参数
    private String jarPath;       //job的jar路径
    private String status;        //job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
}

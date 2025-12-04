package cn.chen.project.generatesql.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author chenzedeng
 * @Email yustart@foxmail.com
 * @Create 2020-02-29 5:02 下午
 */
@Data
public class SqlTable {

    private String tableName;
    private String tableDesc;

    private List<ExcelRow> rows;

}

package io.github.wujun728.sql.entity;

import lombok.Data;

@Data
public class ApiDataSource {

    String id;
    String url;
    String username;
    String password;
    String driver;
    String type;

}
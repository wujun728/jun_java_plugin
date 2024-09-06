package io.github.wujun728.rest.entity;

import lombok.Data;

@Data
public class ApiDataSource {
    String id;
    String name;
    String url;
    String username;
    String password;
    String type;
    String driver;
}

package com.mycompany.myproject.repository.redis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import java.io.Serializable;

@RedisHash("myRedisUser")
public class MyRedisUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "userId")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

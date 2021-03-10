package com.gosalelab.pojo;

import java.time.LocalDateTime;

public class CacheSampleEntity {

    public CacheSampleEntity(){}

    public CacheSampleEntity(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
    }

    private String name;
    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }



}

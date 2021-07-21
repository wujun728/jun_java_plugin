package com.neo.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Entity {

    @Override
    public String toString() {
 	return ToStringBuilder.reflectionToString(this);
     }
}


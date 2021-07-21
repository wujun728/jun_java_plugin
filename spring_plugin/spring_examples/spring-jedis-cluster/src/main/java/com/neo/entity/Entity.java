package com.neo.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Entity implements Serializable{

    /** serialVersionUID*/
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
 	return ToStringBuilder.reflectionToString(this);
     }
}


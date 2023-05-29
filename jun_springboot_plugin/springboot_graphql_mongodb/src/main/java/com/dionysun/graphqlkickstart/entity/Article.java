package com.dionysun.graphqlkickstart.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@ToString
@Builder
@Document("articles")
public class Article {
    @Id
    private String id;
    @Field("title")
    private String title;
    @Field("content")
    private String content;
    @Field("authorId")
    private String authorId;
    @Field("createBy")
    private Date createBy;
    @Field("thumbUp")
    private Integer thumbUp;
}

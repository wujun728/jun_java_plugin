package com.dionysun.graphqlkickstart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
@Data
@Builder
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @Field("nickname")
    private String nickname;
    @Field("mail")
    private String mail;
    @Field("password")
    private String password;
    @Field("description")
    private String description;
}

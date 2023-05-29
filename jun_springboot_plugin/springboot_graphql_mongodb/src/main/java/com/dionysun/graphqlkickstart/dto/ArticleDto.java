package com.dionysun.graphqlkickstart.dto;

import com.dionysun.graphqlkickstart.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String id;
    private User author;
    private String title;
    private String content;
    private Date createBy;
    private int thumbUp;
}

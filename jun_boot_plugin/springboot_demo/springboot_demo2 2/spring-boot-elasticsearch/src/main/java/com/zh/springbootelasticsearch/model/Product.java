package com.zh.springbootelasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/18
 */
@Data
@ToString
@Document(indexName = "es_products",type = "product", shards = 1, replicas = 0)
public class Product {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer="ik_max_word",fielddata = true)
    private String category;

    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer="ik_max_word",fielddata = true)
    private String brand;

    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer="ik_max_word",fielddata = true)
    private String name;

    @Field(type = FieldType.Double)
    private Double costPrice;

    @Field(type = FieldType.Double)
    private Double salePrice;

    @Field(type = FieldType.Integer)
    private Integer stockCount;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date createTime;


}

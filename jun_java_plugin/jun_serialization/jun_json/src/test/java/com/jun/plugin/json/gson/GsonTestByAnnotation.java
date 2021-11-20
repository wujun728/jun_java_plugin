package com.jun.plugin.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class GsonTestByAnnotation {
    private GsonBuilder builder;
    private Gson gson;

    @Before
    public void init() {
        builder = new GsonBuilder();
    }

    @Test
    public void testAnnotationExposeToJson() {
        Book book = new Book(1, 20, "hhhhhhhhhhhhhhhh", "bill");
        gson = builder.excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(book);
        System.out.println(json);
    }

    @Test
    public void testAnnotationExposeFromJson() {
        String json = " {\"totalPage\":1,\"Content\":\"hhhhhhhhhhhhhhhh\",\"autor\":\"bill\"}";
        gson = builder.excludeFieldsWithoutExposeAnnotation().create();
        Book book = gson.fromJson(json, Book.class);
        System.out.println(book.toString());
    }

}


/**
 * 添加 @SerializedName("totalPage") ，可以更改序列化的时候对应的字段名
 * <p>
 * 如果注释的字段名在类中已经存在，必须把存在的字段用 @Expose 剔除出去如下面的例子。
 * 使用@Expose之后必须用GsonBuilder.create() 的方式来简历gson对象，不然Expose的注释不会生效
 */
class Book {
    @SerializedName("totalPage")
    @Expose
    private Integer pageNo;
    @Expose(serialize = false, deserialize = false)
    private Integer totalPage;
    @Expose
    private String Content;
    @Expose
    private String autor;

    public Book(Integer pageNo, Integer totalPage, String content, String autor) {
        this.pageNo = pageNo;
        this.totalPage = totalPage;
        Content = content;
        this.autor = autor;
    }

    public Book() {
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getPageNo(), book.getPageNo()) &&
                Objects.equals(getTotalPage(), book.getTotalPage()) &&
                Objects.equals(getContent(), book.getContent()) &&
                Objects.equals(getAutor(), book.getAutor());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPageNo(), getTotalPage(), getContent(), getAutor());
    }

    @Override
    public String toString() {
        return "book{" +
                "pageNo=" + pageNo +
                ", totalPage=" + totalPage +
                ", Content='" + Content + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}

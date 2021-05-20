package com.jun.plugin.poi.test2.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jun.plugin.poi.test2.ExcelWriteHelper;
import com.jun.plugin.poi.test2.WriteRowMapper;

public class ExcelWriteHelperTest {
    private ExcelWriteHelper write = null;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testExport() throws IOException {
        write = new ExcelWriteHelper("./src/test/resources/测试22.xls");
        final String[] titles = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN","图书出版社", "封面图片" };
        List<Book> dataset2 = new ArrayList<Book>();
        dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567", "清华出版社"));
        dataset2.add(new Book(2, "java编程思想java编程思想java编程思想java编程思想java编程思想java编程思想", "brucl", 300.33f, "1234567", "阳光出版社"));
        dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567", "清华出版社"));
        dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567", "清华出版社"));
        dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567", "汤春秀出版社"));
        
        write.exportExcel("测试", titles, dataset2, new WriteRowMapper(){
            public List<String> handleData(Object param) {
                List<String> value = new ArrayList<String>(titles.length);
                Book pp = (Book)param;
                value.add(String.valueOf(pp.getBookId()));
                value.add(pp.getName());
                value.add(pp.getAuthor());
                value.add(String.valueOf(pp.getPrice()));
                value.add(pp.getIsbn());
                value.add(pp.getPubName());
                value.add("");
                return value;
            }
            
        });
    }

}

class Book {
    private int bookId;
    private String name;
    private String author;
    private float price;
    private String isbn;
    private String pubName;
    private byte[] preface;
    public Book() {
        super();
    }
    public Book(int bookId, String name, String author, float price,
    String isbn, String pubName) {
        super();
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.pubName = pubName;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getPubName() {
        return pubName;
    }
    public void setPubName(String pubName) {
        this.pubName = pubName;
    }
    public byte[] getPreface() {
        return preface;
    }
    public void setPreface(byte[] preface) {
        this.preface = preface;
    }
}

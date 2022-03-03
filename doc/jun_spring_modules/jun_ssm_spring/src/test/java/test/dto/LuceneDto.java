package test.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springrain.frame.annotation.LuceneField;

@Table(name="test1")
public class LuceneDto {
    
    private String id;
    private String name;
    private int int1=10;
    private Integer int2=20;
    private Date date=new Date();
    private float f1=30f;
    private Float f2=40f;
    private double d1=60d;
    private Double d2=70d;
    
    
   // @LuceneField(stringTokenized=false)
    @LuceneField
    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @LuceneField
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @LuceneField
    public int getInt1() {
        return int1;
    }
    public void setInt1(int int1) {
        this.int1 = int1;
    }
    
    @LuceneField
    public Integer getInt2() {
        return int2;
    }
    public void setInt2(Integer int2) {
        this.int2 = int2;
    }
    
    @LuceneField
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    @LuceneField
    public float getF1() {
        return f1;
    }
    public void setF1(float f1) {
        this.f1 = f1;
    }
    
    @LuceneField
    public Float getF2() {
        return f2;
    }
    public void setF2(Float f2) {
        this.f2 = f2;
    }
    
    @LuceneField
    public double getD1() {
        return d1;
    }
    public void setD1(double d1) {
        this.d1 = d1;
    }
    
    @LuceneField
    public Double getD2() {
        return d2;
    }
    public void setD2(Double d2) {
        this.d2 = d2;
    }
    

}

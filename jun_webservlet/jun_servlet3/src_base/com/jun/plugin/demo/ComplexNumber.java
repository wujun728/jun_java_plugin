package com.jun.plugin.demo;

public class ComplexNumber {
    private double x, y;
    
    public ComplexNumber(double real, double imaginary) {
        this.x = real;
        this.y = imaginary;
    }

    public double real() { return x; } //����ʵ��
    public double imaginary() { return y; }  //�����鲿
    public double magnitude() { return Math.sqrt(x*x + y*y); }  //���ع���ֵ
    public String toString() { return "{" + x + "," + y + "}"; }
    
    //�����������
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x + b.x, a.y + b.y);
    }
    
    //this��������һ���������
    public ComplexNumber add(ComplexNumber a) {
        return new ComplexNumber(this.x + a.x, this.y+a.y);
    }
    
    //�����������
    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x*b.x - a.y*b.y, a.x*b.y + a.y*b.x);
    }
    
    //this����������һ���������
    public ComplexNumber multiply(ComplexNumber a) {
        return new ComplexNumber(x*a.x - y*a.y, x*a.y + y*a.x);
    }
}

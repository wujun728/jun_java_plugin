package com.mycompany.myproject.base.exception;

public class MyException extends  Exception {

    public MyException(String message) {
        super(message);
    }
    
    
    
    public static void main(String[] args){


       try {

           if("".length() == 0) {
               throw new MyException("is My Exception");
           }

       }catch (Exception ex){
           System.out.println(ex.getMessage());
       }


    }
}

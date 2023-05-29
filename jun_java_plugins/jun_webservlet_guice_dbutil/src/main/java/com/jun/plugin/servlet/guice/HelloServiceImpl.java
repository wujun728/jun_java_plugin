package com.jun.plugin.servlet.guice;

public class HelloServiceImpl implements HelloService{

  @Override
  public String echo() {
    return "Hello World!";
  }

}

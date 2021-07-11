package com.tingshulien.servlet.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class HelloServlet extends HttpServlet {

  @Inject
  public HelloServlet(HelloService helloService) {
    this.helloService = helloService;
  }

  private final HelloService helloService;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String echo = helloService.echo();

    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println(echo);

    out.flush();
    out.close();
  }

}

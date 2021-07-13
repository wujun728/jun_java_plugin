package com.tingshulien.servlet.guice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class HelloServletTest {

  @InjectMocks
  private HelloServlet servlet;

  @Mock
  private HelloService service;

  @Before
  public void setUp() throws Exception {
    when(service.echo()).thenReturn("Hello World!");
  }

  @Test
  public void testDoGet() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(printWriter);

    servlet.doGet(request, response);
    String result = stringWriter.getBuffer().toString().trim();

    assertEquals(result, "Hello World!");
  }

}
package com.jun.webservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet({"/path/*", "*.ext"})
@WebServlet(value = {"/http24"})
public class Http2Servlet4 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PushBuilder pushBuilder = req.newPushBuilder();
        if (pushBuilder != null) {
            pushBuilder
                    .path("images/kodedu-logo.png")
                    .addHeader("content-type", "image/png")
                    .push();
        }

        /*PushBuilder pushBuilder = request.newPushBuilder();

        if (pushBuilder != null) {
            pushBuilder.path("images/hero-banner.jpg").push();
            pushBuilder.path("css/menu.css").push();
            pushBuilder.path("js/marquee.js").push();
        }*/


        HttpServletMapping mappings = req.getHttpServletMapping();
        String mapping = mappings.getMappingMatch().name();
        String value = mappings.getMatchValue();
        String pattern = mappings.getPattern();
        String servletName = mappings.getServletName();


        try (PrintWriter respWriter = resp.getWriter();) {
            respWriter.write("<html>" +
                    "<img src='images/kodedu-logo.png'>" +
                    "</html>");
        }

    }
}
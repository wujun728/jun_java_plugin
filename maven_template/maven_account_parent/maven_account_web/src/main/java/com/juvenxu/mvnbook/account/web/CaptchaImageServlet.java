package com.juvenxu.mvnbook.account.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.juvenxu.mvnbook.account.service.AccountService;
import com.juvenxu.mvnbook.account.service.AccountServiceException;

public class CaptchaImageServlet
    extends HttpServlet
{
    private ApplicationContext context;

    private static final long serialVersionUID = 5274323889605521606L;

    @Override
    public void init()
        throws ServletException
    {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException,
            IOException
    {
        String key = request.getParameter( "key" );

        if ( key == null || key.length() == 0 )
        {
            response.sendError( 400, "No Captcha Key Found" );
        }
        else
        {
            AccountService service = (AccountService) context.getBean( "accountService" );

            try
            {
                response.setContentType( "image/jpeg" );
                OutputStream out = response.getOutputStream();
                out.write( service.generateCaptchaImage( key ) );
                out.close();
            }
            catch ( AccountServiceException e )
            {
                response.sendError( 400, e.getMessage() );
            }
        }
    }
}

package com.juvenxu.mvnbook.account.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.juvenxu.mvnbook.account.service.AccountService;
import com.juvenxu.mvnbook.account.service.AccountServiceException;

public class ActivateServlet
    extends HttpServlet
{
    private static final long serialVersionUID = 3668445055149826106L;

    private ApplicationContext context;

    @Override
    public void init()
        throws ServletException
    {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException,
            IOException
    {
        String key = req.getParameter( "key" );

        if ( key == null || key.length() == 0 )
        {
            resp.sendError( 400, "No activation key provided." );
            return;
        }

        AccountService service = (AccountService) context.getBean( "accountService" );

        try
        {
            service.activate( key );
            resp.getWriter().write( "Account is activated, now you can login." );
        }
        catch ( AccountServiceException e )
        {
            resp.sendError( 400, "Unable to activate account" );
            return;
        }
    }
}

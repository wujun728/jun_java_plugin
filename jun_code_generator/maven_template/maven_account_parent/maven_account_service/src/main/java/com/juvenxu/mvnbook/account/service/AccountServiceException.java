package com.juvenxu.mvnbook.account.service;

public class AccountServiceException
    extends Exception
{
    private static final long serialVersionUID = 949282286716874926L;

    public AccountServiceException( String message )
    {
        super( message );
    }

    public AccountServiceException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}

package com.juvenxu.mvnbook.account.captcha;

public class AccountCaptchaException
    extends Exception
{
    private static final long serialVersionUID = 1339439433313285858L;

    public AccountCaptchaException( String message )
    {
        super( message );
    }

    public AccountCaptchaException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}

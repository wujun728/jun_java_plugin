package com.juvenxu.mvnbook.account.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class AccountCaptchaServiceImpl
    implements AccountCaptchaService, InitializingBean
{
    private DefaultKaptcha producer;

    private Map<String, String> captchaMap = new HashMap<String, String>();

    private List<String> preDefinedTexts;

    private int textCount = 0;

    public void afterPropertiesSet()
        throws Exception
    {
        producer = new DefaultKaptcha();

        producer.setConfig( new Config( new Properties() ) );
    }

    public String generateCaptchaKey()
    {
        String key = RandomGenerator.getRandomString();

        String value = getCaptchaText();

        captchaMap.put( key, value );

        return key;
    }

    public List<String> getPreDefinedTexts()
    {
        return preDefinedTexts;
    }

    public void setPreDefinedTexts( List<String> preDefinedTexts )
    {
        this.preDefinedTexts = preDefinedTexts;
    }

    private String getCaptchaText()
    {
        if ( preDefinedTexts != null && !preDefinedTexts.isEmpty() )
        {
            String text = preDefinedTexts.get( textCount );

            textCount = ( textCount + 1 ) % preDefinedTexts.size();

            return text;
        }
        else
        {
            return producer.createText();
        }
    }

    public byte[] generateCaptchaImage( String captchaKey )
        throws AccountCaptchaException
    {
        String text = captchaMap.get( captchaKey );

        if ( text == null )
        {
            throw new AccountCaptchaException( "Captch key '" + captchaKey + "' not found!" );
        }

        BufferedImage image = producer.createImage( text );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try
        {
            ImageIO.write( image, "jpg", out );
        }
        catch ( IOException e )
        {
            throw new AccountCaptchaException( "Failed to write captcha stream!", e );
        }

        return out.toByteArray();
    }

    public boolean validateCaptcha( String captchaKey, String captchaValue )
        throws AccountCaptchaException
    {
        String text = captchaMap.get( captchaKey );

        if ( text == null )
        {
            throw new AccountCaptchaException( "Captch key '" + captchaKey + "' not found!" );
        }

        if ( text.equals( captchaValue ) )
        {
            captchaMap.remove( captchaKey );

            return true;
        }
        else
        {
            return false;
        }
    }
}

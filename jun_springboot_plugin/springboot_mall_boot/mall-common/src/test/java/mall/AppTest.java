package mall;

import static org.junit.Assert.assertTrue;

import com.mall.common.model.JwtToken;
import com.mall.common.model.Token;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void createToken()
    {
        JwtToken jwtToken = JwtToken.getJwtToken("test");
        Token token = new Token("dsd", 10000);
        String strToken = jwtToken.createToken(token, 50000);
        System.out.println(strToken);

    }

    @Test
    public void parseToke() {
    }
}

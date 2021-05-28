package org.jetbrains.kotlin.demo
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.LoggerFactory

class TokenAuthenticationProvider :AuthenticationProvider {

   var tokenService:TokenService 

    constructor(tokenService:TokenService) {
        this.tokenService = tokenService
    }
    
   override fun authenticate(authentication:Authentication ): Authentication ? {
        val token:String =  authentication.getPrincipal() as String 
        if (!tokenService.contains(token)) {
            throw BadCredentialsException("Invalid token or token expired") 
        }
        return  tokenService.retrieve(token)
    }

    override fun supports( authentication:Class<*>):Boolean {
        return authentication.equals(PreAuthenticatedAuthenticationToken::class.java)
    }
}

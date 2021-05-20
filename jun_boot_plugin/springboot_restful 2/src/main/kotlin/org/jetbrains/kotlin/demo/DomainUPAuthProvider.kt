package org.jetbrains.kotlin.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.web.context.*
class DomainUPAuthProvider:AuthenticationProvider {

    var tokenService:TokenService 
    var config:ConfigInfo
    constructor(tokenService:TokenService,config:ConfigInfo) {
        this.tokenService = tokenService
	this.config = config
    }
   
   override public  fun authenticate(authentication:Authentication ):Authentication? {
        val  username =  authentication.getPrincipal() as String 
        val  password =  authentication.getCredentials()
	val accountpassword =config.getEnv().getProperty("account."+username+".password") 
	var role =config.getEnv().getProperty("account."+username+".role") 
        role = if(role == null)"ROLE_DOMAIN_USER" else role

        if (accountpassword == null || !accountpassword. equals(password )) {
            throw BadCredentialsException("Invalid username or password") 
        }
        val  resultOfAuthentication =  AuthenticationWithToken(username, password,AuthorityUtils.commaSeparatedStringToAuthorityList(role))
        val newToken = tokenService.generateNewToken()
        resultOfAuthentication.setToken(newToken)
        tokenService.store(username,newToken, resultOfAuthentication)

        return resultOfAuthentication
    }

    override public fun supports(authentication:Class<*>):Boolean  {
        return authentication.equals(UsernamePasswordAuthenticationToken::class.java)
    }
}

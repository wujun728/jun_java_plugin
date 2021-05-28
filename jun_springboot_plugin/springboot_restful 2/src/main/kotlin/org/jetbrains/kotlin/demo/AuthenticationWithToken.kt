
package org.jetbrains.kotlin.demo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import kotlin.collections.Collection

class AuthenticationWithToken : PreAuthenticatedAuthenticationToken {
    constructor(aPrincipal:Any, aCredentials:Any? ) : super(aPrincipal, aCredentials)

    constructor(aPrincipal:Any, aCredentials:Any?,  anAuthorities:Collection< GrantedAuthority>) :super(aPrincipal, aCredentials, anAuthorities)

    fun setToken(token:String) {
        setDetails(token)
    }

    fun getToken():String  {
        return getDetails() as String
    }
}

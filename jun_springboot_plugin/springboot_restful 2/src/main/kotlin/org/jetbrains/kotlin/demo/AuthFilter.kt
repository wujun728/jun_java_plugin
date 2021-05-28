package org.jetbrains.kotlin.demo
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.util.UrlPathHelper

import org.slf4j.LoggerFactory


class AuthFilter : OncePerRequestFilter {


   var authenticationManager:AuthenticationManager 
   constructor(authenticationManager:AuthenticationManager){
     this.authenticationManager = authenticationManager
   }
    override  protected fun doFilterInternal(request:HttpServletRequest ,
          response : HttpServletResponse , filterChain:FilterChain ){
        val username = request.getHeader("X-Auth-Username")
        val  password = request.getHeader("X-Auth-Password")
        val token = request.getHeader("X-Authorization")
        val resourcePath = UrlPathHelper().getPathWithinApplication(request) 

        if(!resourcePath .startsWith(ApiDomain.HOST_PATH)){
             echo(404,response,"Not Found api")
              return 
        }
       if(resourcePath .endsWith("/authenticate") && request.getMethod().equals("POST") ){ 

          if(username == null || password == null){
             echo(401,response,"Not Found Username or Password")
             return
          }

          val requestAuthentication = UsernamePasswordAuthenticationToken(username, password);
          val  resultOfAuthentication = tryToAuthenticate(requestAuthentication,response) 
          SecurityContextHolder.getContext().setAuthentication(requestAuthentication )           
          if(resultOfAuthentication != null){
             val token = resultOfAuthentication.getDetails().toString()

             echo(200,response,token)
          }
           return
       }


       if(token != null){ 
          val requestAuthentication = PreAuthenticatedAuthenticationToken(token, null)
          val  resultOfAuthentication = tryToAuthenticate(requestAuthentication,response) 
          SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication  )           
          if(resultOfAuthentication == null){
             return
          }
       }else{ 
          echo(401,response,"no auth")
          return
       }

        filterChain.doFilter(request, response)
    }
    fun  echo(status:Int ,response : HttpServletResponse,ret:String){
          /*response.setStatus(HttpServletResponse.SC_OK)*/
          response.setStatus(status)
          response.addHeader("Content-Type", "application/json")
          response.getWriter().print("{\"status\":$status,\"message\":\"$ret\"}")
    }

 fun tryToAuthenticate(requestAuthentication:Authentication,response : HttpServletResponse ):Authentication ? {
    var ret:Authentication? = null 
    try{
       val responseAuthentication  = authenticationManager.authenticate(requestAuthentication)
       if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
             echo(401,response,"no auth")
       }
       ret = responseAuthentication
    }catch(e:org.springframework.security.authentication.BadCredentialsException){
       echo(401,response,e.getLocalizedMessage())
    }
    return ret
    }
}

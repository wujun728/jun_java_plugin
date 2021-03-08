package org.jetbrains.kotlin.demo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
   override    fun configure(http:HttpSecurity )  {

      http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()

      http.addFilterBefore(AuthFilter(authenticationManager()), BasicAuthenticationFilter::class.java)

   }

   @Autowired
   open    fun configureGlobal(auth:AuthenticationManagerBuilder)  {
      /*auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")*/
      auth.authenticationProvider(DomainUPAuthProvider(tokenService(),getConfig())).authenticationProvider(TokenAuthenticationProvider(tokenService()))
   }


   @Bean
   fun tokenService():TokenService  {
      return TokenService()
   }

   @Bean
   fun getConfig():ConfigInfo{
      return ConfigInfo()
   }
}

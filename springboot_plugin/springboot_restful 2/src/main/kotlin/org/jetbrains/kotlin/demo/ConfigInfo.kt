package org.jetbrains.kotlin.demo
import org.springframework.beans.factory.annotation.*
import org.springframework.context.support.*
import org.springframework.stereotype.*
import org.springframework.core.env.Environment
import org.springframework.context.annotation.*
import org.springframework.boot.context.properties.*
@Configuration
@PropertySource("classpath:application.properties")
public class ConfigInfo {
   @Autowired
   lateinit var environment :Environment 

   @Bean
   @Primary
   fun getEnv():Environment {
       return environment 
   }

 
}




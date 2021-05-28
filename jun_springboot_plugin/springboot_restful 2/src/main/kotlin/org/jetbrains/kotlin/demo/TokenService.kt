package org.jetbrains.kotlin.demo
import org.springframework.security.core.Authentication

import java.util.UUID
import java.util.Random
import sun.misc.BASE64Encoder
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TokenService {
   companion object{
      const val Expiration = 60*60*12*1000L
   }

val expiryQueue = DelayQueue<TokenExpiry>()
  

    val  logger = org.slf4j.LoggerFactory.getLogger("tokenService")
val tokens = hashMapOf<String,Authentication>()
val users = hashMapOf<String,String>()
    fun generateNewToken():String {
        val genStr = ""+System.currentTimeMillis()+UUID.randomUUID().toString().replace("-","")
        return BASE64Encoder().encodeBuffer(genStr.toByteArray()).replace("\\s+".toRegex(),"")
    }

    public fun store(user:String,token:String, authentication:Authentication) {
       
          val oldToken = users.get(user)
          if(oldToken != null){
             tokens.remove(oldToken)
          }
          users.put(user,token)

          tokens.put(token,authentication)

          storeExpiry(oldToken,token)
    }
    public fun contains(token:String):Boolean  {
       return tokens.contains(token) 
    }
    fun retrieve(token:String):Authentication ?  {
       return tokens.get(token) 
    }

    @Scheduled(fixedDelay = Expiration/2)
    fun flush(){
       var expiry = expiryQueue.poll()
        while (expiry != null) {
           tokens.remove(expiry.value)
           expiry = expiryQueue.poll()
        }
    }

    fun storeExpiry(oldtoken:String?,token:String){
       var expiry = TokenExpiry(token, Expiration)
       if(oldtoken != null) {
          val oldexpiry = TokenExpiry(oldtoken, Expiration)
          expiryQueue.remove(oldexpiry)
       }
       expiryQueue.remove(expiry)
       expiryQueue.put(expiry)
    }
}

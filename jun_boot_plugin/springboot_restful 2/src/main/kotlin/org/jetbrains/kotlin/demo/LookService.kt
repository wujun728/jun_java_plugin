package org.jetbrains.kotlin.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import java.util.concurrent.CompletableFuture
import org.springframework.stereotype.Component
import org.springframework.security.access.prepost.PreAuthorize 
import org.springframework.boot.json.*
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.module.kotlin.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
   class UserRequestJson(
      @JsonProperty("id") val id: Int,
      @JsonProperty("content") val content: String   
)

class DataVALUENotMATCHException(msg:String) :RuntimeException(msg)
class DataCanNotNullException(msg:String) :RuntimeException(msg)

@Service
class LookService {

   val  logger:Logger = LoggerFactory.getLogger("LookService")

  fun checkJsonNull(json:Map<String,Any>){
     for(it in json){
        if("null".equals(it.value))
          throw DataCanNotNullException("input data value can not null")
          else if(it.value is Map<*,*>) {
             checkJsonNull(it.value as Map<String,Any>)
          }
     }
  }


   fun parseJson(jsonStr:String):Map<String,Any>{
      val json = BasicJsonParser().parseMap(jsonStr)
      checkJsonNull(json) 
      return json
   }

   @Async("taskExecutor")
   fun  findUsers():CompletableFuture<User> {
      val results:User =User(0,"all users") 
      Thread.sleep(5000L)
      logger.info("Looking up " + results)
      return CompletableFuture.completedFuture(results)
   }

   /*@Async*/
   fun  findUserById(id:Long):DataResponse {
      val user = UserRepository.findById(id)
      val ret = DataResponse(message= if(user == null)"no datas" else user) 
      return ret 
   }
   fun  insertUser(msg:String ):DataResponse{

      val retId = UserRepository.insert(parseJson(msg))
      return DataResponse(message= "add, one:$retId")
   }
   fun  updateUser(id:Long,msg:UserRequestJson):DataResponse{
      val ret = UserRepository.update(id,msg)
      return DataResponse(message= "update, one by $id,ret:$ret")
   }
   fun  delUser(id:Long):DataResponse {
      val ret = UserRepository.delById(id)
      return DataResponse(message= "delete, one by $id,ret:$ret")
   }

}

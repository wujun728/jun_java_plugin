package org.jetbrains.kotlin.demo

import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.CompletableFuture
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.*
import org.springframework.security.access.prepost.PreAuthorize 
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.mvc.method.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.HttpRequestMethodNotSupportedException

@RestController
@RequestMapping(value=ApiDomain.HOST_PATH+"/users", produces=arrayOf("application/json"), consumes=arrayOf("application/json"))
class GreetingController {
   @Autowired
   lateinit var look:LookService


    val  logger = LoggerFactory.getLogger("GreetController")
    val counter = AtomicLong()

    @ExceptionHandler
    @ResponseBody
    fun handleServiceException(req:HttpServletRequest , response:HttpServletResponse , e:Exception ):ErrorResponse  
    {
       var status = when(e){
        is AccessDeniedException -> HttpStatus.FORBIDDEN.value()
        is org.springframework.web.bind.MissingServletRequestParameterException -> 400 
	is org.springframework.web.HttpRequestMethodNotSupportedException -> 405
	is java.lang.IllegalArgumentException -> 400
        is org.springframework.http.converter.HttpMessageNotReadableException -> 4002
	is DataCanNotNullException -> 400
	else -> HttpStatus.OK.value()
       }

       if(status != 200){
          var msg = e.message?:e.getLocalizedMessage()
          if(status == 4002){
            msg =   "json data error"
            status = 400
          } 
          val error = ErrorResponse(status,msg)
         response.setStatus(status)
         return error
      }
       else throw e
    }


    @PutMapping("/{id}")
    fun updataUser(@PathVariable id: Long,@RequestBody  account:UserRequestJson) =
           look.updateUser(id,account)


    @PostMapping
    fun addUser(@RequestBody  account:String)= 
           look.insertUser(account)


    @DeleteMapping("/{id}")
    fun delUser(@PathVariable id: Long) =
           look.delUser(id)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long,@RequestParam page:Int,@RequestParam(required=false) limit:Int?) =  
    look.findUserById(id)

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")  
    fun getUsers(@RequestParam page:Int,@RequestParam(required=false, defaultValue = "10") limit:Int,@RequestParam(required=false, defaultValue = "id") sort:String):DataResponse {

        val  domains = look.findUsers()
        val rets = DataResponse(message=arrayListOf(User(0,"all accounts"))) 
        /*CompletableFuture.allOf(domains).join()*/

       return rets 
     }
}

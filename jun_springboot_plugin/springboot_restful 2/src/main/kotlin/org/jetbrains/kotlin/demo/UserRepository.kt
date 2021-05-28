package org.jetbrains.kotlin.demo


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop

import org.jetbrains.exposed.dao.*

open class ResultInterface
data class User(val id: Long, val content: String):ResultInterface()
data class ErrorResponse(val status: Int, val message: String)
data class DataResponse(val status: Int =200, val message: Any)

object Users : Table() {
   val id = long("id").autoIncrement().primaryKey() // Column<Long>
   val name = varchar("name", length = 50) // Column<String>
}

object UserRepository{

   fun handlerData(handMethod: () -> Long):Long{
      var ret:Long = 0
      Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;", driver = "org.h2.Driver")
      transaction {
         create (Users)
         ret =   handMethod() 
      }
      return ret
   }

   fun insert(msg:Map<String,Any>):Long{
     var content:String = msg.get("content") as String
     val ret =  handlerData(
      { ->
         Users.insert {
            it[name] = content
         }get Users.id
      }
     )

     return ret
   }

   fun delById(id:Long):Long{
     return handlerData({ -> 
         (Users.deleteWhere{Users.id eq id}).toLong()}
   )
  } 

   fun update(id:Long,msg:UserRequestJson):Long{
     var content:String = msg.content
      val ret =  handlerData(
      { ->
         (Users.update({Users.id eq id}) {
            it[name] = content
         }).toLong()
      }
      )
      return ret
   }

   fun findById(id:Long):User?{
      Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;", driver = "org.h2.Driver")
      var ret:User? = null
      transaction {
         create (Users)
         Users.select {
            Users.id.eq(id.toString()) 
         }.forEach{

            ret = User(it[Users.id],it[Users.name])
         }

      }
      return ret
   }


}

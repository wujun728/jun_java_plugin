package org.jetbrains.kotlin.demo
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

class TokenExpiry(val value:String,val expiry:Long) :Delayed {

        

     override  fun compareTo(other:Delayed ):Int {

            if (this == other) {
                return 0
            }
            val diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS)
            return  diff.compareTo(0)
        }

      override  fun getDelay(unit:TimeUnit ):Long {
            return expiry - System.currentTimeMillis()
        }

        
}

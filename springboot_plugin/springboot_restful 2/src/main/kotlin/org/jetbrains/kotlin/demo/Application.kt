package org.jetbrains.kotlin.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAsync
@EnableScheduling
open class Application{
@Bean
@Qualifier(value = "taskExecutor")
open fun  taskExecutor(): Executor  {
        val executor:ThreadPoolTaskExecutor = ThreadPoolTaskExecutor()
        executor.setCorePoolSize(2)
        executor.setMaxPoolSize(2)
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("GithubLookup-")
        executor.initialize()
        return executor
 }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}




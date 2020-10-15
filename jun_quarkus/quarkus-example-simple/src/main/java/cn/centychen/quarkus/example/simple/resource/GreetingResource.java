package cn.centychen.quarkus.example.simple.resource;


import cn.centychen.quarkus.example.simple.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author: cent
 * @date: 2019/5/4.
 * @description:
 */
@Path("/hello")
public class GreetingResource {

    @Inject
    private HelloService helloService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public CompletionStage<String> hello(@PathParam("name") String name) {
        //使用异步响应
        return CompletableFuture.supplyAsync(() -> helloService.sayHello(name));
    }
}

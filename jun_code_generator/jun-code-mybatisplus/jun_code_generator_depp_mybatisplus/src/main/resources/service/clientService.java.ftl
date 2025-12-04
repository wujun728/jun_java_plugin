package com.dcy.client;

import com.dcy.api.InterfaceService;
import com.dcy.api.service.${entity}RemoteService;
import org.springframework.cloud.openfeign.FeignClient;

/**
* @Author：${author}
* @Description:  ${table.comment!}暴露远程接口+熔断
* @Date: ${date}
*/
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = ${entity}ClientService.${entity}ServiceFallback.class)
public interface ${entity}ClientService extends ${entity}RemoteService {



    class ${entity}ServiceFallback implements ${entity}ClientService {


    }
}

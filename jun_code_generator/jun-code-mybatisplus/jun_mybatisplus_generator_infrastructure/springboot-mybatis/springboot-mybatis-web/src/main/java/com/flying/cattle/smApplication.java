/**
 * @filename: WalletSignApplication.java 2019-04-16
 * @project wallet-sign-web  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明：启动类</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version        Description
 *-------------------------------------------------------------*
 * 2019-04-16     	flying-cattle    	V1.0           initialize
 */
@SpringBootApplication
public class smApplication {
	/**
     * <p>分页插件</p>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(smApplication.class, args);
	}
}

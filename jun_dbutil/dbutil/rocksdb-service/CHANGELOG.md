2017-05-26
     
     db数据清除指令；提供批量删除的指令；
     db数据备份指令；完成系列 api
         
2017-05-25
    静态资源处理
    管理首页  http://127.0.0.1:9000/
        
2017-05-24
    采用简单策略；json 数据类型支持，默认为 json 数据格式；
    通过 json 数据类型支持 Hash 类型；
    一个实例一个数据表；
    
    restful 支持 json
    http://127.0.0.1:9008/api/mydb/abc
    
    get http://127.0.0.1:9008/api/mydb/a
    put http://127.0.0.1:9008/api/mydb/abc {"a"=12,"b"=c}
    del http://127.0.0.1:9008/api/mydb/abc
    
    get http://127.0.0.1:9008/api/mydb?pre=ab  根据前缀查询 key
    post    http://127.0.0.1:9008/api/mydb/  {a:{"a":1},b:{b:1}}
    
    

2017-05-23
    

        
    数据路径通过配置文件加载；
    使用外部配置文件
        java -jar rocksdb-service-0.1.0.jar --spring.config.location=location_of_your_config_file.properties
    使用Profile区分环境
        @Service
        @Profile("dev")
        class DevEmailService implements EmailService {
        
            public void send(String email) {
                //Do Nothing
            }
        }
        
        @Service
        @Profile("prod")
        class ProdEmailService implements EmailService {
        
            public void send(String email) {
                //Real Email Service Logic
            }
        }
        
        @Profile("dev")表明只有Spring定义的Profile为dev时才会实例化DevEmailService这个类。那么如何设置Profile呢？
        在application.properties中加入：
            spring.profiles.active=dev
        通过命令行参数
            java -jar app.jar --spring.profiles.active=dev
        
    
2017-05-22
    nginx+lua 实现集群与鉴权
    数据库状况监控
    http://127.0.0.1:9000/rocksdb/status
    
    restful 获取一个 key 的 value
    http://127.0.0.1:9008/api/mydb/ab
    
    get http://127.0.0.1:9008/api/mydb/ab
    put  body=  http://127.0.0.1:9008/api/mydb/ab
    delete http://127.0.0.1:9008/api/mydb/ab
2017-05-20
    RatPack 可用于高性能的 API 封装（如果没有合适的 lua 库的情况下）
    引入 RatPack 进行性能优化，比原生的 SpringBoot 快三倍；
    基于 SpringBoot 嵌入式启动 RatPack;
    编写基于 Ratpack 的 api;
    
    
2017-05-18
    主要的性能方面的优化配置；
    
2017-05-18
    RocksjavaTest 测试 OK

    API
    http://127.0.0.1:9000/hello-world?name=abc
    
    健康监测
    http://localhost:9001/health
    
    存储一个数据
    http://127.0.0.1:9000/rocksdb/set?key=ab&value=%E4%B8%AD%E6%96%87
    获取一个数据
    http://127.0.0.1:9000/rocksdb/get?key=ab
# infrastructure
以Spring Boot整理一些基础架构集合（聚合工程）

# 使用说明
<p>item-parent：<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 是一个父级项目，打包方式是POM，作用：控制其下所有子项目的jar包版本，利于项目包版本统一、项目版本统一升级，降低所用包版本迭代快的影响。</p>

<p>item-common:<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 这个就不用多介绍了，你认为你每个项目都用的到的，就可以扔进去，包括JAR包。</p>
        
<p>springboot-mybatis：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;集成最全的代码生成工具：entity集成lombok、格式校验，swagger；dao自动加@mapper，service自动注释和依赖；Controller实现restful 增删改查API，并集成swagger与生成配置文件。

<p>webFluxTest：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;主要是对spring5的新web框架webflux做了测试性的使用，包含redis-reactive整合与使用，mongodb-reactive的整合与使用。
<p>webFlux_websocket：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;主要是对spring5的新web框架webflux和websocket整合使用，使添加消息处理，和定点消息交互更加简单。

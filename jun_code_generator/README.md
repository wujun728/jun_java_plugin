# jun_generator 代码生成器

#### 基础篇(工具)：代码生成器（jun_code_generator）


---jun_code_generator
123\
--
base-admin\ 说明：boot的，前端layui很乱，废弃，后端jpa，整合到bootapi里面，作为API接口调用实现
code_generator\    说明：xxl的，SQL转crud的，删掉
code-general_mybatis-puls\ 删掉
code-generator-parent\ 干掉，当做源码合入
coderfun-bom\    同步fieldmeta，一起处理
fieldmeta\      同步coderfun-bom，一起处理
klg-jpa\         同步coderfun-bom，一起处理
xutils\     同步coderfun-bom，一起处理
template-ssje\         同步coderfun-bom，一起处理
 
doc\
code_freemarker\   合并到code_generator
code_template\   干掉
springboot_code_generator\ 合并到code_generator
springboot_codegenerator\ 合并到code_generator
springboot_codegenerator555\ 合并到code_generator
SpringBootCodeGenerator\ 合并到code_generator
xxl-deep2\ 合并到code_generator
.gitignore
localhost111.zip 合并到code_generator，页面
localhost192.zip 合并到code_generator，页面
easy-code\   拆掉，合并到ssm里面
freeter_generator\    合并到jun_code_generator
freeter-admin\  拆掉，合并到jun_boot里面
Hplus-v.4.1.0后台框架@www.java1234.com\ 迁移到front里面
layuimini\   迁移到front里面
MakeCode\    迁移到code_mybatisplus
min-io-springboot2.x\   迁移到plugin里面
nep-admin\ 迁移到front里面
oa_system\   迁移到OA
ok-admin\ 迁移到foont里面
WhatRuns chrome插件
springBoot\  迁移到plugin里面
spring-oauth-server\    迁移到plugin里面
VIEWUI-FOR-EASYUI\    迁移到foont里面
VIEWUI-FOR-EASYUI2\ 迁移到foont里面
X-admin\   迁移到foont里面
X-SpringBoot\ 迁移到jun_boot里面
xxl-deep\   干掉
yuxuntoo_generator\   干掉，rrg
 
doc\
--
codeGenerator\ 干掉，ry
code-generator-parent\     干掉
code-template\   保留的
codeutil\    保留
dp-generator\    迁移到ssm
dp-security\     迁移到ssm
rapid_generator\   保留
roncoo-adminlte-springmvc\   合并到ssm里面，jetty，剥离前端到nginx里面，后端合并到ssm
ssm\   合并到ssm里面
renren_generator-master    合并到code_generator
form_generator\    暂不处理
jun_code_generator\ 暂不处理
jun_excel_to_table\     暂不处理
jun_maven_template\ 暂不处理
jun_mybatis_generator\ 暂不处理
jun_mybatisplus_generator\ 暂不处理
 
前端模板-合并到front里面
https://github.com/wenfengSAT/wenfengSAT-UI
 
迁移到CRM里面
https://github.com/wenfengSAT/SpringbootCRM
 
迁移到plugin里面
https://github.com/wenfengSAT/wenfengSAT-SpringBoot
 
迁移到uniapp里面
https://github.com/fanchaoo/netease-cloud-music-community
 
 
 
 
 
待处理：
https://github.com/lerry903/spring-boot-api-project-seed
--合并到jun-boot里面
https://github.com/lerry903/spring-boot-cloud
--合并到jun-cloud里面
https://github.com/jackying/H-ui.admin
--合并到jun-front
https://github.com/xiaoshaDestiny/spring-cloud-2020
迁移到cloud里面
 
https://github.com/stylefeng/Guns
---layui+boot，迁移到boot里面
https://github.com/jsnjfz/WebStack-Guns
https://github.com/1477551037/exam
https://github.com/itd2008/My-Blog
https://github.com/qiaokun-sh/spring-token
 
https://github.com/wujun728/inspinia_admin_java_ssm
 
https://github.com/xwjie/ElementVueSpringbootCodeTemplate
https://github.com/RudeCrab/rude-java
https://github.com/Wjhsmart/Front-end-UI
https://github.com/zongjl/JavaWeb
https://github.com/zongjl/Jeebase
https://github.com/xzt1995/nideshop-springboot
 
fsLayui
VIEWUI-FOR-EASYUI
spring-boot-starter-motan
java
layoutit
Personnel-Management-System
fiction_house
inspinia_admin_java_ssm
springboot-mui
Jobs-search
xxyms
 

重置master分支：
git checkout --orphan latest_branch2
git add -A
git commit -am "commit message"
git branch -D master
git branch -m master
git push -f origin master 
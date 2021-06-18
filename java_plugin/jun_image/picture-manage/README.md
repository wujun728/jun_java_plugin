# 文件图床系统 :kiss:

#### 介绍​ :point_right:

文件图床系统是一套独立的专门应对在云服务器上的文件管理操作与图片管理的一套系统。

系统功能可进行文件的上传、删除、移动、创建文件夹、在线预览、批量处理 等操作...

可应用在个人博客上的图床系统、云服务器文件管理器...



##### 使用方法

1.  Win部署：

   ​	在Win下已经打包好了exe 文件 ``picutre-win-runner.exe`` 双击执行此文件等待窗口打开后，稍等片刻及	运行成功在浏览器中输入: ``localhost:8080`` 即可访问，在 .exe 同级目录下会生成名为**h2** 数据库文件夹，	如果删除此文件夹则可重新重置应用数据。



2. Linux下部署方案

   系统首先需要安装 JDK8 运行环境，安装成功后下载提供打包完成的 Jar 包文件并上传到您的服务器上，接下来执行启动命令，这里作者提供了运行的shell脚本。

   执行命令``sudo sh run.sh picture-manage-1.0.0.jar logs`` 即可后台运行此程序。

   请确保您的8080端口处于打开状态，之后在浏览器输入  服务器IP地址:8080/  进行访问。

   第一个参数是运行的Jar路径，第二个是输出.out日志的名称。
   



​		

​		**:fist_right: Linux 与 Win 软件包下载地址：** https://gitee.com/Jack-chendeng/picture-manage/releases/v1.0



3. Docker下部署方案 **(推荐)**  

   1. 系统确保安装了Docker环境

   2. 执行命令启动容器

      ````shell
      sudo docker run --name my-pm -d -p 8080:8080 -v /mnt/upload:/home registry.cn-beijing.aliyuncs.com/yustart/picture-management:1.0
      ````

      参数解释：

      ​	``-p 8080:8080`` :arrow_forward:宿主主机端口映射到容器的端口 ，这里是讲宿主主机8080映射到容器的8080

      ​	``-v /mnt/xxxx:/home``  :arrow_forward: 宿主主机目录挂载在容器目录中，这里/home 是固定的不可更改

   3. 启动成功后可通过 ``docker ps`` 查看正在运行的容器。

   4. 打开浏览器输入 服务器IP地址:8080 进行访问。


 **默认登录用户名和密码为:** ``root``


![](./readme/1.png)

![](./readme/2.png)

![](./readme/3.png)

![](./readme/4.png)

![](./readme/5.png)
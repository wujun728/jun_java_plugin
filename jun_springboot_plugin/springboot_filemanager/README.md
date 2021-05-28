#spring-boot-filemanager
方便有文件管理功能的项目集成,不依赖后端,目前只集成了SpringBoot实现
###功能介绍
* 前后端分离,方便集成到自己的熟悉语言项目中
* 支持选择回调,如弹框文件选择
* 多语言支持
* 支持多种文件列表布局（图标/详细列表）
* 多文件上传
* 支持文件搜索
* 复制、移动、重命名
* 删除、修改、预览、下载
* 直接压缩、解压缩zip文件(目前仅支持zip，后续扩展)
* 支持设置文件权限(UNIX chmod格式)
* 移动端支持


#[更多介绍请访问](http://shaofan.org/angular-filemanager/)

#运行
* 检出项目 <code class="code-normal">git clone https://git.oschina.net/alexyang/spring-boot-filemanager.git</code>
* 再项目根目录执行 <code class="code-normal">mvn spring-boot:run</code> 或运行<code class="code-normal">SpringBootFileManagerApplication</code>

#编译angular-filemanager

> 其实利用webjars可以做到用java去编译，以后空了再弄吧。

* 编译需要用到<code class="code-normal">node.js</code>和<code class="code-normal">gulp</code>模块
* 先安装全局gulp模块 <code class="code-normal">npm install -g gulp</code>
* 然后在项目根目录执行<code class="code-normal">npm install</code>
* 最后打包编译到dist目录下 <code class="code-normal">gulp build</code>




**目录**

- 一、Nodejs介绍
  - [1、JavaScirpt介绍](https://www.cnblogs.com/xiugeng/p/9611254.html#_label0_0)
  - [2、node介绍](https://www.cnblogs.com/xiugeng/p/9611254.html#_label0_1)
- 二、nodejs中npm
  - [1、npm是什么？](https://www.cnblogs.com/xiugeng/p/9611254.html#_label1_0)
  - [2、npm安装/更新/初始化](https://www.cnblogs.com/xiugeng/p/9611254.html#_label1_1)
- 三、使用npm安装和卸载模块
  - [1、下载jquery包](https://www.cnblogs.com/xiugeng/p/9611254.html#_label2_0)
  - [2、下载不同版本的模块](https://www.cnblogs.com/xiugeng/p/9611254.html#_label2_1)
  - [3、卸载模块](https://www.cnblogs.com/xiugeng/p/9611254.html#_label2_2)
  - [4、使用cnpm（淘宝镜像）](https://www.cnblogs.com/xiugeng/p/9611254.html#_label2_3)

 

**正文**

[回到顶部](https://www.cnblogs.com/xiugeng/p/9611254.html#_labelTop)

## 一、Nodejs介绍

　　Nodejs英文网：https://nodejs.org/en/

　　Nodejs中文网：http://nodejs.cn/

Node.js 是一个基于 Chrome V8 引擎的 **JavaScript 运行环境**。 
Node.js 使用了一个事件驱动、非阻塞式 I/O 的模型，使其轻量又高效。 
Node.js 的包管理器 npm，是全球最大的开源库生态系统。



### 1、JavaScirpt介绍

#### （1）什么是JavaScript？

　　javascript是一门运行在浏览器端的脚本语言，用来做客户端页面的交互。

#### （2）JavaScript的运行环境呢？

　　故名思意他的运行环境就是浏览器，但是真的是这样的吗？

　　其实不然，他的真正运行环境其实是运行在浏览器内核中的js引擎。

　　为什么是这样的了？因为通俗来讲浏览器的作用就是用来浏览网页的，我们在浏览器除了执行js外还能执行其他得东西，比如我们在浏览器输入一个地址敲回车就可以发送请求并且接收服务器得响应。所以说浏览器的第一功能是请求一个http地址，也就是可以封装一个请求报文出来，将一个url的地址封装成一个请求报文，这个报文到服务端，然后给我们一个响应报文，然后在将响应解析出来，这也是浏览器的最大的作用。

　　当然服务器响应的内容有可能不一样，比如说服务器返回一个html文件，css文件，img文件，用来渲染，我们称之为渲染引擎，除此之外，还可以执行js，由js引擎来完成，因此可以说javascript的运行环境是浏览器中的js引擎，而不是浏览器。浏览器是一个大的概念。

#### （3）浏览器中的javascript可以做什么

　　javascript就是用来做交互的，但是笼统的说交互有点不明确，具体一点可以分为：

　　　　1）操作dom（也就是对dom的增删改，注册事件之类的事情）

　　　　2）发送AJAX请求/跨域

　　　　3）BOM交互如给我们提供页面跳转，历史记录，控制台打印日志

　　　　4）ecmascript(js的核心语言，如用来定义变量，函数等)

#### （4）浏览器端的javasscript不可以做什么？

　　貌似javascrip对我们日常的交互都可以完成，貌似什么都可以做，但是他也有不能做的事。如：　　

　　1）涉及到端对端的应用程序，我们需要操作文件，浏览器中的javascript是不能进行文件操作的，虽然h5里面提供了关于文件相关的API，但是这些API大多数只限于只读的层面 ，不能像传统的语言如java,通过传一个路径，然后将对应的文件读出来，说白了就是不能进行文件和文件夹的CURD.　　　　

　　2）浏览器端的javascript也没有办法去操作操作系统，如获取操作系统的版本之类的。

　　**那么为什么不能进行这两类操作**？　　

　　其实是出于安全考虑，因为，js这门语言运行的环境比较特殊，说他特殊，特殊在什么地方？　　

　　虽然我们编写好的js代码最终会放在服务器上，但是他毕竟不是在服务器上执行的，而是通过服务器发送到浏览器端执行的，在浏览器端执行文件的操作显然是不安全的，因此说这些功能在客户端不是不能做，而是由于特殊的运行环境没法做。

#### （5）了解了客户端的js所能做的事,javascrip只能运行在浏览器端吗？

　　前端开发人员都知道，javascrip是有ecmascrip语言，BOM，DOM组成的，在语言层面，她只是给我们提供一些操作语法，如定义变量，函数，类型，流程控制等的操作。而BOM，DOM是浏览器提供的，并非es提供的。因此我们常提及的js其实就是es，js的大部分功能（DOm，BOM（浏览器开放出来的API）等的操作）都是由 浏览器的执行引擎决定的 ，这也衍生出一个观点，任何一门编程语言 ，他的能力不是由语言本身决定的，而是由他的执行环境决定的。

　　比如说java，他即是一门语言也是一个平台，对于javascript来说语言就是es，平台是浏览器。那么js只能运行在浏览器中吗？非也！对于大多数语言，都是运行 在一个平台上的，比如java只运行在虚拟机上，但是也有运行在多个平台的语言, java在一定层面上来讲是没有必要运行在多个平台上的，因为虚拟机是跨平台（也就是跨操作系统如window，linux等）的。

　　js同样是可以运行在多个平台的，浏览器之所以能过运行js，是因为他由js的执行引擎。js同样，只要由支持他的平台就可以执行。因此说要想语言有很强大的功能，只需要提供强大的平台，node就是这样一个平台，能够执行js。



### 2、node介绍

#### （1）什么是node？

　　根据官方文档可以知道，node就是一个给予谷歌v8引擎的一个javascript的运行时，可以理解为运行js的一个虚拟机。他使用的是一个事件驱动，非阻塞I/O模型 ，他是将js的运行环境搬到了服务器端，和客户端没有一点关系。是一个纯服务端的东西，node只是为js提供了一个平台。

　　node里面其实还分了两块，一是封装了v8引擎，目的是为了执行es（如定义变量，定义函数等），另外一个提供了大量的工具库,是帮助node实现各种功能的，提供了一些以前js的环境办不到的事情，比如文件操作，网络操作，操作系统的操作。

　　既然node是一个平台（所谓的平台就是用来运行特定语言的），也就意味着node是用来运行语言的，那么java也是语言，node能运行java吗？据nodejs创始人Ryan Dahl回忆，他最初是选择了Ruby这门语言，但是Ruby这门语言的虚拟机效率不怎么样最终放弃了，按照这种思路，貌似node将java的虚拟机集成进来应该可以运行java，但node作者最终选择了javascript。

　　这样js就实现了在服务端运行的可能，js运行在node平台上（分为v8部分，用来执行es，和大量的工具库组件（API）称之为libuv，提供了以前js的环境办不到的事，如文件操作，网络操作等等）。

#### （2）node在web中有什么用途？

　　（1）node可以**接受客户端用户的所有请求**，并且能够快速的给出响应，因此node可以用来做网站。

　　（2）node可以作为一个**中间层来来分发调用数据接口**，比如有一个网站数据是有java提供的，我们可以让node作为一个中间层，来接受用户的请求，然后通过node来调用java数据接口，获取到数据后直接在node层面做html的瓶装，然后将渲染好的页面直接给用户。为什么要这样做，直接请求java接口不行吗，这是因为node被称之为高性能的web服务器，在并发和抗压方面都比传统的平台要好很多，因此这样一包装可以极大的减轻服务器的开发。

　　通过上面的两点，可以总结出，node在web中要么从前端页面到后端服务全包了，一个是只做其中的一点。

　　一言以蔽之，node就是一个javascript的运行环境（平台），他不是一门语言，也不是javascript的框架。可以用来开发服务端应用程序，web系统。其特点是体积小，快速，高性能。

[回到顶部](https://www.cnblogs.com/xiugeng/p/9611254.html#_labelTop)

## 二、nodejs中npm



### 1、npm是什么？

　　![img](https://images2018.cnblogs.com/blog/1311506/201809/1311506-20180909003318876-516381287.png)

　　简单的说，npm就是JavaScript的包管理工具。类似Java语法中的maven，gradle，python中的pip。



### 2、npm安装/更新/初始化

　　官网地址：https://nodejs.org/en/

　　在这里虽然node.js更新到了8.11.1的版本，还是使用6.10.3版本。

　　![img](https://images2018.cnblogs.com/blog/1311506/201809/1311506-20180909003609995-477170440.png)

#### （1）安装

　　使用mac电脑选择[node-v6.10.3.pkg](https://nodejs.org/download/release/v6.10.3/node-v6.10.3.pkg) 下载安装。　　

　　npm是和Nodejs一起并存的，只要安装了Nodejs，npm也安装好了，安装好Nodejs之后。打开终端，执行如下命令，检查是否安装成功。

```
MacBook-Pro:~ hqs$ node -v``v6.10.3``MacBook-Pro:~ hqs$ npm -v``3.10.10
```

#### （2）npm更新

　　由于npm自身的更新频率比Node.js高很多，所以通过上面安装的npm可能不是最新版本，可以通过下面的命令单独更新npm。在这里不建议更新。

```
$ sudo npm install npm@lastest -g
```

#### （3）npm初始化

　　在去下载包之前，首先去当前项目的包进行初始化操作，执行命令：`npm init。`

　　运行这个命令后，它会询问一些关于包的基本信息，根据实际情况回答即可。

　　如果不喜欢这种方式，可以使用`npm init --yes`命令直接使用默认的配置来创建`package.json`文件，最后根据需要修改创建好的`package.json`文件即可。

[+ View Code](https://www.cnblogs.com/xiugeng/p/9611254.html#)

　　生成的package.json文件，主要字段的含义如下：

```
name: 模块名, 模块的名称有如下要求:``  ``全部小写``  ``只能是一个词语，没有空格``  ``允许使用破折号和下划线作为单词分隔符``version: 模块版本信息``description：关于模块功能的简单描述，如果这个字段为空的话，默认会从当前目录的READMD.md或README文件读取第一行内容作为它的默认值。``main: 模块被引入后，首先加载的文件，默认为index.js。``scripts: 定义一些常用命令入口` `关于最后一个英文的意思，我们可以证明，当我执行npm init之后，会自动的生成``package``.json的文件。
```

[回到顶部](https://www.cnblogs.com/xiugeng/p/9611254.html#_labelTop)

## 三、使用npm安装和卸载模块

　　使用`npm install`会读取`package.json`文件来安装模块。

　　安装的模块分为两类`dependencies`和`devDependencies`，分别对应生产环境需要的安装包和开发环境需要的安装包。 同样在安装模块的时候，可以通过指定参数来修改package.json文件，以jquery和webpack做例子。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
$ npm install vue --save    # --save:让vue包下载到当前目录下
npm_study@1.0.3 /Users/hqs/PycharmProjects/ES6_demo
└── vue@2.6.10

$ npm install jquery --save     
$ npm install webpack --save-dev 
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

　　执行后，会将新安装的模块信息记录到**`package.json`**文件（该文件主要负责项目包的管理）：

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  "name": "npm_study",    # 模块名
  "version": "1.0.3",     # 项目版本号
  "description": "学习npm",      # 项目描述
  "main": "index.js",     # 项目入口文件
  "scripts": {            # 项目脚本
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [
    "123"
  ],
  "author": "hqs",        # 项目作者
  "license": "ISC",       # 认证证书
  "dependencies": {       # 当前项目依赖
    "jquery": "^3.3.1",
    "vue": "^2.6.10"
  }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)



### 1、下载jquery包

```
hqs$ npm install jquery --save``npm_study@1.0.3 /Users/hqs/PycharmProjects/ES6_demo```-- jquery@3.3.1`` ` `npm WARN npm_study@1.0.3 No repository field.
```

　　执行以上命令，便可以安装对应的包到执行命令的当前目录，并创建一个`node_modules`的文件夹,然后把需要安装的安装包下载到里面。同时package.json文件的dependencies配置更新。 　　

　　![img](https://images2018.cnblogs.com/blog/1311506/201809/1311506-20180909005040009-600134031.png)



### 2、下载不同版本的模块

　　在npm install安装时，可以通过**@**来指定下载的模块版本。

```
MacBook-Pro:ES6_demo hqs$ npm install bootstrap@3.1.1 --save``npm_study@1.0.3 /Users/hqs/PycharmProjects/ES6_demo```-- bootstrap@3.1.1`` ` `npm WARN npm_study@1.0.3 No repository field.
```

　　`node_modules和package.json变化如下：`

　　![img](https://images2018.cnblogs.com/blog/1311506/201809/1311506-20180909122119754-254821428.png)



### 3、卸载模块

```
MacBook-Pro:ES6_demo hqs$ npm uninstall bootstrap --save    ``- bootstrap@3.1.1 node_modules/bootstrap``npm WARN npm_study@1.0.3 No repository field.
```

　　变化效果如下：

　　![img](https://images2018.cnblogs.com/blog/1311506/201809/1311506-20180909122220661-899369275.png)



### 4、使用cnpm（淘宝镜像）

　　使用npm下载依赖时，由于是从国外的网站上下载内容，所以可能经常会出现不稳定的情况，所以需要下载cnpm代替npm，cnpm是国内淘宝的做的，在国内使用稳定。 

　　1.下载cnpm

```
npm install -g cnpm --registry=https:``//registry.npm.taobao.org
```

　　2.使用cnpm

```
cnpm install jquery --save
```
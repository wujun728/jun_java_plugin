# 文档离线版

## 使用码云pages服务

- 把pagesmanager-dest文件夹上传到git
- 在码云上开启pages服务，部署目录填：`pagesmanager-dest/docs`

## 本地查看文档

- 前提：先安装好npm，[npm安装教程](https://blog.csdn.net/zhangwenwu2/article/details/52778521)。建议使用淘宝镜像。
- 安装docsify，执行npm命令`npm i docsify-cli -g`。(安装了淘宝cnpm可以执行`cnpm i docsify-cli -g`)
- cd到当前目录，运行命令`docsify serve docs`，然后访问：`http://localhost:3000`即可查看。
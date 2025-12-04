# [docker安装Redis并设置密码](https://www.cnblogs.com/zhangzimo/p/12753563.html)

## 1.搜索镜像

```
docker search redis
```

## 2.拉取镜像

```
docker pull redis
```

## 3.创建Redis容器并设置密码

```
docker run --name redis -p 6380:6379 redis-test --requirepass 123456

docker run -itd --name redis-v4-6379 -p 6379:6379 redis:4.0

docker run -itd --name redis-6379 -p 6379:6379 redis:5.0  --requirepass 123456

#前边是宿主机端口 后面是docker使用的端口
```

## 4.备注

为现有的redis创建密码或修改密码的方法：

1.进入redis的容器 docker exec -it 容器ID bash

2.进入redis目录 cd /usr/local/bin 

3.运行命令：redis-cli

4.查看现有的redis密码：config get requirepass

5.设置redis密码config set requirepass ****（****为你要设置的密码）

6.若出现(error) NOAUTH Authentication required.错误，则使用 auth 密码 来认证密码
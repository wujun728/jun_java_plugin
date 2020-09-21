# redisSky
redis web 管理工具, 参考 redisMaster, 该项目主要是因为使用 redisMaster 处理大数据量的时候容易卡死, 很不爽, 所以用 ivew + golang 仿写了一个 web 界面的 redis 管理工具

## 不兼容 ie, 作为一个程序员不应该用ie
- 前端使用 iview + socket.io
- 后端使用 golang + socket.io

## Demo 地址(使用免费的阿里云ECS)：
` 帮你打广告了，请给我 5 毛广告费`
> http://59.110.239.205

## 联系邮箱：
prettyyjnic@qq.com

## 特性：
1. 使用 redis scan 系列命令进行加载，避免 超大 key 加载慢
2. 支持批量导出 key 到 mongodb
3. 滚动加载更多key
4. 批量删除key
5. 简洁美观的界面

## 使用：
```
cd $项目根目录/frontend 
npm install # 安装依赖
npm run build # 前端代码编译
cd $项目根目录/backend/bin && go run start.go # 启动服务
```

## 截图：
![image](http://59.110.239.205/screenShot/screenShot.png)
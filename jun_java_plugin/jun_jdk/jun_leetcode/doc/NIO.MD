# 建立非阻塞NIO流程

客户端：

- 获取通道
   
   - TCP ```SocketChannel```
   - UDP ```datagramChannel ```
   - 设置非阻塞```configureBlocking(false)```

- 建立Buffer```ByteBuffer.allocate(1024)```

- 将Buffer数据写入Channel
   
   - TCP 
   ```
   buf.put(data);
   buf.flip();
   socketChannel.write(buf);
   ```
   - UPD
   ```
   byteBuffer.put(data);
   byteBuffer.flip();
   datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9898));
   ```
  
  服务端：
  
- 获取通道
   - TCP ```ServerSocketChannel```
   - UDP ```datagramChannel ```
   - 设置非阻塞```configureBlocking(false)```
   - 绑定端口 ```bind(new InetSocketAddress(9898));```
- 获取选择器
     - 将通道注册到选择器上
   ```channel.register(selector,SelectionKey.OP_ACCEPT)```
   此处可以监听通道的四种状态：
         - SelectionKey.OP_ACCEPT 准备就绪
         - SelectionKey.OP_WRITE 可读
         - SelectionKey.OP_ACCEPT 可写
         - SelectionKey.OP_CONNECT 连接上
 - 轮询选择器中的事件返回一个Set,对各种事件进行处理
    
 ``` 
 //TCP
if(selectionKey.isAcceptable())
               {
                   //获取客户端的连接
                   SocketChannel socketChannel = serverSocketChannel.accept();
                   //切换成非阻塞模式
                   socketChannel.configureBlocking(false);
                   //注册到选择器上
                   socketChannel.register(selector,SelectionKey.OP_READ);
               }
               else if(selectionKey.isReadable())
               {
                   SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                   //读取数据
                   ByteBuffer buf =ByteBuffer.allocate(1024);
                   int len = 0;
                   while((len = socketChannel.read(buf))!=-1)
                   {
                       buf.flip();
                       System.out.println(new String(buf.array(),0,len));
                       buf.clear();
                   }
               }
```


   

   
```
//UDP
   while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        datagramChannel.receive(byteBuffer);
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                    //使用完毕后取消Key
                    iterator.remove();
                }
            }
```
  

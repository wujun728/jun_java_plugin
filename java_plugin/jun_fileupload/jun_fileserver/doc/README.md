# LFS
File System（就是要最简单的，而且是最快的）

######
    最新版貌似更快了……为虾米呢？最新数据是 24秒，快了 4秒（加了并发"行级锁"之后，怎么可能会更快？一定有其他地方的优化，一定……）
    和 C 直接读写文件速度几乎一样，意味着几乎没有额外 CPU 消耗，还用过多的解释吗？

非常非常快的文件系统，可以同时存储海量大文件和小文件，高并发<br>
存储视频音乐和图片等非常棒<br>
并且可以作为列存储<br>


链接：[github](https://github.com/ikCourage/LFS)  [git@osc](https://git.oschina.net/ikCourage/LFS_FREE)<br>

示例：（这不仅是极致简单的，而且是最快的）

```java
//上传文件，只需一行
//？如果文件特别大怎么办，是不是需要切片？
//：为什么需要呢？依然只有这一行，10G? 100G? 1T? 都如你所愿
//？是否会有碎片？
//：1 个字节都木有
//？是否支持断点上传？会不会复杂？
//：如果我告诉你，依然只有这一行呢？仅仅多了两个参数而已
FileInputStream inputStream = new FileInputStream(file);
fileId = LFS_Stream.writeStream(FILE_NAME, fileId, inputStream, file.length());
```

```java
//下载文件，依然只需一行
//？弱弱的问下，断点下载也很简单哈？
//：如你所想，再多 1 个 或 2 个参数就好
LFS_Stream.readStream(FILE_NAME, fileId, readStream);
```

```java
//但是 IReadStream 需要自己设计（读出来的数据都通知给了 IReadStream，所以你自己得知道用数据干嘛才行，不是吗）
//这里是一个通过 http 显示图片的示例（竟如此简单，而且非常快）
IReadStream readStream = new IReadStream()
{
	public boolean init(long fileId, int size, long sizeTotal, long sizeTotalRead, long offset)
	{
		response.setIntHeader("Content-Length", (int)sizeTotalRead);
		response.setHeader("Cache-Control", "max-age=604800");
		response.setIntHeader("Etag", 0);
		return true;
	}

	//参数好多啊，不要被吓到了，只有前两个是你需要用的
	//其他的只是为了避免全局变量而已（如果需要的话，所幸，绝大多数场景都不会需要）
	public boolean parseData(byte[] b, int bytesAvalibale, int size, long sizeTotal, long sizeTotalRead, long sizeTotalReaded, long offset)
	{
		try {
			//这里应实现自己的数据输出，比如输出到 http
			response.getOutputStream().write(b, 0, bytesAvalibale);
			return true;
		} catch (Exception e) {}
		return false;
	}
};
```

脚本语言使用 lua，why?因为快且小，而且非常简单<br>

指南针 <img src="http://pic.sucaibar.com/pic/201307/16/1c1cbb2ccd.png" width="50" height="50">
-


#####设置端口号
LFS --port 9002<br>

#####设置 IP（如：ipv6，默认同时侦听 ipv4 ipv6）
LFS --host ::<br>

#####设置文件存储目录
LFS --dir Storage<br>

#####打开打印模式
LFS --print<br>

#####守护进程
LFS --daemon<br>

#####设置 Socket 的收发缓存大小
LFS --inBufferSize 102400 --outBufferSize 102400<br>


测试数据
-

系统：OS X Yosemite<br>
处理器：2 GHz Intel Core i7<br>
内存：4 GB 1333 MHz DDR3<br>
硬盘：Hitachi 500 GB SATA 磁盘<br>

写入 27.5 万个分词，并构建索引（写入前先查询索引，去重）：<br>
* `全部用时：28.246 s（含本机通信用时）`
* `服务用时：28.228273082 s`

根据索引读取：<br>
* `全部用时：2 ms（含本机通信用时）`
* `服务用时：0.328847 ms`

遍历查询（第 275542 项，共 275714 项）<br>
* `全部用时：2072 ms`
* `服务用时：2071.948199 ms`



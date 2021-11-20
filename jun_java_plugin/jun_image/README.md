1. 它是什么？
-----------------
		这是一个非常简单的图片处理的插件，可快速集成在你的web应用中。基于http对图片进行缩略大小、质量压缩、旋转、转换扩展类型、添加水印等常用操作。
	前台的图片尺寸发生变化的时候在前端修改url参数就好了，不用每次单独在后台去写一个尺寸常量，然后重新生成。
2. 如何使用？
-----------------
		预览缩略图
			服务器有一个abc.png的图片大小为1024x800，现在想要300x230的比例图
				http://host:port/upload/abc.png?imageView/s/300x230 这个链接就ok了
				这里imageView是图片预览，后面的1是按大小缩放，300x230是缩放后的大小
			我想取一个512x400的图，就是原图的一半
				http://host:port/upload/abc.png?imageView/p/50
				这里的2是按比例缩放，50是缩放为原图的50%
		下载缩略图
			只需把imageView改为imageDown即可
		查看图片信息
			http://host:port/upload/abc.png?imageInfo
		旋转图片
			http://host:port/upload/abc.png?imageView/r/180 这个链接就ok了
		添加水印
			待完成
		这里的参数顺序可以打乱的，imageView这个参数是请求显示还是下载（必须是第一个），后面的是处理图片参数（以键值对方式排列）
		…….

3. 快速集成
--------
		1. 在web.xml中加入即可
		<filter>
			<filter-name>image-plugin</filter-name>
			<filter-class>org.unique.plugin.image.ImageFilter</filter-class>
		</filter>
		
		<filter-mapping>
			<filter-name>image-plugin</filter-name>
			<url-pattern>/upload/*</url-pattern>
		</filter-mapping>
		
		然后，就没有然后了。。。
4. 参数详解
--------
	图片预览
		http://xxxxx/abc.png?imageView/s/300x200/q/90
		http://xxxxx/abc.png?imageView/缩放类型/类型参数/压缩图片比例/比例参数
		缩放类型：1按大小缩放  2按比例缩放
		类型参数：类型为1后面跟widthxheight（当前这个不是强制压缩的会根据图片宽高比缩放），类型为2后面跟1-100的比例
		压缩图片比例：对图片的质量要求不高可以在这里处理（非必须）
		比例参数：1-100比例
	图片下载参数和预览是一样的，imageView变成了imageDown，我没有考虑吧他变的很复杂做成可配置的，
	因为这个插件只支持单机环境，分布式的话考虑别的架构，它非常的轻小。
	
	图片旋转
		http://xxxxx/abc.png?imageView/r/180
		将图片abc旋转180度预览，参考范围（0-360）可以是负数
	图片加水印

整体参数
--------
	r：旋转角度（单位 角度数字）
	q：图片质量（单位%）
	p：按比例缩放（单位%）
	s：按大小缩放（单位px）	
	c：裁剪（单位px）
	f：转换图片格式（单位 图片格式）

	
	
	
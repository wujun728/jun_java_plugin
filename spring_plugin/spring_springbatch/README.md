

该项目主要提供springbatch批处理框架的使用和配置。为需要的朋友提供直接的可运行的Demo

目前只成功处理了  txt的读写、数据库的读写、xml的数据复制。

对于csv文件的数据读取复制，目前存在一些问题。暂时不提供研究，研究另外2者即可。



可以进入com.zb.quartz.StartQuartz类中运行main方法，查看效果.

对哪一种文件格式的操作，需要先到application.xml中引用对应的xml配置文件。详情见26 - 29行的配置。目前默认是txt文件的处理.



sql文件在/resources/sql目录下。


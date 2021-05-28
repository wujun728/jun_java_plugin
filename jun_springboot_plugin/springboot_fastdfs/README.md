![输入图片说明](https://gitee.com/uploads/images/2018/0227/170934_44748f97_87650.jpeg "4.jpg")
## 概述
分布式文件系统：Distributed file system, DFS，又叫做网络文件系统：Network File System。一种允许文件通过网络在多台主机上分享的文件系统，可让多机器上的多用户分享文件和存储空间。

FastDFS是用c语言编写的一款开源的分布式文件系统，充分考虑了冗余备份、负载均衡、线性扩容等机制，并注重高可用、高性能等指标，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。特别适合中小文件（建议范围：4KB < file_size <500MB），对以文件为载体的在线服务，如相册网站、视频网站等。

## FastDFS 架构

FastDFS架构包括Tracker server和Storage server。客户端请求Tracker server进行文件上传、下载，通过Tracker server调度最终由Storage server完成文件上传和下载。

![1.jpg](https://blog.52itstyle.com/usr/uploads/2018/02/1424504379.jpg)

#### 跟踪服务器Tracker Server
主要做调度工作，起到均衡的作用；负责管理所有的 storage server和 group，每个 storage 在启动后会连接 Tracker，告知自己所属 group 等信息，并保持周期性心跳。tracker根据storage的心跳信息，建立group==>[storage serverlist]的映射表。

Tracker需要管理的元信息很少，会全部存储在内存中；另外tracker上的元信息都是由storage汇报的信息生成的，本身不需要持久化任何数据，这样使得tracker非常容易扩展，直接增加tracker机器即可扩展为tracker cluster来服务，cluster里每个tracker之间是完全对等的，所有的tracker都接受stroage的心跳信息，生成元数据信息来提供读写服务。

#### 存储服务器Storage Server

主要提供容量和备份服务；以 group 为单位，每个 group 内可以有多台 storage server，数据互为备份。以group为单位组织存储能方便的进行应用隔离、负载均衡、副本数定制（group内storage server数量即为该group的副本数），比如将不同应用数据存到不同的group就能隔离应用数据，同时还可根据应用的访问特性来将应用分配到不同的group来做负载均衡；缺点是group的容量受单机存储容量的限制，同时当group内有机器坏掉时，数据恢复只能依赖group内地其他机器，使得恢复时间会很长。

group内每个storage的存储依赖于本地文件系统，storage可配置多个数据存储目录，比如有10块磁盘，分别挂载在/data/disk1-/data/disk10，则可将这10个目录都配置为storage的数据存储目录。storage接受到写文件请求时，会根据配置好的规则选择其中一个存储目录来存储文件。为了避免单个目录下的文件数太多，在storage第一次启动时，会在每个数据存储目录里创建2级子目录，每级256个，总共65536个文件，新写的文件会以hash的方式被路由到其中某个子目录下，然后将文件数据作为本地文件存储到该目录中。

#### FastDFS的存储策略
为了支持大容量，存储节点（服务器）采用了分卷（或分组）的组织方式。存储系统由一个或多个卷组成，卷与卷之间的文件是相互独立的，所有卷的文件容量累加就是整个存储系统中的文件容量。一个卷可以由一台或多台存储服务器组成，一个卷下的存储服务器中的文件都是相同的，卷中的多台存储服务器起到了冗余备份和负载均衡的作用。

在卷中增加服务器时，同步已有的文件由系统自动完成，同步完成后，系统自动将新增服务器切换到线上提供服务。当存储空间不足或即将耗尽时，可以动态添加卷。只需要增加一台或多台服务器，并将它们配置为一个新的卷，这样就扩大了存储系统的容量。

#### FastDFS的上传过程
FastDFS向使用者提供基本文件访问接口，比如upload、download、append、delete等，以客户端库的方式提供给用户使用。

Storage Server会定期的向Tracker Server发送自己的存储信息。当Tracker Server Cluster中的Tracker Server不止一个时，各个Tracker之间的关系是对等的，所以客户端上传时可以选择任意一个Tracker。

当Tracker收到客户端上传文件的请求时，会为该文件分配一个可以存储文件的group，当选定了group后就要决定给客户端分配group中的哪一个storage server。当分配好storage server后，客户端向storage发送写文件请求，storage将会为文件分配一个数据存储目录。然后为文件分配一个fileid，最后根据以上的信息生成文件名存储文件。

![2.jpg](https://blog.52itstyle.com/usr/uploads/2018/02/2090946570.jpg)

##### 选择tracker server

当集群中不止一个tracker server时，由于tracker之间是完全对等的关系，客户端在upload文件时可以任意选择一个trakcer。
#####  选择存储的group

当tracker接收到upload file的请求时，会为该文件分配一个可以存储该文件的group，支持如下选择group的规则： 1. Round robin，所有的group间轮询 2. Specified group，指定某一个确定的group 3. Load balance，剩余存储空间多多group优先
#####  选择storage server

当选定group后，tracker会在group内选择一个storage server给客户端，支持如下选择storage的规则： 1. Round robin，在group内的所有storage间轮询 2. First server ordered by ip，按ip排序 3. First server ordered by priority，按优先级排序（优先级在storage上配置）
##### 选择storage path

当分配好storage server后，客户端将向storage发送写文件请求，storage将会为文件分配一个数据存储目录，支持如下规则： 1. Round robin，多个存储目录间轮询 2. 剩余存储空间最多的优先
#####  生成Fileid

选定存储目录之后，storage会为文件生一个Fileid，由storage server ip、文件创建时间、文件大小、文件crc32和一个随机数拼接而成，然后将这个二进制串进行base64编码，转换为可打印的字符串。
##### 选择两级目录

当选定存储目录之后，storage会为文件分配一个fileid，每个存储目录下有两级256*256的子目录，storage会按文件fileid进行两次hash（猜测），路由到其中一个子目录，然后将文件以fileid为文件名存储到该子目录下。
##### 生成文件名

当文件存储到某个子目录后，即认为该文件存储成功，接下来会为该文件生成一个文件名，文件名由group、存储目录、两级子目录、fileid、文件后缀名（由客户端指定，主要用于区分文件类型）拼接而成。

![3.jpg](https://blog.52itstyle.com/usr/uploads/2018/02/266801294.jpg)

#### FastDFS的文件同步
写文件时，客户端将文件写至group内一个storage server即认为写文件成功，storage server写完文件后，会由后台线程将文件同步至同group内其他的storage server。

每个storage写文件后，同时会写一份binlog，binlog里不包含文件数据，只包含文件名等元信息，这份binlog用于后台同步，storage会记录向group内其他storage同步的进度，以便重启后能接上次的进度继续同步；进度以时间戳的方式进行记录，所以最好能保证集群内所有server的时钟保持同步。

storage的同步进度会作为元数据的一部分汇报到tracker上，tracke在选择读storage的时候会以同步进度作为参考。

比如一个group内有A、B、C三个storage server，A向C同步到进度为T1 (T1以前写的文件都已经同步到B上了），B向C同步到时间戳为T2（T2 > T1)，tracker接收到这些同步进度信息时，就会进行整理，将最小的那个做为C的同步时间戳，本例中T1即为C的同步时间戳为T1（即所有T1以前写的数据都已经同步到C上了）；同理，根据上述规则，tracker会为A、B生成一个同步时间戳。

#### FastDFS的文件下载
客户端uploadfile成功后，会拿到一个storage生成的文件名，接下来客户端根据这个文件名即可访问到该文件。

![4.jpg](https://blog.52itstyle.com/usr/uploads/2018/02/1665688425.jpg)

跟upload file一样，在downloadfile时客户端可以选择任意tracker server。tracker发送download请求给某个tracker，必须带上文件名信息，tracke从文件名中解析出文件的group、大小、创建时间等信息，然后为该请求选择一个storage用来服务读请求。

####  FastDFS性能方案
![5.jpg](https://blog.52itstyle.com/usr/uploads/2018/02/658506662.jpg)

## FastDFS 安装
| 软件包  |  版本 |
| ------------ | ------------ |
| FastDFS  | v5.05  |
|  libfastcommon |  v1.0.7 |

#### 下载安装libfastcommon

- 下载

```
wget https://github.com/happyfish100/libfastcommon/archive/V1.0.7.tar.gz
```
- 解压

```
tar -xvf V1.0.7.tar.gz
cd libfastcommon-1.0.7
```
- 编译、安装

```
./make.sh
./make.sh install
```
- 创建软链接

```
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so 
```
#### 下载安装FastDFS

- 下载FastDFS

```
 wget https://github.com/happyfish100/fastdfs/archive/V5.05.tar.gz
```
- 解压

```
tar -xvf V5.05.tar.gz
cd fastdfs-5.05
```

- 编译、安装

```
./make.sh
./make.sh install
```
#### 配置 Tracker 服务

上述安装成功后，在/etc/目录下会有一个fdfs的目录，进入它。会看到三个.sample后缀的文件，这是作者给我们的示例文件，我们需要把其中的tracker.conf.sample文件改为tracker.conf配置文件并修改它：
```
cp tracker.conf.sample tracker.conf
vi tracker.conf
```
编辑tracker.conf
```
# 配置文件是否不生效，false 为生效
disabled=false

# 提供服务的端口
port=22122

# Tracker 数据和日志目录地址
base_path=//home/data/fastdfs

# HTTP 服务端口
http.server_port=80
```
创建tracker基础数据目录，即base_path对应的目录
```
mkdir -p /home/data/fastdfs
```
使用ln -s 建立软链接
```
ln -s /usr/bin/fdfs_trackerd /usr/local/bin
ln -s /usr/bin/stop.sh /usr/local/bin
ln -s /usr/bin/restart.sh /usr/local/bin
```
启动服务
```
service fdfs_trackerd start
```
查看监听
```
netstat -unltp|grep fdfs
```
如果看到22122端口正常被监听后，这时候说明Tracker服务启动成功啦！

tracker server 目录及文件结构
Tracker服务启动成功后，会在base_path下创建data、logs两个目录。目录结构如下：
```
${base_path}
  |__data
  |   |__storage_groups.dat：存储分组信息
  |   |__storage_servers.dat：存储服务器列表
  |__logs
  |   |__trackerd.log： tracker server 日志文件 
```

#### 配置 Storage 服务
进入 /etc/fdfs 目录，复制 FastDFS 存储器样例配置文件 storage.conf.sample，并重命名为 storage.conf

```
# cd /etc/fdfs
# cp storage.conf.sample storage.conf
# vi storage.conf
```
编辑storage.conf
```
# 配置文件是否不生效，false 为生效
disabled=false

# 指定此 storage server 所在 组(卷)
group_name=group1

# storage server 服务端口
port=23000

# 心跳间隔时间，单位为秒 (这里是指主动向 tracker server 发送心跳)
heart_beat_interval=30

# Storage 数据和日志目录地址(根目录必须存在，子目录会自动生成)
base_path=/home/data/fastdfs/storage

# 存放文件时 storage server 支持多个路径。这里配置存放文件的基路径数目，通常只配一个目录。
store_path_count=1

# 逐一配置 store_path_count 个路径，索引号基于 0。
# 如果不配置 store_path0，那它就和 base_path 对应的路径一样。
store_path0=/home/data/fastdfs/storage

# FastDFS 存储文件时，采用了两级目录。这里配置存放文件的目录个数。 
# 如果本参数只为 N（如： 256），那么 storage server 在初次运行时，会在 store_path 下自动创建 N * N 个存放文件的子目录。
subdir_count_per_path=256

# tracker_server 的列表 ，会主动连接 tracker_server
# 有多个 tracker server 时，每个 tracker server 写一行
tracker_server=192.168.1.190:22122

# 允许系统同步的时间段 (默认是全天) 。一般用于避免高峰同步产生一些问题而设定。
sync_start_time=00:00
sync_end_time=23:59

```
使用ln -s 建立软链接
```
ln -s /usr/bin/fdfs_storaged /usr/local/bin
```
启动服务
```
service fdfs_storaged start
```
查看监听
```
netstat -unltp|grep fdfs
```
启动Storage前确保Tracker是启动的。初次启动成功，会在 /home/data/fastdfs/storage 目录下创建 data、 logs 两个目录。如果看到23000端口正常被监听后，这时候说明Storage服务启动成功啦！

查看Storage和Tracker是否在通信
```
/usr/bin/fdfs_monitor /etc/fdfs/storage.conf
```

## FastDFS 配置 Nginx 模块

| 软件包  |  版本 |
| ------------ | ------------ |
| openresty  | v1.13.6.1  |
|  fastdfs-nginx-module |  v1.1.6 |

FastDFS 通过 Tracker 服务器，将文件放在 Storage 服务器存储， 但是同组存储服务器之间需要进行文件复制，有同步延迟的问题。

假设 Tracker 服务器将文件上传到了 192.168.1.190，上传成功后文件 ID已经返回给客户端。此时 FastDFS 存储集群机制会将这个文件同步到同组存192.168.1.190，在文件还没有复制完成的情况下，客户端如果用这个文件 ID 在 192.168.1.190 上取文件,就会出现文件无法访问的错误。而 fastdfs-nginx-module 可以重定向文件链接到源服务器取文件，避免客户端由于复制延迟导致的文件无法访问错误。

下载 安装 Nginx 和 fastdfs-nginx-module：

推荐您使用yum安装以下的开发库:
```
yum install readline-devel pcre-devel openssl-devel -y
```
下载最新版本并解压：
```
wget https://openresty.org/download/openresty-1.13.6.1.tar.gz

tar -xvf openresty-1.13.6.1.tar.gz

wget https://github.com/happyfish100/fastdfs-nginx-module/archive/master.zip

unzip master.zip
```

配置 nginx 安装，加入fastdfs-nginx-module模块：
```
./configure --add-module=../fastdfs-nginx-module-master/src/
```
编译、安装：
```
make && make install
```
查看Nginx的模块：
```
/usr/local/openresty/nginx/sbin/nginx -v
```
有下面这个就说明添加模块成功
![1.png](https://blog.52itstyle.com/usr/uploads/2018/02/3229446385.png)

复制 fastdfs-nginx-module 源码中的配置文件到/etc/fdfs 目录， 并修改：
```
cp /fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/

```
```
# 连接超时时间
connect_timeout=10

# Tracker Server
tracker_server=192.168.1.190:22122

# StorageServer 默认端口
storage_server_port=23000

# 如果文件ID的uri中包含/group**，则要设置为true
url_have_group_name = true

# Storage 配置的store_path0路径，必须和storage.conf中的一致
store_path0=/home/data/fastdfs/storage
```
复制 FastDFS 的部分配置文件到/etc/fdfs 目录：
```
cp /fastdfs-nginx-module/src/http.conf /etc/fdfs/
cp /fastdfs-nginx-module/src/mime.types /etc/fdfs/
```
配置nginx，修改nginx.conf：
```
location ~/group([0-9])/M00 {
    ngx_fastdfs_module;
}
```
启动Nginx：
```
[root@iz2ze7tgu9zb2gr6av1tysz sbin]# ./nginx
ngx_http_fastdfs_set pid=9236
```
测试上传：
```
[root@iz2ze7tgu9zb2gr6av1tysz fdfs]# /usr/bin/fdfs_upload_file /etc/fdfs/client.conf /etc/fdfs/4.jpg
group1/M00/00/00/rBD8EFqVACuAI9mcAAC_ornlYSU088.jpg
```

部署结构图：

![5.png](https://blog.52itstyle.com/usr/uploads/2018/02/177205871.png)

## JAVA 客户端集成
pom.xml引入：
```xml
<!-- fastdfs -->
<dependency>
	<groupId>org.csource</groupId>
	<artifactId>fastdfs-client-java</artifactId>
	<version>1.27</version>
</dependency>
```
fdfs_client.conf配置：
```
#连接tracker服务器超时时长
connect_timeout = 2  
#socket连接超时时长
network_timeout = 30
#文件内容编码 
charset = UTF-8 
#tracker服务器端口
http.tracker_http_port = 8080
http.anti_steal_token = no
http.secret_key = FastDFS1234567890
#tracker服务器IP和端口（可以写多个）
tracker_server = 192.168.1.190:22122 
```
FastDFSClient上传类：
```java
public class FastDFSClient{
	private static final String CONFIG_FILENAME = "D:\\itstyle\\src\\main\\resources\\fdfs_client.conf";
	private static final String GROUP_NAME = "market1";
	private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;

    static{
    	try {
			ClientGlobal.init(CONFIG_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
    }
    public FastDFSClient() throws Exception {
	   trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
	   trackerServer = trackerClient.getConnection();
	   storageServer = trackerClient.getStoreStorage(trackerServer);;
	   storageClient = new StorageClient(trackerServer, storageServer);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @return
     */
    public  String[] uploadFile(File file, String fileName) {
        return uploadFile(file,fileName,null);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @param metaList 文件元数据
     * @return
     */
    public  String[] uploadFile(File file, String fileName, Map<String,String> metaList) {
        try {
            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<String,String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name,value);
                }
            }
            return storageClient.upload_file(GROUP_NAME,buff,fileName,nameValuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public Map<String,String> getFileMetadata(String groupname,String fileId) {
        try {
            NameValuePair[] metaList = storageClient.get_metadata(groupname,fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public int deleteFile(String groupname,String fileId) {
        try {
            return storageClient.delete_file(groupname,fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param outFile 文件下载保存位置
     * @return
     */
    public  int downloadFile(String groupName,String fileId, File outFile) {
        FileOutputStream fos = null;
        try {
            byte[] content = storageClient.download_file(groupName,fileId);
            fos = new FileOutputStream(outFile);
            InputStream ips = new ByteArrayInputStream(content); 
            IOUtils.copy(ips,fos);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) throws Exception {
    	FastDFSClient client = new FastDFSClient();
    	File file = new File("D:\\23456.png");
        String[] result = client.uploadFile(file, "png");
        System.out.println(result.length);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
}
```
执行main方法测试返回：
```
2
group1
M00/00/00/rBD8EFqTrNyAWyAkAAKCRJfpzAQ227.png
```

地址：https://blog.52itstyle.com/archives/2430/
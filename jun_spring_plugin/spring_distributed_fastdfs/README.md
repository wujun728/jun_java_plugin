#### jun_distributed_fastdfs，基于FastDFS的一个开源的分布式文件系统

她对文件进行管理，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等, 解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等等。

FastDFS服务端有两个角色：跟踪器（tracker）和存储节点（storage）。跟踪器主要做调度工作，在访问上起负载均衡的作用。

存储节点存储文件，完成文件管理的所有功能：存储、同步和提供存取接口，FastDFS同时对文件的meta data进行管理。所谓文件的meta data就是文件的相关属性，以键值对（key value pair）方式表示，如：width=1024，其中的key为width，value为1024。文件meta data是文件属性列表，可以包含多个键值对。
 
跟踪器和存储节点都可以由一台多台服务器构成。跟踪器和存储节点中的服务器均可以随时增加或下线而不会影响线上服务。其中跟踪器中的所有服务器都是对等的，可以根据服务器的压力情况随时增加或减少。

为了支持大容量，存储节点（服务器）采用了分卷（或分组）的组织方式。存储系统由一个或多个卷组成，卷与卷之间的文件是相互独立的，所有卷 的文件容量累加就是整个存储系统中的文件容量。一个卷可以由一台或多台存储服务器组成，一个卷下的存储服务器中的文件都是相同的，卷中的多台存储服务器起 到了冗余备份和负载均衡的作用。

在卷中增加服务器时，同步已有的文件由系统自动完成，同步完成后，系统自动将新增服务器切换到线上提供服务。

当存储空间不足或即将耗尽时，可以动态添加卷。只需要增加一台或多台服务器，并将它们配置为一个新的卷，这样就扩大了存储系统的容量。
FastDFS中的文件标识分为两个部分：卷名和文件名，二者缺一不可。

 FastDFS file upload

上传文件交互过程：
1. client询问tracker上传到的storage，不需要附加参数；
2. tracker返回一台可用的storage；
3. client直接和storage通讯完成文件上传。 

 FastDFS file download

下载文件交互过程：
1. client询问tracker下载文件的storage，参数为文件标识（卷名和文件名）；
2. tracker返回一台可用的storage；
3. client直接和storage通讯完成文件下载。
需要说明的是，client为使用FastDFS服务的调用方，client也应该是一台服务器，它对tracker和storage的调用均为服务器间的调用。


##### Features #####
#### 1.分布式文件系统架构说明 ####
##### - fastdfs-client(FastDFS 客户端) #####
- fastdfs提供的java客户端api，java相关功能都基于这个基础上封装，扩展，第三方应用不需要关心该接口.
##### - fastdfs-core(HTTP服务器) #####
- 基于spring boot实现，提供http接口服务.
- 提供http服务器信息获取，http上传，http下载，删除上报，该服务会记录文件的基本信息，其中服务器信息获取，上传上报都由fastdfs-app自动完成，第三方应用不需要关心.

##### - fastdfs-app(Apply SDK) #####
- **初始化**
- APIConfigure config = new APIConfigure("appKey", "httpServerUrl");
- DFSAppClient.instance().initAPIConfigure(config);
- 实现执行初化操作，从fastdfs-core获取trackers服务器信息，及appKey对应的groupName，
这些动作都由SDK自动完成，第三方应用不需要关心.

- **上传文件**
- String fileId = DFSAppClient.instance().uploadFile(new File("文件绝对路径"));
- fileId:返回的fileId字符串，示例：group1/M00/00/00/wKgABFuOVJyEPGKEAAAAADUuUeE339.png
- fileId是后续对文件进行操作的基本参数，第三方应用拿到该值后应本地做好保存.
	
- **下载文件**
- FileOutputStream fos = new FileOutputStream(new File("文件绝对路径"));
- DFSAppClient.instance().downloadFile(fileId, fos, true);
- fileId:上传文件成功后返回的fileId字符串.

- **删除文件**
- int result = DFSAppClient.instance().deleteFile(fileId);
- fileId:上传文件成功后返回的fileId字符串.
- result:该方法会返回0表示删除成功，其他表示失败.

# **fastdfs 下载示例说明** #
- http://127.0.0.1:8808/dfs/v1/download?fileId=group1/M00/00/00/wKgABFuQ2PWEbNsOAAAAADUuUeE667.png&direct=true
- fileId:上传文件成功后返回的fileId字符串.
- direct:表示是否直接显示，非直接显示会提示下载，默认是非直接显示.

 
> mvn clean install -DskipTests -U

- execute sql
>  dfs_create_tables.sql 
 
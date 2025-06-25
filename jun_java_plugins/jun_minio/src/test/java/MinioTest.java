import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;

/**
 * @project heima-leadnews
 * @description
 * @author capture or new
 * @date 2023/7/14 13:42:53
 * @version 1.0
 */

public class MinioTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {

            // 本地文件地址
            fileInputStream =  new FileInputStream("D:\\Documents\\Desktop\\list.html");

            //1.创建minio链接客户端
            MinioClient minioClient = MinioClient.builder().credentials("root", "minioadmin")
                    .endpoint("http://10.100.254.13:30346")
                    .build();
            //2.上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("list.html")//文件名
                    .contentType("text/html")//文件类型
                    .bucket("byk2kubevirt")//桶名词  与minio创建的名词一致
                    //   fileInputStream.available() 表示一直有内容就会上传  -1 表示将所有的相关文件的内容都上传
                    .stream(fileInputStream, fileInputStream.available(), -1) //文件流
                    .build();
            minioClient.putObject(putObjectArgs);
            // 输出访问地址
            //System.out.println("http://10.13.167.16:30346/byk2kubevirt/list.html");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



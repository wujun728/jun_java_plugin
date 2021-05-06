package jun_webmagic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class UrlFileDownloadUtil {
	
	@SuppressWarnings("null")
	public static void main(String[] args) {
		List<String> urlList = new ArrayList<>() ;
		urlList.add("http://p8.urlpic.club/pic20190701/upload/image/20190705/70500005770.jpg");
		urlList.add("http://p8.urlpic.club/pic20190701/upload/image/20190705/70500005756.jpg");
		urlList.add("http://p8.urlpic.club/pic20190701/upload/image/20190705/70500005796.jpg");
		urlList.add("http://p8.urlpic.club/pic20190701/upload/image/20190705/70500005841.jpg");
		urlList.add("http://p8.urlpic.club/pic20190701/upload/image/20190705/70500005801.jpg"); 
		UrlFileDownloadUtil.downloadPictures(urlList );
	}
    /**
     * 传入要下载的图片的url列表，将url所对应的图片下载到本地
     */
    public static void downloadPictures(List<String> urlList, List<String> names) {
        String baseDir = "D:\\spider_img_14\\";
        URL url = null;

        for (int i = 0; i < urlList.size(); i++) {
            try {
                url = new URL(urlList.get(i));
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(new File(baseDir + names.get(i)));

                byte[] buffer = new byte[1024 * 50];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                System.out.println("已经下载：" + baseDir + names.get(i));
                dataInputStream.close();
                fileOutputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadPictures(List<String> urlList) {
        String baseDir = "E:\\Images";
        URL url = null;
        
        
        
        for (int i = 0; i < urlList.size(); i++) {
            try {
                String[] files = urlList.get(i).split("/");
                String name = files[files.length - 1];
                url = new URL(urlList.get(i));
                
                String filePath = urlList.get(i).substring(0, urlList.get(i).lastIndexOf("/")).replace("http://blog.java1234.com", baseDir);
                File file = new File(filePath.replace("/", "\\"));
                if (!file.exists())
                {
                    file.mkdirs();
                }
                File fileFinalPath=new File(filePath+"\\"+name);
                if(fileFinalPath.exists()) {
                	System.err.println(" **************************    文件已存在，不再下载  ： "+fileFinalPath.getAbsolutePath());
                	continue ;
                }
                
                URLConnection myurlcon = url.openConnection();
                myurlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                myurlcon.setConnectTimeout(5000); 
                myurlcon.setReadTimeout(60000); 
                
                DataInputStream dataInputStream = new DataInputStream(myurlcon.getInputStream());
//                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath+"\\"+name));

                byte[] buffer = new byte[1024 * 50];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                System.out.println("已经下载："+myurlcon.getContentLength() + " || " +filePath+"\\"+name);
                dataInputStream.close();
                fileOutputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 下载一张图片
    public static void downloadPicture(String u, String name) {
        String baseDir = "D:\\spider_img_14\\";
        URL url = null;

        try {
            url = new URL(u);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(baseDir + name));

            byte[] buffer = new byte[1024 * 50];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            System.out.println("已经下载：" + baseDir + name);
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 下载一张图片
    public static void downloadPicture(String u) {
        String baseDir = "D:\\spider_img_14\\";
        URL url = null;
        String[] files = u.split("/");
        String name = files[files.length - 1];

        try {
            url = new URL(u);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(baseDir + name));

            byte[] buffer = new byte[1024 * 50];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            System.out.println("已经下载：" + baseDir + name);
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

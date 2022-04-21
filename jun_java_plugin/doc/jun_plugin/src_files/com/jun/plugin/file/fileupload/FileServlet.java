package com.jun.plugin.file.fileupload;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.jun.plugin.base.BaseServlet;
import com.jun.plugin.utils.DateUtil;
import com.jun.plugin.vccode.VerifyCode;

/**
 *  表单的类型是post且enctype为multipart/form-date 
 * @author jun
 */
//http://localhost:10080/jun_base/fileServlet?method=Uploadify
@WebServlet(name = "FileServlet", value = { "/fileServlet", "/cosUploadServlet" }, loadOnStartup = 1, 
initParams = { @WebInitParam(name = "upload_path", value = "/"), @WebInitParam(name = "uploadPath", value = "/upload/") })
public class FileServlet extends BaseServlet {
//	*****************************************************************************
//	*****************************************************************************
	
	
	
//	*****************************************************************************
//	*****************************************************************************
	
	
	
	
	
	
	
	
	// 使用临时文件临界�?10M
	private static final int TEMP_MAX_SIZE = 10 * 1024 * 1024;
	// 文件大小上限10M
	private static final int FILE_MAX_SIZE = 10 * 1024 * 1024;
	private static String uploadPath;
	// 集合中保存所有成�?
	private List<String> words = new ArrayList<String>();
	private int num = 5;
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("content-disposition", "attachment;filename=22.jpg");
		InputStream is = this.getServletContext().getResourceAsStream("1.jpg");
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) > -1) {
			out.write(buf, 0, len);
		}
		out.close();
		is.close();
	}
	


	public void FastServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try {
			ServletFileUpload up = new ServletFileUpload(disk);
			// 以下是迭代器模式
			FileItemIterator it = up.getItemIterator(request);
			while (it.hasNext()) {
				FileItemStream item = it.next();
				String fileName = item.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				InputStream in = item.openStream();
				FileUtils.copyInputStreamToFile(in, new File(path + "/" + fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void DownServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && password != null) {
			InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/com/demo/plugin/request_response/config.properties");
			Properties props = new Properties();
			props.load(is);
			if (props.getProperty(username).equals(password)) {
				response.setHeader("content-disposition", "attachment;filename=a.JPG");
			} else {
				request.getRequestDispatcher("/message.html").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/login.html").forward(request, response);
		}
	}

	public void Download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/download/1.jpg");// ��ȡ��Դ
		String fileName = path.substring(path.lastIndexOf("\\") + 1);// ��ȡ�ļ���
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		InputStream in = new FileInputStream(path);
		OutputStream out = response.getOutputStream();
		int length = 0;
		byte buffer[] = new byte[1024];//
		while ((length = in.read(buffer)) > 0) {// read���ض��뻺��������ֽ���� -1��
			out.write(buffer, 0, length);
		}
	}


	public void UpServlet11(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory();
		try{
			ServletFileUpload up = 
					new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			//声明�?个image对象
			for(FileItem item:list){
				if(item.isFormField()){
					String note = item.getString(request.getCharacterEncoding());
				}else{
					String oldName = item.getName();
					String type = item.getContentType();
					if(!type.contains("image/")){
						throw new RuntimeException("不接收的类型");
					}
					oldName = oldName.substring(oldName.lastIndexOf("\\")+1);
					String ext = oldName.substring(oldName.lastIndexOf("."));
					String newName = 
							UUID.randomUUID()+ext;
					
					//上传
					String path = getServletContext().getRealPath("/up");
					item.write(new File(path+"/"+newName));
					item.delete();
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	public void UpServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String txt = req.getParameter("txt");//返回的是null
		System.err.println("txt is :"+txt);
		System.err.println("=========================================");
		InputStream in = req.getInputStream();
//		byte[] b= new byte[1024];
//		int len = 0;
//		while((len=in.read(b))!=-1){
//			String s = new String(b,0,len);
//			System.err.print(s);
//		}
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine = br.readLine();//读取第一行，且第�?行是分隔符号
		String fileName = br.readLine();
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1);// bafasd.txt"
		fileName = fileName.substring(0,fileName.length()-1);
		
		br.readLine();
		br.readLine();
		String data = null;
		//获取当前项目的运行路�?
		String projectPath = getServletContext().getRealPath("/up");
		PrintWriter out  = new PrintWriter(projectPath+"/"+fileName);
		while((data=br.readLine())!=null){
			if(data.equals(firstLine+"--")){
				break;
			}
			out.println(data);
		}
		out.close();
	}
	
	



	public void UpImgServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory(1024*10,new File("d:/a"));
		ServletFileUpload up = new ServletFileUpload(disk);
		try{
			List<FileItem> list = up.parseRequest(request);
			//只接收图�?*.jpg-iamge/jpege.,bmp/imge/bmp,png,
			List<String> imgs = new ArrayList<String>();
			for(FileItem file :list){
				if(file.getContentType().contains("image/")){
					String fileName = file.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
					//获取扩展
					String extName = fileName.substring(fileName.lastIndexOf("."));//.jpg
					//UUID
					String uuid = UUID.randomUUID().toString().replace("-", "");
					//新名�?
					String newName = uuid+extName;
					
					
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(path+"/"+newName));
					//放到list
					imgs.add(newName);
				}
				file.delete();
			}
			request.setAttribute("imgs",imgs);
			request.getRequestDispatcher("/jsps/imgs.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	

	public void ResponseDemo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String realpath = this.getServletContext().getRealPath("/download/1.gif");
		String filename = realpath.substring(realpath.lastIndexOf(" \\") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + filename);
		// 服务器�?�过这个头，告诉浏览器以下载方式打开数据
		FileInputStream in = new FileInputStream(realpath);
		int len = 0;
		byte buffer[] = new byte[1024];
		OutputStream out = response.getOutputStream();
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		// out不用close，response在销毁的时�?�服务器会自动关闭与response相关的流�?
	}
 

	@Override
	public void init(ServletConfig config) throws ServletException {
//		uploadPath = request.getServletContext().getRealPath("/") + "upload\\" ;    
//		uploadPath = FileUtils.getTempDirectoryPath();
		
		uploadPath = config.getServletContext().getRealPath(config.getInitParameter("uploadPath"));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		uploadPath = getServletContext().getRealPath("/") + "Upload/" + sdf.format(new Date()) + "/";
		System.err.println("uploadPath="+uploadPath);
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdir();
		}
		
		/*String path = getServletContext().getRealPath("/WEB-INF/new_words.txt");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
 

	public static String getFolderPath() {
		System.err.println("uploadPath="+uploadPath);
		File folder = new File(uploadPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File[] files = folder.listFiles();
		for(int i = 0; i < files.length; i++){
			File file = files[i];
			String fileName = file.getName();
			System.err.println("fileName="+fileName);
		}
			
		return uploadPath;
	}

	
	
	public void ResponseDemo5432543(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        String realpath = this.getServletContext().getRealPath("/download/1.gif"); 
        String filename = realpath.substring(realpath.lastIndexOf(" \\")+1); 
        response.setHeader("content-disposition", "attachment;filename="+filename); 
        //服务器�?�过这个头，告诉浏览器以下载方式打开数据 
        FileInputStream in = new FileInputStream(realpath); 
        int len = 0; 
        byte buffer[]=new byte[1024]; 
        OutputStream out = response.getOutputStream(); 
        while((len = in.read(buffer))>0){ 
            out.write(buffer, 0, len); 
            } 
        in.close(); 
        //out不用close，response在销毁的时�?�服务器会自动关闭与response相关的流�? 
}
	
	
	 /**  
     * 实现多文件的同时上传  
     */     
    public void Uploadify(HttpServletRequest request,    
            HttpServletResponse response) throws ServletException, IOException {    
            
        //设置接收的编码格�?    
        request.setCharacterEncoding("UTF-8");    
        Date date = new Date();//获取当前时间    
        SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");    
        SimpleDateFormat sdfFolder = new SimpleDateFormat("yyMM");    
        String newfileName = sdfFileName.format(date);//文件名称    
        String fileRealPath = "";//文件存放真实地址    
            
        String fileRealResistPath = "";//文件存放真实相对路径    
            
        //名称  界面编码 必须 和request 保存�?�?..否则乱码    
        String name = request.getParameter("name");    
                
             
        String firstFileName="";    
        // 获得容器中上传文件夹�?在的物理路径  
        String savePath = request.getRealPath("/") + "upload\\" ;    
        System.out.println("路径" + savePath+"; name:"+name);    
        File file = new File(savePath);    
        if (!file.isDirectory()) {    
            file.mkdirs();    
        }    
    
        try {    
            DiskFileItemFactory fac = new DiskFileItemFactory();    
            ServletFileUpload upload = new ServletFileUpload(fac);    
            upload.setHeaderEncoding("UTF-8");    
            // 获取多个上传文件    
            List fileList = fileList = upload.parseRequest(request);    
            // 遍历上传文件写入磁盘    
            Iterator it = fileList.iterator();    
            while (it.hasNext()) {    
                Object obit = it.next();  
                if(obit instanceof DiskFileItem){  
                    System.out.println("xxxxxxxxxxxxx");  
                    DiskFileItem item = (DiskFileItem) obit;    
                        
                    // 如果item是文件上传表单域       
                    // 获得文件名及路径       
                    String fileName = item.getName();    
                    if (fileName != null) {    
                        firstFileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);    
                        String formatName = firstFileName.substring(firstFileName.lastIndexOf("."));//获取文件后缀�?    
                        fileRealPath = savePath + newfileName+ formatName;//文件存放真实地址    
                            
                        BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入�?    
                        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));// 获得文件输出�?    
                        Streams.copy(in, outStream, true);// �?始把文件写到你指定的上传文件�?    
                        //上传成功，则插入数据�?    
                        if (new File(fileRealPath).exists()) {    
                            //虚拟路径赋�??    
                            fileRealResistPath=sdfFolder.format(date)+"/"+fileRealPath.substring(fileRealPath.lastIndexOf("\\")+1);    
                            //保存到数据库    
                            System.out.println("保存到数据库:");    
                            System.out.println("name:"+name);    
                            System.out.println("虚拟路径:"+fileRealResistPath);    
                        }    
                             
                    }     
                }  
            }     
        } catch (org.apache.commons.fileupload.FileUploadException ex) {  
           ex.printStackTrace();    
           System.out.println("没有上传文件");    
           return;    
        }     
       response.getWriter().write("1");    
            
    }    
    
    



	public void DownloadServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.得到下载文件名称
		String filename = new Date().toLocaleString() + "�?售榜�?.csv";
		// 2.设置下载文件的mimeType类型
		response.setContentType(this.getServletContext().getMimeType(filename));

		// 3.设置Content-Dispostion
		String agent = request.getHeader("User-agent");
		response.setHeader("Content-disposition", "attachement;filename=" + FileUtil.getDownloadFileName(filename, agent));

		// 4.得到�?售榜单信�?

		/*List<Product> ps = null;
		ProductService service = ProductServiceFactory.getInstance();
		try {
			User user = (User) request.getSession().getAttribute("user");
			ps = service.findSell(user);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PrivilegeException e) {
			response.sendRedirect(request.getContextPath() + "/error/error.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("gbk");
		response.getWriter().println("商品名称,�?售数�?");
		for (Product p : ps) {
			response.getWriter().println(
					p.getName() + "," + p.getTotalSaleNum());
			response.getWriter().flush();
		}*/

		response.getWriter().close();
	}

	
	public void DownServlet123(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oldname =  request.getParameter("oldname");
		oldname = new String(oldname.getBytes("ISO-8859-1"),"UTF-8");
		String newname = request.getParameter("newname");
		//此文件是否存�?
		String path = getServletContext().getRealPath("/up/"+newname);
		File file = new File(path);
		if(file.exists()){
			response.setContentType("application/force-download");
			oldname = URLEncoder.encode(oldname, "UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+oldname);
			response.setContentLength((int)file.length());
			InputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int len = -1;
			OutputStream out = response.getOutputStream();
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			out.close();
		}else{
			System.err.println("你下载文件已经不存在...");
		}
		
		
	}
	
	
	public void DownServlet2321(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		//第一步：设置响应的类�?
		resp.setContentType("application/force-download");
		//第二读取文件
		String path = getServletContext().getRealPath("/up/"+name);
		InputStream in = new FileInputStream(path);
		//设置响应�?
		//对文件名进行url编码
		name = URLEncoder.encode(name, "UTF-8");
		resp.setHeader("Content-Disposition","attachment;filename="+name);
		resp.setContentLength(in.available());
		
		//第三步：�?始文件copy
		OutputStream out = resp.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len=in.read(b))!=-1){
			out.write(b,0,len);
		}
		out.close();
		in.close();
	}
	

	public void GzipServlet1(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		//声明准备被压缩的数据
		String str = "Hello你好Hello你好在内存中声明�?Hello你好�?" +
				"内存中声明一个Hello你好在内存中声明�?个Hello�?" +
				"好在内存中声明一�?<br/>容器声明准备被压缩获取准备被压缩" +
				"的数据的字节码的数据容器声明准备被压缩获取准备被压缩的数" +
				"据的字节码的数据容器声明准备被压缩获取准备被压缩的数据的" +
				"字节码的数据个容器声明准备被压缩获取准备被压缩的数据的字节码�?" +
				"数据在内存中声明�?个容器声明准备被压缩获取准备被压缩的数据" +
				"的字节码的数�?";
		//2：获取准备被压缩的数据的字节�?
		byte[] src = str.getBytes("UTF-8");
		//3:在内存中声明�?个容�?
		ByteArrayOutputStream destByte = new ByteArrayOutputStream();
		//４：声明压缩的工具流，并设置压缩的目的地为destByte
		GZIPOutputStream zip = new GZIPOutputStream(destByte);
		//5:写入数据
		zip.write(src);
		//6:关闭压缩工具�?
		zip.close();
		System.err.println("压缩之前字节码大小："+src.length);
		//７：获取压缩以后数据
		byte[] dest = destByte.toByteArray();
		System.err.println("压缩以后的字节码大小�?"+dest.length);
		
		//8:必须要输出压缩以后字节数�?
		resp.setContentType("text/html;charset=UTF-8");
		//9:必须要使用字节流来输出信�?
		OutputStream out = resp.getOutputStream();
		//10:通知浏览器�?�这是压缩的数据，要求浏览器解压
		resp.setHeader("Content-encoding","gzip");
		//11:通知浏览器压缩数据的长度
		resp.setContentLength(dest.length);
		//10:输出
		out.write(dest);
	}
	


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 下载
		// 思�?�：能不能加下载的文件名称写成中文？
		System.out.println("doPost");
		// 通知浏览器进行下�?
		response.setHeader("content-disposition", "attachment;filename=22.jpg");
		// 资源 1.jpg
		InputStream is = this.getServletContext().getResourceAsStream("1.jpg");
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) > -1) {
			out.write(buf, 0, len);
		}
		out.close();
		is.close();
	}

	private void refresh(HttpServletResponse response) throws IOException {
		// refresh:服务器�?�知浏览器，刷新的时间以及url 单位：秒
		if (num > 0) {
			// 将内容输�?
			response.getWriter().print(num--);
			response.setHeader("refresh", "1");
		} else {
			response.setHeader("refresh",
					"0;url=http://localhost:8080/day044/1.html");
		}
	}

	private void type(HttpServletResponse response) throws IOException {
		// content-type:服务器�?�知浏览器，服务器发送的数据的编�?
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.getWriter().write("中文");
	}

	private void encoding(HttpServletResponse response) throws IOException {
		// 通知浏览器，服务器发送的数据时压缩的，并且指定压缩的格式
		response.setHeader("content-encoding", "gzip");
		// 将大数据压缩后，发�?�给浏览�?
		// 准备大数�?
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 80000; i++) {
			builder.append("abcd");
		}
		String data = builder.toString();
		// 确定压缩的位�?
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩 gzip
		GZIPOutputStream gzip = new GZIPOutputStream(baos); // 压缩的位�?
		// 压缩数据
		gzip.write(data.getBytes());
		gzip.close();
		// 获得压缩后的字节数组
		byte[] endData = baos.toByteArray();
		// 将压缩的数据发�?�给浏览�? --
		response.getOutputStream().write(endData);
	}

	private void location(HttpServletResponse response) {
		// 跳转 (重定�?) -- 服务器�?�知浏览
		// 设置http响应�?
		response.setHeader("location", "http://localhost:8080/day044/1.html");
		// 状�?�码确定行为
		response.setStatus(302);
	}

	public void DownServlet321321(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// NO1
		ServletConfig config = this.getServletConfig();
		String filePath = config.getInitParameter("filePath");
		// NO2
		File file = new File(filePath);
		response.setHeader("content-disposition",
				"attachment;filename=" + file.getName());
		// NO3
		InputStream is = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = is.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
		is.close();
		os.close();
	}

	public void DownServlet22(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		// 第一步：设置响应的类�?
		resp.setContentType("application/force-download");
		// 第二读取文件
		String path = getServletContext().getRealPath("/up/" + name);
		InputStream in = new FileInputStream(path);
		// 设置响应�?
		// 对文件名进行url编码
		name = URLEncoder.encode(name, "UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=" + name);
		resp.setContentLength(in.available());
		// 第三步：�?始文件copy
		OutputStream out = resp.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
		in.close();
	}

	public void GetImageServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得�?张图�?
		// 创建图片 -- 在内存中
		int width = 80;
		int height = 40;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 创建图层，获得画�?
		Graphics g = image.getGraphics();
		// 确定画笔颜色
		g.setColor(Color.BLACK);
		// 填充�?个矩�?
		g.fillRect(0, 0, width, height);
		// 只需要一个边�?
		// 设置颜色
		g.setColor(Color.WHITE);
		// 填充�?个矩�?
		g.fillRect(1, 1, width - 2, height - 2);
		// 填充字符
		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		// 设置字体
		g.setFont(new Font("宋体", Font.BOLD, 30));
		// 缓存随机生成的字�?
		StringBuffer buf = new StringBuffer();
		// 随机获得4个字�?
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			// 设置随机颜色
			g.setColor(new Color(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			// 获得�?个随机字�?
			int index = random.nextInt(62);
			// 截取字符�?
			String str = data.substring(index, index + 1); // [)
			// �?要将随机的字符，写到图片�?
			g.drawString(str, 20 * i, 30);
			// 缓存
			buf.append(str);
		}
		// 将获得随机字符串，保存到session
		// * 获得session
		HttpSession session = request.getSession();
		// * 保存�?
		session.setAttribute("number", buf.toString());
		// 干扰�?
		for (int i = 0; i < 10; i++) {
			// 设置随机颜色
			g.setColor(new Color(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			// 随机画直�?
			g.drawLine(random.nextInt(width), random.nextInt(height),
					random.nextInt(width), random.nextInt(height));
		}
		/**
		 * <extension>jpg</extension> <mime-type>image/jpeg</mime-type>
		 */
		// 通知浏览器发送的数据时一张图�?
		response.setContentType("image/jpeg");
		// 将图片发送给浏览�?
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	public void GzipServlet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		// 声明准备被压缩的数据
		String str = "Hello你好Hello你好在内存中声明�??ello你好�??"
				+ "内存中声明一个Hello你好在内存中声明�??��Hello�?? "
				+ "好在内存中声明一�??br/>容器声明准备被压缩获取准备被压缩"
				+ "的数据的字节码的数据容器声明准备被压缩获取准备被压缩的数"
				+ "据的字节码的数据容器声明准备被压缩获取准备被压缩的数据的"
				+ "字节码的数据个容器声明准备被压缩获取准备被压缩的数据的字节码�??"
				+ "数据在内存中声明�??��容器声明准备被压缩获取准备被压缩的数�??" + "的字节码的数�??";
		// 2：获取准备被压缩的数据的字节�??
		byte[] src = str.getBytes("UTF-8");
		// 3:在内存中声明�??��容器
		ByteArrayOutputStream destByte = new ByteArrayOutputStream();
		// ４：声明压缩的工具流，并设置压缩的目的地为destByte
		GZIPOutputStream zip = new GZIPOutputStream(destByte);
		// 5:写入数据
		zip.write(src);
		// 6:关闭压缩工具�??
		zip.close();
		System.err.println("压缩之前字节码大小：" + src.length);
		// ７：获取压缩以后数据
		byte[] dest = destByte.toByteArray();
		System.err.println("压缩以后的字节码大小�??+dest.length");
		// 8:必须要输出压缩以后字节数�??
		resp.setContentType("text/html;charset=UTF-8");
		// 9:必须要使用字节流来输出信�??
		OutputStream out = resp.getOutputStream();
		// 10:通知浏览器�?这是压缩的数据，要求浏览器解�??
		resp.setHeader("Content-encoding", "gzip");
		// 11:通知浏览器压缩数据的长度
		resp.setContentLength(dest.length);
		// 10:输出
		out.write(dest);
	}

	public void ImageServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 设置响应类型
		resp.setContentType("image/jpeg");
		int width = 60;
		int height = 30;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.BOLD, 18));
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			int code = r.nextInt(10);
			g.setColor(c);
			g.drawString("" + code, i * 15, 10 + r.nextInt(20));
		}
		for (int i = 0; i < 10; i++) {
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			g.setColor(c);
			g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60),
					r.nextInt(30));
		}
		// 图片生效
		g.dispose();
		// 写到
		ImageIO.write(img, "JPEG", resp.getOutputStream());
	}

	public void Up2Servlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTf-8");
		// 获取项目的路�?
		String path = getServletContext().getRealPath("/up");
		// 第一步声明diskfileitemfactory工厂类，用于在指的磁盘上设置�?个临时目�?
		DiskFileItemFactory disk = new DiskFileItemFactory(1024 * 10, new File(
				"d:/a"));
		// 第二步：声明ServletFileUpoload，接收上面的临时目录
		ServletFileUpload up = new ServletFileUpload(disk);
		// 第三步：解析request
		try {
			List<FileItem> list = up.parseRequest(req);
			// 如果就一个文�?
			FileItem file = list.get(0);
			// 获取文件�?,带路�?
			String fileName = file.getName();
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			// 获取文件的类�?
			String fileType = file.getContentType();
			// 获取文件的字节码
			InputStream in = file.getInputStream();
			// 声明输出字节�?
			OutputStream out = new FileOutputStream(path + "/" + fileName);
			// 文件copy
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
			long size = file.getInputStream().available();
			// 删除上传的临时文�?
			file.delete();
			// 显示数据
			resp.setContentType("text/html;charset=UTf-8");
			PrintWriter op = resp.getWriter();
			op.print("文件上传成功<br/>文件�?:" + fileName);
			op.print("<br/>文件类型:" + fileType);
			op.print("<br/>文件大小（bytes�?" + size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Up3Servlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		// 声明disk
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setSizeThreshold(1024 * 1024);
		disk.setRepository(new File("d:/a"));
		// 声明解析requst的servlet
		ServletFileUpload up = new ServletFileUpload(disk);
		up.setSizeMax(1024 * 1024 * 100);
		up.setFileSizeMax(1024 * 1024 * 10);
		try {
			// 解析requst
			List<FileItem> list = up.parseRequest(request);
			// 声明�?个list<map>封装上传的文件的数据
			List<Map<String, String>> ups = new ArrayList<Map<String, String>>();
			for (FileItem file : list) {
				Map<String, String> mm = new HashMap<String, String>();
				// 获取文件�?
				String fileName = file.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				String fileType = file.getContentType();
				InputStream in = file.getInputStream();
				int size = in.available();
				// 使用工具�?
				// FileUtils.copyInputStreamToFile(in,new
				// File(path+"/"+fileName));
				file.write(new File(path + "/" + fileName));
				mm.put("fileName", fileName);
				mm.put("fileType", fileType);
				mm.put("size", "" + size);
				ups.add(mm);
				file.delete();
			}
			request.setAttribute("ups", ups);
			// 转发
			request.getRequestDispatcher("/jsps/show.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpDescServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 可以获取中文的文件名
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try {
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			// 声明�?个map用于封装信息
			Map<String, Object> img = new HashMap<String, Object>();
			for (FileItem file : list) {
				// 第一步：判断是否是普通的表单�?
				if (file.isFormField()) {
					String fileName = file.getFieldName();// <input type="text"
															// name="desc">=desc
					String value = file.getString("UTF-8");// 默认以ISO方式读取数据
					System.err.println(fileName + "=" + value);
					// 放入图片的说�?
					img.put("desc", value);
				} else {// 说明是一个文�?
					String fileName = file.getName();// 以前的名�?
					// 处理文件�?
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					img.put("oldName", fileName);
					// 修改名称
					String extName = fileName.substring(fileName
							.lastIndexOf("."));
					String newName = UUID.randomUUID().toString()
							.replace("-", "")
							+ extName;
					// 保存新的名称
					img.put("newName", newName);
					file.write(new File(path + "/" + newName));
					System.err.println("文件名是:" + fileName);
					System.err.println("文件大小是：" + file.getSize());
					img.put("size", file.getSize());
					file.delete();
				}
			}
			// 将img=map放到req
			request.setAttribute("img", img);
			// 转发
			request.getRequestDispatcher("/jsps/desc.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpImgServlet734654(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory(1024 * 10, new File(
				"d:/a"));
		ServletFileUpload up = new ServletFileUpload(disk);
		try {
			List<FileItem> list = up.parseRequest(request);
			// 只接收图�?*.jpg-iamge/jpege.,bmp/imge/bmp,png,
			List<String> imgs = new ArrayList<String>();
			for (FileItem file : list) {
				if (file.getContentType().contains("image/")) {
					String fileName = file.getName();
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					// 获取扩展
					String extName = fileName.substring(fileName
							.lastIndexOf("."));// .jpg
					// UUID
					String uuid = UUID.randomUUID().toString().replace("-", "");
					// 新名�?
					String newName = uuid + extName;
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(path + "/" + newName));
					// 放到list
					imgs.add(newName);
				}
				file.delete();
			}
			request.setAttribute("imgs", imgs);
			request.getRequestDispatcher("/jsps/imgs.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpServlet623543(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String txt = req.getParameter("txt");// 返回的是null
		System.err.println("txt is :" + txt);
		System.err.println("=========================================");
		InputStream in = req.getInputStream();
		// byte[] b= new byte[1024];
		// int len = 0;
		// while((len=in.read(b))!=-1){
		// String s = new String(b,0,len);
		// System.err.print(s);
		// }
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine = br.readLine();// 读取第一行，且第�?行是分隔符号
		String fileName = br.readLine();
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);// bafasd.txt"
		fileName = fileName.substring(0, fileName.length() - 1);
		br.readLine();
		br.readLine();
		String data = null;
		// 获取当前项目的运行路�?
		String projectPath = getServletContext().getRealPath("/up");
		PrintWriter out = new PrintWriter(projectPath + "/" + fileName);
		while ((data = br.readLine()) != null) {
			if (data.equals(firstLine + "--")) {
				break;
			}
			out.println(data);
		}
		out.close();
	}

	

 

	public void DirServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try {
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			for (FileItem file : list) {
				if (!file.isFormField()) {
					String fileName = file.getName();
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					String extName = fileName.substring(fileName
							.lastIndexOf("."));
					String newName = UUID.randomUUID().toString()
							.replace("-", "")
							+ extName;
					// 第一步：获取新名称的hashcode
					int code = newName.hashCode();
					// 第二步：获取后一位做为第�?层目�?
					String dir1 = Integer.toHexString(code & 0xf);
					// 获取第二层的目录
					String dir2 = Integer.toHexString((code >> 4) & 0xf);
					String savePath = dir1 + "/" + dir2;
					// 组成保存的目�?
					savePath = path + "/" + savePath;
					// 判断目录是否存在
					File f = new File(savePath);
					if (!f.exists()) {
						// 创建目录
						f.mkdirs();
					}
					// 保存文件
					file.write(new File(savePath + "/" + newName));
					file.delete();
					// 带路径保存到request
					request.setAttribute("fileName", dir1 + "/" + dir2 + "/"
							+ newName);
				}
			}
			request.getRequestDispatcher("/jsps/show.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GzipServlet2(HttpServletRequest request,
			HttpServletResponse resp) throws ServletException, IOException {
		// 声明准备被压缩的数据
		String str = "Hello你好Hello你好在内存中声明�?Hello你好�?"
				+ "内存中声明一个Hello你好在内存中声明�?个Hello�?"
				+ "好在内存中声明一�?<br/>容器声明准备被压缩获取准备被压缩"
				+ "的数据的字节码的数据容器声明准备被压缩获取准备被压缩的数"
				+ "据的字节码的数据容器声明准备被压缩获取准备被压缩的数据的"
				+ "字节码的数据个容器声明准备被压缩获取准备被压缩的数据的字节码�?"
				+ "数据在内存中声明�?个容器声明准备被压缩获取准备被压缩的数据" + "的字节码的数�?";
		// 2：获取准备被压缩的数据的字节�?
		byte[] src = str.getBytes("UTF-8");
		// 3:在内存中声明�?个容�?
		ByteArrayOutputStream destByte = new ByteArrayOutputStream();
		// ４：声明压缩的工具流，并设置压缩的目的地为destByte
		GZIPOutputStream zip = new GZIPOutputStream(destByte);
		// 5:写入数据
		zip.write(src);
		// 6:关闭压缩工具�?
		zip.close();
		System.err.println("压缩之前字节码大小：" + src.length);
		// ７：获取压缩以后数据
		byte[] dest = destByte.toByteArray();
		System.err.println("压缩以后的字节码大小�?" + dest.length);
		// 8:必须要输出压缩以后字节数�?
		resp.setContentType("text/html;charset=UTF-8");
		// 9:必须要使用字节流来输出信�?
		OutputStream out = resp.getOutputStream();
		// 10:通知浏览器�?�这是压缩的数据，要求浏览器解压
		resp.setHeader("Content-encoding", "gzip");
		// 11:通知浏览器压缩数据的长度
		resp.setContentLength(dest.length);
		// 10:输出
		out.write(dest);
	}

	

	public void CheckImgServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 禁止缓存
		// response.setHeader("Cache-Control", "no-cache");
		// response.setHeader("Pragma", "no-cache");
		// response.setDateHeader("Expires", -1);
		int width = 120;
		int height = 30;
		// 步骤�? 绘制�?张内存中图片
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 步骤�? 图片绘制背景颜色 ---通过绘图对象
		Graphics graphics = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
		// 绘制任何图形之前 都必须指定一个颜�?
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		// 步骤�? 绘制边框
		graphics.setColor(Color.WHITE);
		graphics.drawRect(0, 0, width - 1, height - 1);
		// 步骤�? 四个随机数字
		Graphics2D graphics2d = (Graphics2D) graphics;
		// 设置输出字体
		graphics2d.setFont(new Font("宋体", Font.BOLD, 18));
		Random random = new Random();// 生成随机�?
		int index = random.nextInt(words.size());
		String word = words.get(index);// 获得成语
		// 定义x坐标
		int x = 10;
		for (int i = 0; i < word.length(); i++) {
			// 随机颜色
			graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 旋转 -30 --- 30�?
			int jiaodu = random.nextInt(60) - 30;
			// 换算弧度
			double theta = jiaodu * Math.PI / 180;
			// 获得字母数字
			char c = word.charAt(i);
			// 将c 输出到图�?
			graphics2d.rotate(theta, x, 20);
			graphics2d.drawString(String.valueOf(c), x, 20);
			graphics2d.rotate(-theta, x, 20);
			x += 30;
		}
		// 将验证码内容保存session
		request.getSession().setAttribute("checkcode_session", word);
		System.out.println(word);
		// 步骤�? 绘制干扰�?
		// graphics.setColor(getRandColor(160, 200));
		// int x1;
		// int x2;
		// int y1;
		// int y2;
		// for (int i = 0; i < 30; i++) {
		// x1 = random.nextInt(width);
		// x2 = random.nextInt(12);
		// y1 = random.nextInt(height);
		// y2 = random.nextInt(12);
		// graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
		// }
		// 将上面图片输出到浏览�? ImageIO
		graphics.dispose();// 释放资源
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
	}

	/**
	 * 取其某一范围的color
	 * 
	 * @param fc
	 *            int 范围参数1
	 * @param bc
	 *            int 范围参数2
	 * @return Color
	 */
	private Color getRandColor(int fc, int bc) {
		// 取其随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void CodeServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		int width = 60;
		int height = 30;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		String sCode = "";
		g.setFont(new Font("宋体", Font.BOLD, 18));
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			int a = r.nextInt(10);
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			g.setColor(c);
			g.drawString("" + a, i * 15, height - 5);
			sCode += a;
		}
		request.getSession().setAttribute("sCode", sCode);
		for (int i = 0; i < 4; i++) {
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			g.setColor(c);
			g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60),
					r.nextInt(30));
		}
		g.dispose();
		ImageIO.write(img, "JPEG", response.getOutputStream());
	}

	public void GetImageServlet2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得�?张图�?
		// 创建图片 -- 在内存中
		int width = 80;
		int height = 40;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 创建图层，获得画�?
		Graphics g = image.getGraphics();
		// 确定画笔颜色
		g.setColor(Color.BLACK);
		// 填充�?个矩�?
		g.fillRect(0, 0, width, height);
		// 只需要一个边�?
		// 设置颜色
		g.setColor(Color.WHITE);
		// 填充�?个矩�?
		g.fillRect(1, 1, width - 2, height - 2);
		// 填充字符
		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		// 设置字体
		g.setFont(new Font("宋体", Font.BOLD, 30));
		// 缓存随机生成的字�?
		StringBuffer buf = new StringBuffer();
		// 随机获得4个字�?
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			// 设置随机颜色
			g.setColor(new Color(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			// 获得�?个随机字�?
			int index = random.nextInt(62);
			// 截取字符�?
			String str = data.substring(index, index + 1); // [)
			// �?要将随机的字符，写到图片�?
			g.drawString(str, 20 * i, 30);
			// 缓存
			buf.append(str);
		}
		// 将获得随机字符串，保存到session
		// * 获得session
		HttpSession session = request.getSession();
		// * 保存�?
		session.setAttribute("number", buf.toString());
		// 干扰�?
		for (int i = 0; i < 10; i++) {
			// 设置随机颜色
			g.setColor(new Color(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			// 随机画直�?
			g.drawLine(random.nextInt(width), random.nextInt(height),
					random.nextInt(width), random.nextInt(height));
		}
		/**
		 * <extension>jpg</extension> <mime-type>image/jpeg</mime-type>
		 */
		// 通知浏览器发送的数据时一张图�?
		response.setContentType("image/jpeg");
		// 将图片发送给浏览�?
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	public void VerifyCodeServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1. 生成图片 2. 保存图片上的文本到session域中 3. 把图片响应给客户�?
		 */
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		request.getSession().setAttribute("session_vcode", vc.getText());// 保存图片上的文本到session�?
		VerifyCode.output(image, response.getOutputStream());
	}

	public void UploadServlet3(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// �����ϴ��ļ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��л�����Ĵ�С��Ĭ��?10K
		factory.setSizeThreshold(100 * 1024);
		// �����ϴ��ļ���ʱ��ŵ�Ŀ�?
		String tempPath = this.getServletContext().getRealPath(
				FileUtil.tempPath11);
		factory.setRepository(new File(tempPath));
		// �����ϴ��ļ�����[����]
		ServletFileUpload upload = new ServletFileUpload(factory);
		// �����ϴ��ļ������ı��뷽ʽ
		upload.setHeaderEncoding("UTF-8");
		// �ͻ����ϴ��ļ��Ƿ�ʹ��MIMEЭ�飬
		boolean flag = upload.isMultipartContent(request);
		if (!flag) {
			// ������MIMEЭ���ϴ��ļ�
			throw new ServletException();
		} else {
			/*
			 * ����MIMEЭ���ϴ����ļ�������request�е������ϴ�����
			 * ÿ�����ݷ�װ��һ������FileItem��FileItem�����ͨ�ֶκ��ϴ��ֶζ���?
			 */
			try {
				List<FileItem> fileItemList = upload.parseRequest(request);
				for (FileItem fileItem : fileItemList) {
					if (fileItem.isFormField()) {
						// �ض�����ͨ�ֶ�
						String fieldName = fileItem.getFieldName();
						String fieldValue = fileItem.getString("UTF-8");
						System.out.println(fieldName + ":" + fieldValue);
					} else {
						// �ض����ϴ��ֶ�
						String realFileName = fileItem.getName();
						// ȡ����ʵ�ļ���
						realFileName = FileUtil.getRealFileName(realFileName);
						// ͨ����ʵ�ļ������Ψһ���ļ���?
						String uuidFileName = FileUtil
								.makeUuidFileName(realFileName);
						// ͨ��λ���㻻���uploadĿ¼�µ���Ŀ¼
						String uploadPath = this.getServletContext()
								.getRealPath(FileUtil.uploadPath);
						String uuidFilePath = FileUtil.makeUuidFilePath(
								uploadPath, uuidFileName);
						// ȡ���ļ�������
						InputStream is = fileItem.getInputStream();
						// ȡ���ļ������?
						FileUtil.doSave(is, uuidFileName, uuidFilePath);
						// ���ϴ��ļ��������ʱ�ļ�ɾ��?
						fileItem.delete();
						request.setAttribute("message", "�ϴ��ļ��ɹ�");
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "�ϴ��ļ�ʧ��");
				request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
						request, response);
			}
		}
	}

	public void UploadServlet2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ȡ���ϴ�ʹ�õ���ʱ����ʵĿ¼
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		String uploadPath = this.getServletContext().getRealPath(
				"/WEB-INF/upload");
		// �����ϴ��ļ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��л�����Ĵ�С��Ĭ��?10K
		factory.setSizeThreshold(100 * 1024);
		// �����ϴ��ļ���ʱ��ŵ�Ŀ�?
		factory.setRepository(new File(tempPath));
		// �����ϴ��ļ�����[����]
		ServletFileUpload upload = new ServletFileUpload(factory);
		// �����ϴ��ļ������ı��뷽ʽ
		upload.setHeaderEncoding("UTF-8");
		// �ͻ����ϴ��ļ��Ƿ�ʹ��MIMEЭ�飬
		boolean flag = upload.isMultipartContent(request);
		if (!flag) {
			// ������MIMEЭ���ϴ��ļ�
			throw new ServletException();
		} else {
			/*
			 * ����MIMEЭ���ϴ����ļ�������request�е������ϴ�����
			 * ÿ�����ݷ�װ��һ������FileItem��FileItem�����ͨ�ֶκ��ϴ��ֶζ���?
			 */
			try {
				List<FileItem> fileItemList = upload.parseRequest(request);
				for (FileItem fileItem : fileItemList) {
					if (fileItem.isFormField()) {
						// �ض�����ͨ�ֶ�
						String fieldName = fileItem.getFieldName();
						String fieldValue = fileItem.getString("UTF-8");
						System.out.println(fieldName + ":" + fieldValue);
					} else {
						// �ض����ϴ��ֶ�
						String realFileName = fileItem.getName();
						int index = realFileName.lastIndexOf("\\");
						if (index >= 0) {
							// IE6�����?
							realFileName = realFileName.substring(index + 1);
						}
						// ͨ����ʵ�ļ������Ψһ���ļ���?
						String uuidFileName = makeUuidFileName(realFileName);
						// ͨ��λ���㻻���uploadĿ¼�µ���Ŀ¼
						String uuidFilePath = makeUuidFilePath(uploadPath,
								uuidFileName);
						// ȡ���ļ�������
						InputStream is = fileItem.getInputStream();
						// ȡ���ļ������?
						OutputStream os = new FileOutputStream(uuidFilePath
								+ "/" + uuidFileName);
						byte[] buf = new byte[1024];
						int len = 0;
						while ((len = is.read(buf)) > 0) {
							os.write(buf, 0, len);
						}
						is.close();
						os.close();
						// ���ϴ��ļ��������ʱ�ļ�ɾ��?
						fileItem.delete();
						request.setAttribute("message", "�ϴ��ļ��ɹ�");
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "�ϴ��ļ�ʧ��");
				request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
						request, response);
			}
		}
		/*
		 * ��ʽ�� InputStream is = request.getInputStream(); OutputStream os =
		 * response.getOutputStream(); byte[] buf = new byte[1024]; int len = 0;
		 * while( (len=is.read(buf))>0 ){ os.write(buf,0,len); } is.close();
		 * os.close();
		 */
		/*
		 * ��ʽһ String username = request.getParameter("username"); String
		 * upfile = request.getParameter("upfile");
		 * System.out.println("�ϴ��û���" + username);
		 * System.out.println("�ϴ��ļ���" + upfile);
		 */
	}

	/*
	 * uploadPath="/upload" uuidFileName="fdafdsfdsa_a.JPG" return /upload/8/a
	 */
	private String makeUuidFilePath(String uploadPath, String uuidFileName) {
		String uuidFilePath = null;
		int code = uuidFileName.hashCode();// 8
		int dir1 = code & 0xF;// 3
		int dir2 = code >> 4 & 0xF;// A
		File file = new File(uploadPath + "/" + dir1 + "/" + dir2);
		// ����Ŀ¼δ����
		if (!file.exists()) {
			// һ���Դ���N��Ŀ¼
			file.mkdirs();
		}
		uuidFilePath = file.getPath();
		return uuidFilePath;
	}

	/*
	 * realFileName="a.JPG" return "fdafdsfdsa_a.JPG"
	 */
	private String makeUuidFileName(String realFileName) {
		return UUID.randomUUID().toString() + "_" + realFileName;
	}

	public void UploadServlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// User user = FileUtil.doUpload(request);
			String uploadPath = this.getServletContext().getRealPath(
					FileUtil.uploadPath);
//			List<Up> upList = new ArrayList<Up>();
			// д��Ӳ��
			// FileUtil.doSave(user,uploadPath,upList);
			// д����ݿ��
//			UpService upService = new UpService();
//			upService.addUps(upList);
			request.setAttribute("message", "�ϴ��ļ��ɹ�");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�ϴ��ļ�ʧ��");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
		}
	}

	public void DirServlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try {
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			for (FileItem file : list) {
				if (!file.isFormField()) {
					String fileName = file.getName();
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					String extName = fileName.substring(fileName
							.lastIndexOf("."));
					String newName = UUID.randomUUID().toString()
							.replace("-", "")
							+ extName;
					// 第一步：获取新名称的hashcode
					int code = newName.hashCode();
					// 第二步：获取后一位做为第�?层目�?
					String dir1 = Integer.toHexString(code & 0xf);
					// 获取第二层的目录
					String dir2 = Integer.toHexString((code >> 4) & 0xf);
					String savePath = dir1 + "/" + dir2;
					// 组成保存的目�?
					savePath = path + "/" + savePath;
					// 判断目录是否存在
					File f = new File(savePath);
					if (!f.exists()) {
						// 创建目录
						f.mkdirs();
					}
					// 保存文件
					file.write(new File(savePath + "/" + newName));
					file.delete();
					// 带路径保存到request
					request.setAttribute("fileName", dir1 + "/" + dir2 + "/"
							+ newName);
				}
			}
			request.getRequestDispatcher("/jsps/show.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DownloadServlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ȡ��uuid�ļ������URL����
		String uuidFileName = request.getParameter("uuidFileName");
		byte[] buf = uuidFileName.getBytes("ISO8859-1");
		uuidFileName = new String(buf, "UTF-8");
		try {
			// ���UUID�ļ����ѯUp����
//			UpService upService = new UpService();
			// Up up = upService.findUpByUuidFileName(uuidFileName);
			// ���������������ط�ʽ���ļ�
			// response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(up.getRealFileName(),"UTF-8"));
			// ��������
			String uploadPath = this.getServletContext().getRealPath(
					FileUtil.uploadPath);
			String uuidFilePath = FileUtil.makeUuidFilePath(uploadPath,
					uuidFileName);
			InputStream is = new FileInputStream(uuidFilePath + "/"
					+ uuidFileName);
			OutputStream os = response.getOutputStream();
			int len = 0;
			buf = new byte[1024];
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�����ļ�ʧ��");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
		}
	}

	public void DownloadServlet2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uuidFileName = request.getParameter("uuidFileName");
		byte[] buf = uuidFileName.getBytes("ISO8859-1");
		uuidFileName = new String(buf, "UTF-8");
		int index = uuidFileName.lastIndexOf("_");
		String realFileName = uuidFileName.substring(index + 1);
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(realFileName, "UTF-8"));
		String uploadPath = this.getServletContext().getRealPath(
				FileUtil.uploadPath);
		String uuidFilePath = FileUtil.makeUuidFilePath(uploadPath,
				uuidFileName);
		InputStream is = new FileInputStream(uuidFilePath + "/" + uuidFileName);
		// ģʽ��/WEB-INF/upload/12/4/43213_cc.jpg
		OutputStream os = response.getOutputStream();
		buf = new byte[1024];
		int len = 0;
		while ((len = is.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
		is.close();
		os.close();
	}

	protected void v(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String filePath = new String(req.getParameter("filePath").getBytes(
				"ISO-8859-1"), "UTF-8");
		File file = new File(filePath);
		if (file.exists()) {
			String fileName = file.getName();
			resp.setContentType("application/x-download");
			resp.addHeader(
					"Content-Disposition",
					"attachment;filename="
							+ URLEncoder.encode(fileName.substring(fileName
									.indexOf("_") + 1), "UTF-8"));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = resp.getOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} else {
			resp.setContentType("text/html");
			PrintWriter pw = resp.getWriter();
			pw.println("<h1 style='color:red'>文件不存�?</h1>");
		}
	}

	public void DownServlet1(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		// 第一步：设置响应的类�?
		resp.setContentType("application/force-download");
		// 第二读取文件
		String path = getServletContext().getRealPath("/up/" + name);
		InputStream in = new FileInputStream(path);
		// 设置响应�?
		// 对文件名进行url编码
		name = URLEncoder.encode(name, "UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=" + name);
		resp.setContentLength(in.available());
		// 第三步：�?始文件copy
		OutputStream out = resp.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
		in.close();
	}

	public void DownServlet2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oldname = request.getParameter("oldname");
		oldname = new String(oldname.getBytes("ISO-8859-1"), "UTF-8");
		String newname = request.getParameter("newname");
		// 此文件是否存�?
		String path = getServletContext().getRealPath("/up/" + newname);
		File file = new File(path);
		if (file.exists()) {
			response.setContentType("application/force-download");
			oldname = URLEncoder.encode(oldname, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ oldname);
			response.setContentLength((int) file.length());
			InputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int len = -1;
			OutputStream out = response.getOutputStream();
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
		} else {
			System.err.println("你下载文件已经不存在...");
		}
	}

	public void DownServlet3(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oldname = request.getParameter("oldname");
		oldname = new String(oldname.getBytes("ISO-8859-1"), "UTF-8");
		String newname = request.getParameter("newname");
		// 此文件是否存�?
		String path = getServletContext().getRealPath("/up/" + newname);
		File file = new File(path);
		if (file.exists()) {
			response.setContentType("application/force-download");
			oldname = URLEncoder.encode(oldname, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ oldname);
			response.setContentLength((int) file.length());
			InputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int len = -1;
			OutputStream out = response.getOutputStream();
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
		} else {
			System.err.println("你下载文件已经不存在...");
		}
	}

	public void ListFileServletdoGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 定位下载文件的目�?
		String uploadPath = this.getServletContext().getRealPath(
				FileUtil.uploadPath);
		// 创建Map<UUID文件�?,真实文件�?>
		Map<String, String> map = new HashMap<String, String>();
		// 取得下载文件的相关信�?
		ListFileServletgetFiles(uploadPath, map);
		// 转发到list.jsp显示可供下载的文�?
		request.setAttribute("map", map);
		request.getRequestDispatcher("/WEB-INF/list.jsp").forward(request,
				response);
	}

	// 递归询查�?有可供下载的文件
	private void ListFileServletgetFiles(String uploadPath,
			Map<String, String> map) {
		File file = new File(uploadPath);
		// 如果file表示文件
		if (file.isFile()) {// 出口
			// 取得文件名，即UUID文件�?
			String uuidFileName = file.getName();
			int index = uuidFileName.indexOf("_");
			String realFileName = uuidFileName.substring(index + 1);
			// 存放到Map集合�?
			map.put(uuidFileName, realFileName);
		} else {
			// 必定是目�?
			// 取得该目录下的所有内�?
			File[] files = file.listFiles();
			for (File f : files) {
				// 递归调用自已
				ListFileServletgetFiles(f.getPath(), map);
			}
		}
	}

	public void Up2Servlet1(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTf-8");
		// 获取项目的路�?
		String path = getServletContext().getRealPath("/up");
		// 第一步声明diskfileitemfactory工厂类，用于在指的磁盘上设置�?个临时目�?
		DiskFileItemFactory disk = new DiskFileItemFactory(1024 * 10, new File(
				"d:/a"));
		// 第二步：声明ServletFileUpoload，接收上面的临时目录
		ServletFileUpload up = new ServletFileUpload(disk);
		// 第三步：解析request
		try {
			List<FileItem> list = up.parseRequest(req);
			// 如果就一个文�?
			FileItem file = list.get(0);
			// 获取文件�?,带路�?
			String fileName = file.getName();
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			// 获取文件的类�?
			String fileType = file.getContentType();
			// 获取文件的字节码
			InputStream in = file.getInputStream();
			// 声明输出字节�?
			OutputStream out = new FileOutputStream(path + "/" + fileName);
			// 文件copy
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
			long size = file.getInputStream().available();
			// 删除上传的临时文�?
			file.delete();
			// 显示数据
			resp.setContentType("text/html;charset=UTf-8");
			PrintWriter op = resp.getWriter();
			op.print("文件上传成功<br/>文件�?:" + fileName);
			op.print("<br/>文件类型:" + fileType);
			op.print("<br/>文件大小（bytes�?" + size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Up3Servlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		// 声明disk
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setSizeThreshold(1024 * 1024);
		disk.setRepository(new File("d:/a"));
		// 声明解析requst的servlet
		ServletFileUpload up = new ServletFileUpload(disk);
		up.setSizeMax(1024 * 1024 * 100);
		up.setFileSizeMax(1024 * 1024 * 10);
		try {
			// 解析requst
			List<FileItem> list = up.parseRequest(request);
			// 声明�?个list<map>封装上传的文件的数据
			List<Map<String, String>> ups = new ArrayList<Map<String, String>>();
			for (FileItem file : list) {
				Map<String, String> mm = new HashMap<String, String>();
				// 获取文件�?
				String fileName = file.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				String fileType = file.getContentType();
				InputStream in = file.getInputStream();
				int size = in.available();
				// 使用工具�?
				// FileUtils.copyInputStreamToFile(in,new
				// File(path+"/"+fileName));
				file.write(new File(path + "/" + fileName));
				mm.put("fileName", fileName);
				mm.put("fileType", fileType);
				mm.put("size", "" + size);
				ups.add(mm);
				file.delete();
			}
			request.setAttribute("ups", ups);
			// 转发
			request.getRequestDispatcher("/jsps/show.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpDescServlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 可以获取中文的文件名
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try {
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			// 声明�?个map用于封装信息
			Map<String, Object> img = new HashMap<String, Object>();
			for (FileItem file : list) {
				// 第一步：判断是否是普通的表单�?
				if (file.isFormField()) {
					String fileName = file.getFieldName();// <input type="text"
															// name="desc">=desc
					String value = file.getString("UTF-8");// 默认以ISO方式读取数据
					System.err.println(fileName + "=" + value);
					// 放入图片的说�?
					img.put("desc", value);
				} else {// 说明是一个文�?
					String fileName = file.getName();// 以前的名�?
					// 处理文件�?
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					img.put("oldName", fileName);
					// 修改名称
					String extName = fileName.substring(fileName
							.lastIndexOf("."));
					String newName = UUID.randomUUID().toString()
							.replace("-", "")
							+ extName;
					// 保存新的名称
					img.put("newName", newName);
					file.write(new File(path + "/" + newName));
					System.err.println("文件名是:" + fileName);
					System.err.println("文件大小是：" + file.getSize());
					img.put("size", file.getSize());
					file.delete();
				}
			}
			// 将img=map放到req
			request.setAttribute("img", img);
			// 转发
			request.getRequestDispatcher("/jsps/desc.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpImgServlet1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = new DiskFileItemFactory(1024 * 10, new File(
				"d:/a"));
		ServletFileUpload up = new ServletFileUpload(disk);
		try {
			List<FileItem> list = up.parseRequest(request);
			// 只接收图�?*.jpg-iamge/jpege.,bmp/imge/bmp,png,
			List<String> imgs = new ArrayList<String>();
			for (FileItem file : list) {
				if (file.getContentType().contains("image/")) {
					String fileName = file.getName();
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);
					// 获取扩展
					String extName = fileName.substring(fileName
							.lastIndexOf("."));// .jpg
					// UUID
					String uuid = UUID.randomUUID().toString().replace("-", "");
					// 新名�?
					String newName = uuid + extName;
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(path + "/" + newName));
					// 放到list
					imgs.add(newName);
				}
				file.delete();
			}
			request.setAttribute("imgs", imgs);
			request.getRequestDispatcher("/jsps/imgs.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@SuppressWarnings("unchecked")
	public void UploaderServlet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Integer schunk = null;// 分割块数
		Integer schunks = null;// 总分割数
		String name = null;// 文件�?
		BufferedOutputStream outputStream = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				factory.setRepository(new File(uploadPath));// 设置临时目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				upload.setSizeMax(5 * 1024 * 1024);// 设置附近大小
				List<FileItem> items = upload.parseRequest(request);
				// 生成新文件名
				String newFileName = null;
				for (FileItem item : items) {
					if (!item.isFormField()) {// 如果是文件类�?
						name = item.getName();// 获得文件�?
						newFileName = UUID.randomUUID().toString()
								.replace("-", "").concat(".")
								.concat(FilenameUtils.getExtension(name));
						if (name != null) {
							String nFname = newFileName;
							if (schunk != null) {
								nFname = schunk + "_" + name;
							}
							File savedFile = new File(uploadPath, nFname);
							item.write(savedFile);
						}
					} else {
						// 判断是否带分割信�?
						if (item.getFieldName().equals("chunk")) {
							schunk = Integer.parseInt(item.getString());
						}
						if (item.getFieldName().equals("chunks")) {
							schunks = Integer.parseInt(item.getString());
						}
					}
				}
				if (schunk != null && schunk + 1 == schunks) {
					outputStream = new BufferedOutputStream(
							new FileOutputStream(new File(uploadPath,
									newFileName)));
					// 遍历文件合并
					for (int i = 0; i < schunks; i++) {
						File tempFile = new File(uploadPath, i + "_" + name);
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						outputStream.write(bytes);
						outputStream.flush();
						tempFile.delete();
					}
					outputStream.flush();
				}
				response.getWriter()
						.write("{\"status\":true,\"newName\":\"" + newFileName
								+ "\"}");
			} catch (FileUploadException e) {
				e.printStackTrace();
				response.getWriter().write("{\"status\":false}");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("{\"status\":false}");
			} finally {
				try {
					if (outputStream != null) outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}



	protected void UploadServlet(HttpServletRequest req,
			HttpServletResponse request) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(TEMP_MAX_SIZE);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(FILE_MAX_SIZE);
		try {
			List<FileItem> list = upload.parseRequest(req);
			Iterator<FileItem> it = list.iterator();
			while (it.hasNext()) {
				FileItem fi = it.next();
				if (!fi.isFormField()) {
					InputStream in = fi.getInputStream();
					FileOutputStream out = new FileOutputStream(new File(
							uploadPath + System.currentTimeMillis() + "_"
									+ fi.getName()));
					byte[] buf = new byte[1024];
					int len = -1;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}
			}
		} catch (FileUploadException e) {
			System.out.println(e);
		} finally {
		}
		request.sendRedirect("/mine/plugins/fileupload/filelist.jsp");
	}


	public void UpServlet1(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String txt = req.getParameter("txt");// 返回的是null
		System.err.println("txt is :" + txt);
		System.err.println("=========================================");
		InputStream in = req.getInputStream();
		// byte[] b= new byte[1024];
		// int len = 0;
		// while((len=in.read(b))!=-1){
		// String s = new String(b,0,len);
		// System.err.print(s);
		// }
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine = br.readLine();// 读取第一行，且第�?行是分隔符号
		String fileName = br.readLine();
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);// bafasd.txt"
		fileName = fileName.substring(0, fileName.length() - 1);
		br.readLine();
		br.readLine();
		String data = null;
		// 获取当前项目的运行路�?
		String projectPath = getServletContext().getRealPath("/up");
		PrintWriter out = new PrintWriter(projectPath + "/" + fileName);
		while ((data = br.readLine()) != null) {
			if (data.equals(firstLine + "--")) {
				break;
			}
			out.println(data);
		}
		out.close();
	}


	public void FileServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��ȡ�������? ��ֲ�ͬ�Ĳ�������?
		String method = request.getParameter("method");
		if ("upload".equals(method)) {
			// �ϴ�
			upload(request,response);
		}
		
		else if ("downList".equals(method)) {
			// ���������б�
			downList(request,response);
		}
		
		else if ("down".equals(method)) {
			// ����
			down(request,response);
		}
	}
	
	
	/**
	 * 1. �ϴ�
	 */
	private void upload(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		try {
			// 1. ������������
			FileItemFactory factory = new DiskFileItemFactory();
			// 2. �ļ��ϴ����Ĺ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ���ô�С���Ʋ���
			upload.setFileSizeMax(10*1024*1024);	// �����ļ���С����
			upload.setSizeMax(50*1024*1024);		// ���ļ���С����
			upload.setHeaderEncoding("UTF-8");		// �������ļ����봦��

			// �ж�
			if (upload.isMultipartContent(request)) {
				// 3. ���������ת��Ϊlist����
				List<FileItem> list = upload.parseRequest(request);
				// ����
				for (FileItem item : list){
					// �жϣ���ͨ�ı����?
					if (item.isFormField()){
						// ��ȡ���?
						String name = item.getFieldName();
						// ��ȡֵ
						String value = item.getString();
						System.out.println(value);
					} 
					// �ļ��??��
					else {
						/******** �ļ��ϴ� ***********/
						// a. ��ȡ�ļ����?
						String name = item.getName();
						// ----�����ϴ��ļ�����������----
						// a1. �ȵõ�Ψһ���?
						String id = UUID.randomUUID().toString();
						// a2. ƴ���ļ���
						name = id + "#" + name;						
						
						// b. �õ��ϴ�Ŀ¼
						String basePath = getServletContext().getRealPath("/upload");
						// c. ����Ҫ�ϴ����ļ�����
						File file = new File(basePath,name);
						// d. �ϴ�
						item.write(file);
						item.delete();  // ɾ���������ʱ�������ʱ�ļ�
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}

	
	/**
	 * 2. ���������б�
	 */
	private void downList(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// ʵ��˼·���Ȼ�ȡuploadĿ¼�������ļ����ļ����ٱ��棻��ת��down.jsp�б�չʾ
		
		//1. ��ʼ��map����Map<��Ψһ��ǵ��ļ���?, ����ļ���?>  ;
		Map<String,String> fileNames = new HashMap<String,String>();
		
		//2. ��ȡ�ϴ�Ŀ¼�����������е��ļ����ļ���
		String bathPath = getServletContext().getRealPath("/upload");
		// Ŀ¼
		File file = new File(bathPath);
		// Ŀ¼�£������ļ���
		String list[] = file.list();
		// ������?
		if (list != null && list.length > 0){
			for (int i=0; i<list.length; i++){
				// ȫ��
				String fileName = list[i];
				// ����
				String shortName = fileName.substring(fileName.lastIndexOf("#")+1);
				// ��װ
				fileNames.put(fileName, shortName);
			}
		}
		
		// 3. ���浽request��
		request.setAttribute("fileNames", fileNames);
		// 4. ת��
		request.getRequestDispatcher("/downlist.jsp").forward(request, response);

	}

	
	/**
	 *  3. ��������
	 */
	private void down(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// ��ȡ�û����ص��ļ����?(url��ַ��׷�����?,get)
		String fileName = request.getParameter("fileName");
		fileName = new String(fileName.getBytes("ISO8859-1"),"UTF-8");
		
		// �Ȼ�ȡ�ϴ�Ŀ¼·��
		String basePath = getServletContext().getRealPath("/upload");
		// ��ȡһ���ļ���
		InputStream in = new FileInputStream(new File(basePath,fileName));
		
		// ����ļ��������ģ���Ҫ����url����
		fileName = URLEncoder.encode(fileName, "UTF-8");
		// �������ص���Ӧͷ
		response.setHeader("content-disposition", "attachment;fileName=" + fileName);
		
		// ��ȡresponse�ֽ���
		OutputStream out = response.getOutputStream();
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = in.read(b)) != -1){
			out.write(b, 0, len);
		}
		// �ر�
		out.close();
		in.close();
		
		
	}
	
	

	public void DirServlet22(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try{
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			for(FileItem file:list){
				if(!file.isFormField()){
					String fileName = file.getName();
					fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
					String extName = fileName.substring(fileName.lastIndexOf("."));
					String newName = UUID.randomUUID().toString().replace("-","")+extName;
					//第一步：获取新名称的hashcode
					int code = newName.hashCode();
					//第二步：获取后一位做为第�?层目�?
					String dir1 = 
							Integer.toHexString(code & 0xf);
					//获取第二层的目录
					String dir2 = 
							Integer.toHexString((code>>4)&0xf);
					String savePath = dir1+"/"+dir2;
					//组成保存的目�?
					savePath=path+"/"+savePath;
					//判断目录是否存在
					File f = new File(savePath);
					if(!f.exists()){
						//创建目录
						f.mkdirs();
					}
					//保存文件
					file.write(new File(savePath+"/"+newName));
					file.delete();
					//带路径保存到request
					request.setAttribute("fileName",dir1+"/"+dir2+"/"+newName);
				}
			}
			
			request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void FastServlet1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try{
			ServletFileUpload up = new ServletFileUpload(disk);
			//以下是迭代器模式
			FileItemIterator it= up.getItemIterator(request);
			while(it.hasNext()){
				FileItemStream item =  it.next();
				String fileName = item.getName();
				fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
				InputStream in = item.openStream();
				FileUtils.copyInputStreamToFile(in,new File(path+"/"+fileName));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	


	public void UpImgServlet11(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory(1024*10,new File("d:/a"));
		ServletFileUpload up = new ServletFileUpload(disk);
		try{
			List<FileItem> list = up.parseRequest(request);
			//只接收图�?*.jpg-iamge/jpege.,bmp/imge/bmp,png,
			List<String> imgs = new ArrayList<String>();
			for(FileItem file :list){
				if(file.getContentType().contains("image/")){
					String fileName = file.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
					//获取扩展
					String extName = fileName.substring(fileName.lastIndexOf("."));//.jpg
					//UUID
					String uuid = UUID.randomUUID().toString().replace("-", "");
					//新名�?
					String newName = uuid+extName;
					
					
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(path+"/"+newName));
					//放到list
					imgs.add(newName);
				}
				file.delete();
			}
			request.setAttribute("imgs",imgs);
			request.getRequestDispatcher("/jsps/imgs.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	

	public void Up2Servlet11(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTf-8");
		//获取项目的路�?
		String path = getServletContext().getRealPath("/up");
		//第一步声明diskfileitemfactory工厂类，用于在指的磁盘上设置�?个临时目�?
		DiskFileItemFactory disk = 
				new DiskFileItemFactory(1024*10,new File("d:/a"));
		//第二步：声明ServletFileUpoload，接收上面的临时目录
		ServletFileUpload up = new ServletFileUpload(disk);
		//第三步：解析request
		try {
			List<FileItem> list =  up.parseRequest(req);
			//如果就一个文�?
			FileItem file = list.get(0);
			//获取文件�?,带路�?
			String fileName = file.getName();
			fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
			//获取文件的类�?
			String fileType = file.getContentType();
			//获取文件的字节码
			InputStream in = file.getInputStream();
			//声明输出字节�?
			OutputStream out = new FileOutputStream(path+"/"+fileName);
			//文件copy
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			out.close();
			
			long size = file.getInputStream().available();
			
			//删除上传的临时文�?
			file.delete();
			//显示数据
			resp.setContentType("text/html;charset=UTf-8");
			PrintWriter op = resp.getWriter();
			op.print("文件上传成功<br/>文件�?:"+fileName);
			op.print("<br/>文件类型:"+fileType);
			op.print("<br/>文件大小（bytes�?"+size);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	public void Up3Servlet11(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/up");
		//声明disk
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setSizeThreshold(1024*1024);
		disk.setRepository(new File("d:/a"));
		//声明解析requst的servlet
		ServletFileUpload up = new ServletFileUpload(disk);
		up.setSizeMax(1024*1024*100);
		up.setFileSizeMax(1024*1024*10);
		try{
			//解析requst
			List<FileItem> list = up.parseRequest(request);
			//声明�?个list<map>封装上传的文件的数据
			List<Map<String,String>> ups = new ArrayList<Map<String,String>>();
			for(FileItem file:list){
				Map<String,String> mm = new HashMap<String, String>();
				//获取文件�?
				String fileName = file.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
				String fileType = file.getContentType();
				InputStream in = file.getInputStream();
				int size = in.available();
				//使用工具�?
				//FileUtils.copyInputStreamToFile(in,new File(path+"/"+fileName));
				file.write(new File(path+"/"+fileName));
				mm.put("fileName",fileName);
				mm.put("fileType",fileType);
				mm.put("size",""+size);
				
				ups.add(mm);
				file.delete();
			}
			request.setAttribute("ups",ups);
			//转发
			request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	/**
	 * 处理目录打散�?
	 * 思想：对新生成的文件名进行二进制运算�?
	 * 先取后一�? int x = hashcode & 0xf;
	 * 再取后第二位:int y = (hashCode >> 4) & 0xf;
	 * @author wangjianme
	 *
	 */
		public void DirServlet11441(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			String path = getServletContext().getRealPath("/up");
			DiskFileItemFactory disk = 
					new DiskFileItemFactory();
			disk.setRepository(new File("d:/a"));
			try{
				ServletFileUpload up = new ServletFileUpload(disk);
				List<FileItem> list = up.parseRequest(request);
				for(FileItem file:list){
					if(!file.isFormField()){
						String fileName = file.getName();
						fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
						String extName = fileName.substring(fileName.lastIndexOf("."));
						String newName = UUID.randomUUID().toString().replace("-","")+extName;
						//第一步：获取新名称的hashcode
						int code = newName.hashCode();
						//第二步：获取后一位做为第�?层目�?
						String dir1 = 
								Integer.toHexString(code & 0xf);
						//获取第二层的目录
						String dir2 = 
								Integer.toHexString((code>>4)&0xf);
						String savePath = dir1+"/"+dir2;
						//组成保存的目�?
						savePath=path+"/"+savePath;
						//判断目录是否存在
						File f = new File(savePath);
						if(!f.exists()){
							//创建目录
							f.mkdirs();
						}
						//保存文件
						file.write(new File(savePath+"/"+newName));
						file.delete();
						//带路径保存到request
						request.setAttribute("fileName",dir1+"/"+dir2+"/"+newName);
					}
				}
				
				request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
	

		public void DownloadServlet99(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 1.得到下载文件名称
			String filename = new Date().toLocaleString() + "�?售榜�?.csv";
			// 2.设置下载文件的mimeType类型
			response.setContentType(this.getServletContext().getMimeType(filename));

			// 3.设置Content-Dispostion
			String agent = request.getHeader("User-agent");
			response.setHeader("Content-disposition", "attachement;filename=" + FileUtil.getDownloadFileName(filename, agent));

			// 4.得到�?售榜单信�?

//			List<Product> ps = null;
//			ProductService service = ProductServiceFactory.getInstance();
//			try {
//				User user = (User) request.getSession().getAttribute("user");
//				ps = service.findSell(user);
	//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} catch (PrivilegeException e) {
//				response.sendRedirect(request.getContextPath() + "/error/error.jsp");
//				return;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			response.setCharacterEncoding("gbk");
			response.getWriter().println("商品名称,�?售数�?");

//			for (Product p : ps) {
//				response.getWriter().println(
//						p.getName() + "," + p.getTotalSaleNum());
//				response.getWriter().flush();
//			}

			response.getWriter().close();
		}

		public void DownServlet88(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			String name = req.getParameter("name");
			//第一步：设置响应的类�?
			resp.setContentType("application/force-download");
			//第二读取文件
			String path = getServletContext().getRealPath("/up/"+name);
			InputStream in = new FileInputStream(path);
			//设置响应�?
			//对文件名进行url编码
			name = URLEncoder.encode(name, "UTF-8");
			resp.setHeader("Content-Disposition","attachment;filename="+name);
			resp.setContentLength(in.available());
			
			//第三步：�?始文件copy
			OutputStream out = resp.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}
		
		
 
		
		
		

		public void doPost993(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("gbk");
			RequestContext requestContext = new ServletRequestContext(request);

			if (FileUpload.isMultipartContent(requestContext)) {

				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setRepository(new File("c:/tmp/"));
				ServletFileUpload upload = new ServletFileUpload(factory);
				// upload.setHeaderEncoding("gbk");
				upload.setSizeMax(2000000);
				List items = new ArrayList();
				try {
					items = upload.parseRequest(request);
				} catch (FileUploadException e1) {
					System.out.println("文件上传发生错误" + e1.getMessage());
				}
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					if (fileItem.isFormField()) {
						System.out.println(fileItem.getFieldName() + "   " + fileItem.getName() + "   "
								+ new String(fileItem.getString().getBytes("iso8859-1"), "gbk"));
					} else {
						System.out.println(fileItem.getFieldName() + "   " + fileItem.getName() + "   "
								+ fileItem.isInMemory() + "    " + fileItem.getContentType() + "   " + fileItem.getSize());

						if (fileItem.getName() != null && fileItem.getSize() != 0) {
							File fullFile = new File(fileItem.getName());
							File newFile = new File("c:/temp/" + fullFile.getName());
							try {
								fileItem.write(newFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							System.out.println("文件没有选择 �? 文件内容为空");
						}
					}

				}
			}
		}
		
		
		
		
		

		public void Up2Servlet54643(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			req.setCharacterEncoding("UTf-8");
			//获取项目的路�?
			String path = getServletContext().getRealPath("/up");
			//第一步声明diskfileitemfactory工厂类，用于在指的磁盘上设置�?个临时目�?
			DiskFileItemFactory disk = 
					new DiskFileItemFactory(1024*10,new File("d:/a"));
			//第二步：声明ServletFileUpoload，接收上面的临时目录
			ServletFileUpload up = new ServletFileUpload(disk);
			//第三步：解析request
			try {
				List<FileItem> list =  up.parseRequest(req);
				//如果就一个文�?
				FileItem file = list.get(0);
				//获取文件�?,带路�?
				String fileName = file.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
				//获取文件的类�?
				String fileType = file.getContentType();
				//获取文件的字节码
				InputStream in = file.getInputStream();
				//声明输出字节�?
				OutputStream out = new FileOutputStream(path+"/"+fileName);
				//文件copy
				byte[] b = new byte[1024];
				int len = 0;
				while((len=in.read(b))!=-1){
					out.write(b,0,len);
				}
				out.close();
				
				long size = file.getInputStream().available();
				
				//删除上传的临时文�?
				file.delete();
				//显示数据
				resp.setContentType("text/html;charset=UTf-8");
				PrintWriter op = resp.getWriter();
				op.print("文件上传成功<br/>文件�?:"+fileName);
				op.print("<br/>文件类型:"+fileType);
				op.print("<br/>文件大小（bytes�?"+size);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		public void Up3Servlet73465(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			String path = getServletContext().getRealPath("/up");
			//声明disk
			DiskFileItemFactory disk = new DiskFileItemFactory();
			disk.setSizeThreshold(1024*1024);
			disk.setRepository(new File("d:/a"));
			//声明解析requst的servlet
			ServletFileUpload up = new ServletFileUpload(disk);
			up.setSizeMax(1024*1024*100);
			up.setFileSizeMax(1024*1024*10);
			try{
				//解析requst
				List<FileItem> list = up.parseRequest(request);
				//声明�?个list<map>封装上传的文件的数据
				List<Map<String,String>> ups = new ArrayList<Map<String,String>>();
				for(FileItem file:list){
					Map<String,String> mm = new HashMap<String, String>();
					//获取文件�?
					String fileName = file.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					String fileType = file.getContentType();
					InputStream in = file.getInputStream();
					int size = in.available();
					//使用工具�?
					//FileUtils.copyInputStreamToFile(in,new File(path+"/"+fileName));
					file.write(new File(path+"/"+fileName));
					mm.put("fileName",fileName);
					mm.put("fileType",fileType);
					mm.put("size",""+size);
					
					ups.add(mm);
					file.delete();
				}
				request.setAttribute("ups",ups);
				//转发
				request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void UpDescServlet8645765(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");//可以获取中文的文件名
			String path = getServletContext().getRealPath("/up");
			DiskFileItemFactory disk = 
					new DiskFileItemFactory();
			disk.setRepository(new File("d:/a"));
			try{
				ServletFileUpload up = 
						new ServletFileUpload(disk);
				List<FileItem> list = up.parseRequest(request);
				
				//声明�?个map用于封装信息
				Map<String,Object> img = new HashMap<String, Object>();
				for(FileItem file:list){
					//第一步：判断是否是普通的表单�?
					if(file.isFormField()){
						String fileName = file.getFieldName();//<input type="text" name="desc">=desc
						String value = file.getString("UTF-8");//默认以ISO方式读取数据
						System.err.println(fileName+"="+value);
						//放入图片的说�?
						img.put("desc",value);
					}else{//说明是一个文�?
						String fileName = file.getName();//以前的名�?
						//处理文件�?
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
						img.put("oldName",fileName);
						//修改名称
						String extName = fileName.substring(fileName.lastIndexOf("."));
						String newName = 
								UUID.randomUUID().toString().replace("-", "")+extName;
						//保存新的名称
						img.put("newName",newName);
						file.write(new File(path+"/"+newName));
						System.err.println("文件名是:"+fileName);
						System.err.println("文件大小是："+file.getSize());
						img.put("size",file.getSize());
						file.delete();
					}
				}
				//将img=map放到req
				request.setAttribute("img",img);
				//转发
				request.getRequestDispatcher("/jsps/desc.jsp").forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		/**
		 * get��post�ύ��ʽ
		 * 
		 * @param request
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 */
		public void doGetAndPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("GBK");
			// �ļ���ŵ�Ŀ�?
			//C:\\Documents and Settings\\jnzbht\\Workspaces\\MyEclipse 8.5\\upload\\WebRoot\\upload\\
			File tempDirPath = new File(request.getSession().getServletContext()
					.getRealPath("/")
					+ "\\upload\\");
			if (!tempDirPath.exists()) {
				tempDirPath.mkdirs();
			}

			// ���������ļ�����
			DiskFileItemFactory fac = new DiskFileItemFactory();
			
			// ����servlet�ļ��ϴ����?
			ServletFileUpload upload = new ServletFileUpload(fac);
			
			//ʹ��utf-8�ı����ʽ�������
			upload.setHeaderEncoding("utf-8"); 
			
			// �ļ��б�
			List fileList = null;
			
			// ����request�Ӷ�õ�ǰ̨���������ļ�?
			try {
				fileList = upload.parseRequest(request);
			} catch (FileUploadException ex) {
				ex.printStackTrace();
				return;
			}
			// �������ļ���
			String imageName = null;
			// �����ǰ̨�õ����ļ��б�?
			Iterator<FileItem> it = fileList.iterator();
			
			
			while (it.hasNext()) {
				
				FileItem item = it.next();
				// �������ͨ�?�򣬵����ļ���������
				if (!item.isFormField()) {
					//�����λ�����
					Random r = new Random();
					int rannum = (int) (r.nextDouble() * (9999 - 1000 + 1)) + 1000; 
					
					imageName = DateUtil.getNowStrDate() +  rannum
							+ item.getName();

					BufferedInputStream in = new BufferedInputStream(item
							.getInputStream());
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(new File(tempDirPath + "\\"
									+ imageName)));
					Streams.copy(in, out, true);

				}
			}
			//   
			PrintWriter out = null;
			try {
				out = encodehead(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ����ط������٣�����ǰ̨�ò����ϴ��Ľ��
			out.write("1");
			out.close();
		}

		/**
		 * Ajax���� ��ȡ PrintWriter
		 * 
		 * @return
		 * @throws IOException
		 * @throws IOException
		 *             request.setCharacterEncoding("utf-8");
		 *             response.setContentType("text/html; charset=utf-8");
		 */
		private PrintWriter encodehead(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			return response.getWriter();
		}
		


		public void UpServlet5432543(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			String txt = req.getParameter("txt");//返回的是null
			System.err.println("txt is :"+txt);
			System.err.println("=========================================");
			InputStream in = req.getInputStream();
//			byte[] b= new byte[1024];
//			int len = 0;
//			while((len=in.read(b))!=-1){
//				String s = new String(b,0,len);
//				System.err.print(s);
//			}
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String firstLine = br.readLine();//读取第一行，且第�?行是分隔符号
			String fileName = br.readLine();
			fileName = fileName.substring(fileName.lastIndexOf("\\")+1);// bafasd.txt"
			fileName = fileName.substring(0,fileName.length()-1);
			
			br.readLine();
			br.readLine();
			String data = null;
			//获取当前项目的运行路�?
			String projectPath = getServletContext().getRealPath("/up");
			PrintWriter out  = new PrintWriter(projectPath+"/"+fileName);
			while((data=br.readLine())!=null){
				if(data.equals(firstLine+"--")){
					break;
				}
				out.println(data);
			}
			out.close();
		}
		
		
		
}

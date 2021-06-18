package com.ketayao.learn.itext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class OperatorRTF {
 
	/**
	 * 字符串转换为rtf编码
	 * @param content
	 * @return
	 */
	public String strToRtf(String content){
		char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = content.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
        	sb.append("\\'");
            sb.append(digital[bit]);
            bit = bs[i] & 0x0f;
            sb.append(digital[bit]);
        }
        return sb.toString();
	}
	
	/**
	 * 替换文档的可变部分
	 * @param content
	 * @param replacecontent
	 * @param flag
	 * @return
	 */
	public String replaceRTF(String content,String replacecontent,int flag){
		String rc = strToRtf(replacecontent);
		String target = "";
		/*if(flag==0){
			target = content.replace("$time$",rc);
		}*/
		if(flag==0){
			target = content.replace("$timetop$",rc);
		}
		if(flag==1){
			target = content.replace("$info$",rc);
		}
		if(flag==2){
			target = content.replace("$idea$",rc);
		}
		if(flag==3){
			target = content.replace("$advice$",rc);
		}
		if(flag==4){
			target = content.replace("$infosend$",rc);
		}
		return target;
	}
	
	/**
	 * 获取文件路径
	 * @param flag
	 * @return
	 */
	public String getSavePath() {
		
		String path = "C:\\YQ";
		
		File fDirecotry = new File(path);
		if (!fDirecotry.exists()) {
			fDirecotry.mkdirs();
		}
		return path;
	}
	
	/**
	 * 半角转为全角
	 */
	public String ToSBC(String input){
	    char[] c = input.toCharArray();
	    for (int i = 0; i < c.length; i++){
	        if (c[i] == 32){
	            c[i] = (char) 12288;
	            continue;
	        }
	        if (c[i] < 127){
	        	c[i] = (char) (c[i] + 65248);
	        }
	    }
	    return new String(c);
	}
	
	public void rgModel(String username, String content) {
		// TODO Auto-generated method stub
		/*  构建生成文件名 targetname:12时10分23秒_username_记录.rtf */
		Date current=new Date();
        SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String targetname = sdf.format(current).substring(11,13) + "时";
		targetname += sdf.format(current).substring(14,16) + "分";
		targetname += sdf.format(current).substring(17,19) + "秒";
		targetname += "_" + username +"_记录.rtf";
		
		/* 字节形式读取模板文件内容,将结果转为字符串 */
		String strpath = getSavePath();
		String sourname = strpath+"\\"+"模板.rtf";
		String sourcecontent = "";
		InputStream ins = null;
		try{
			 ins = new FileInputStream(sourname);
			 byte[] b = new byte[1024];
	         if (ins == null) {
	              System.out.println("源模板文件不存在");
	         }
	         int bytesRead = 0;
	         while (true) {
	             bytesRead = ins.read(b, 0, 1024); // return final read bytes counts
	             if(bytesRead == -1) {// end of InputStream
	            	 System.out.println("读取模板文件结束");
	            	 break;
	             }
	             sourcecontent += new String(b, 0, bytesRead); // convert to string using bytes
	          }
		}catch(Exception e){
			e.printStackTrace();
		}
		/* 修改变化部分 */
		String targetcontent = "";
		/**
		 * 拆分之后的数组元素与模板中的标识符对应关系
		 * array[0]:timetop    
		 * array[1]:info		
		 * array[2]:idea		
		 * array[3]:advice		
		 * array[4]:infosend	
		 */
		String array[] = content.split("~");
		/**
		 * 2008年11月27日：更新模板之后时间无需自动填充
		 */
		/*String nowtime = sdf.format(current).substring(0,4) + "年";
		nowtime += sdf.format(current).substring(5,7) + "月";
		nowtime += sdf.format(current).substring(8,10) + "日";*/
		for(int i=0;i<array.length;i++){
			/*if(i==0){
				targetcontent = documentDoc.replaceRTF(sourcecontent,nowtime,i);
			}else{
				targetcontent = documentDoc.replaceRTF(targetcontent,array[i-1],i);
			}*/
			if(i==0){
				targetcontent = replaceRTF(sourcecontent, array[i], i);
			}else{
				targetcontent = replaceRTF(targetcontent, array[i], i);
			}
			
		}	
		/* 结果输出保存到文件 */
		try {
			FileWriter fw = new FileWriter(getSavePath()+"\\" + targetname,true);
            PrintWriter out = new PrintWriter(fw);
            if(targetcontent.equals("")||targetcontent==""){
            	out.println(sourcecontent);
            }else{
            	out.println(targetcontent);
            }
            out.close();
            fw.close();
            System.out.println(getSavePath()+"  该目录下生成文件" + targetname + " 成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OperatorRTF oRTF = new OperatorRTF();
		/**
		 * 被替换内容以"~"符号分割,处理的时候将其拆分为数组即可
		 */
		String content = "2008年10月12日9时-2008年10月12日6时~我们参照检验药品的方法~我们参照检验药品的方法~我们参照检验药品的方法~我们参照检验药品的方法";
		oRTF.rgModel("cheney",content);
 
	}
 
}
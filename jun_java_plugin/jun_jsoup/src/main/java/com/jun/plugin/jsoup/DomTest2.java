package com.jun.plugin.jsoup;

import java.io.File;
import java.io.IOException;
 
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
 
import com.alibaba.fastjson.JSONObject;
 
public class DomTest2 {
    public static void main(String[] args) {
    	String username  = "apidigital";
		String pwd = "api123456";
		String password = DigestUtils.md5Hex(pwd.getBytes());
    	
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("srcLanguage", "auto");
		jsonParam.put("tgtLanguage", "zh");
    	
		
        String htmlStr = readtoStr("D:\\Janes_aeroengine\\html\\00a4326fa3b6aef536a3692f4bfcc6ec.html", "utf-8");
        Document doc = Jsoup.parse(htmlStr);
 
        NodeTraversor nodeTraversor = new NodeTraversor(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    String text = textNode.text();
                    if (!" ".equals(text) && !" ".equals(text)) {
//                    	System.out.println("****"+ text +"****");
 
                    }
                }
 
            }
 
            @Override
            public void tail(Node node, int depth) {
 
            }
        });
 
        nodeTraversor.traverse(doc);
//         System.out.println(doc);
 
    }
 
    public static String readtoStr(String path, String encode) {
        String str = null;
        try {
            str = FileUtils.readFileToString(new File(path), encode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
 
}

package org.ws.httphelper.common;

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperXmlConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-12-30.
 */
public class TestConfigXmlFileFilter extends TestCase{
    public void testFilterPath()throws Exception{
        String path=WSHttpHelperXmlConfig.class.getResource("/").toURI().getPath();
        System.out.println(path);
        File file = new File(path);
        String mapping="request/**/**";
        String [] matchs = mapping.split("/");

        List<String>pathList = new ArrayList<String>();
        listFlies(file,matchs,0,pathList);

        for(String s:pathList){
            System.out.println(s);
        }
    }

    public void listFlies(File file,String[] matchs,int i,List<String> pathList){
        if(i>matchs.length-1){
            return;
        }
        File [] list = file.listFiles(new ConfigXmlFileFilter(matchs[i]));

        for(File f:list){
            if(f.isFile()){
                pathList.add(f.getPath());
            }
            else if(f.isDirectory()){
                listFlies(f,matchs,i+1,pathList);
            }
        }
    }
}

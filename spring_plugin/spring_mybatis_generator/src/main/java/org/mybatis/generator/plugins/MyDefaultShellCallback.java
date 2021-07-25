package org.mybatis.generator.plugins;

import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 重写回调中的mergeJavaFile方法，如果mapper已经存在则读取
 * Created by ht896632 on 2017/6/5.
 */
public class MyDefaultShellCallback extends DefaultShellCallback {

    /**
     * Instantiates a new default shell callback.
     *
     * @param overwrite the overwrite
     */
    public MyDefaultShellCallback(boolean overwrite) {
        super(overwrite);
    }

    @Override
    public boolean isMergeSupported() {
        return true;
    }

    @Override
    public String mergeJavaFile(String newFileSource, String existingFileFullPath, String[] javadocTags, String fileEncoding) throws ShellException {
        String result=newFileSource;
        System.out.println(result);
        if(isClassInterface(existingFileFullPath)){
            result=readFile(existingFileFullPath);
        }
        return result;
    }

    public static String readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder builder=new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString ="";
            while ((tempString = reader.readLine()) != null) {
                builder.append(tempString+"\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return builder.toString();
    }

    private boolean isClassInterface(String fileName){
        File file = new File(fileName);
        BufferedReader reader = null;
        boolean result =false;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString ="";
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("interface")|| tempString.contains("Impl")){
                    result=true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }
}

package Balking.A2_1;

import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;

public class Data {
    private String filename;    //修改是的名字
    private String content;     // 资料的内容
    private boolean changed;    //修改后的内容还没存储的话，值为true

    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    // 修改资料内容
    public synchronized void change(String newContent) {        
        content = newContent;                                   
        changed = true;                                           
    }                                                           

    // 若有资料修改，就存储到挡安里
    public synchronized void save() throws IOException {      
        if (!changed) {                                           
            System.out.println(Thread.currentThread().getName() + " balks");
            return;                                             
        }                                                       
        doSave();                                             
        changed = false;                                          
    }                                                           

    // 实际资料储存到挡案里用的方法
    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls doSave, content = " + content);
        Writer writer = new FileWriter(filename);
        writer.write(content);
        writer.close();
    }
}

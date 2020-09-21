package com.yisin.dbc.main;

import com.yisin.dbc.util.Utililies;


public class CreateResult {
    String dir = "template/";
    
    public String getTlpPath(String name){
        String fix = "";
        return dir + fix + name;
    }
    
    public void start(){
        String content = Utililies.readResourceFile(getTlpPath("ibase_dao.tlp"));
        
        
        
    }
    
    
}

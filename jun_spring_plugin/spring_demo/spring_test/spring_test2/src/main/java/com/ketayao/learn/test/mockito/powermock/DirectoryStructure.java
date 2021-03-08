package com.ketayao.learn.test.mockito.powermock;

import java.io.File;

public class DirectoryStructure { 
    public boolean create(String directoryPath) { 
        File directory = new File(directoryPath); 

        if (directory.exists()) { 
            throw new IllegalArgumentException(
            "\"" + directoryPath + "\" already exists."); 
        } 

        return directory.mkdirs(); 
    } 
 } 

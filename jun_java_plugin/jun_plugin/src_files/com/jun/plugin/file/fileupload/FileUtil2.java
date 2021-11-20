package com.jun.plugin.file.fileupload;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason Krebs
 * @date 2015骞?2鏈?7鏃?
 */
public class FileUtil2 {

    private static final String CHARSET = "UTF-8";
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

    /**
     * 鍙互浠嶫ar鍖呭唴閮ㄥ姞杞?
     *
     * @param input
     * @return
     */
    public static String loadFrom(InputStream input) {
        BufferedReader reader = null;
        StringBuilder data = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(input, CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            }
            catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
        return data.toString();
    }

    /**
     * 浠庡閮ㄨ矾寰勫姞杞?
     *
     * @param path
     * @return
     */
    public static String loadFrom(String path) {
        try {
            return loadFrom(new FileInputStream(path));
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(loadFrom("D:/BackUp/Desktop/nodes.json"));
    }

    public static void saveTo(String path, String message) {
        OutputStreamWriter writer = null;
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(path);
            writer = new OutputStreamWriter(output, CHARSET);
            writer.write(message);
            writer.flush();
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                    writer = null;
                }
                if (output != null) {
                    output.close();
                    output = null;
                }
            }
            catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
    }
    
    
 
}

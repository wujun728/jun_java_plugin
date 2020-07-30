package com.gitee.docx2pdf;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Html2Doc {
    
    private static Logger logger = LoggerFactory.getLogger(Html2Doc.class);
    
    public static void main(String[] args)  
    {  
          
        generate(new File("D:/1.html"), new File("d:/1.doc"));  
    }  
      
    /**  
     * 生成文件 
     * @param inputFile html文件路径 
     * @param outputFile doc文件路径 
     */  
    public static void generate(File inputFile, File outputFile)  
    {  
        InputStream templateStream = null;  
        try  
        {  
            // Get the template input stream from the application resources.  
            final URL resource = inputFile.toURI().toURL();  
              
            // Instanciate the Docx4j objects.  
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();  
            XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);  
              
            // Load the XHTML document.  
            wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(resource));  
              
            // Save it as a DOCX document on disc.  
            wordMLPackage.save(outputFile);  
            // Desktop.getDesktop().open(outputFile);  
              
        }  
        catch (Exception e)  
        {  
            throw new RuntimeException("Error converting file " + inputFile, e);  
              
        }  
        finally  
        {  
            if (templateStream != null)  
            {  
                try  
                {  
                    templateStream.close();  
                }  
                catch (Exception ex)  
                {  
                    logger.error("Can not close the input stream.", ex);  
                      
                }  
            }  
        }  
    }
}

package cn.org.rapid_framework.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.xml.sax.InputSource;

import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;
import cn.org.rapid_framework.generator.util.FileHelper;
import cn.org.rapid_framework.generator.util.GLogger;
import cn.org.rapid_framework.generator.util.IOHelper;
import cn.org.rapid_framework.generator.util.SqlExecutorHelper;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.SystemHelper;
import cn.org.rapid_framework.generator.util.XMLHelper;
import freemarker.ext.dom.NodeModel;

/**
 * 生成器模板控制器,用于模板中可以控制生成器执行相关控制操作
 * 如: 是否覆盖目标文件
 * 
 * <pre>
 * 使用方式:
 * 可以在freemarker或是veloctiy中直接控制模板的生成
 * 
 * ${gg.generateFile('d:/g_temp.log','info_from_generator')}
 * ${gg.setIgnoreOutput(true)}
 * </pre>
 * 
 * ${gg.setIgnoreOutput(true)}将设置为true如果不生成
 * 
 * @author badqiu
 *
 */
public class GeneratorControl {
	private boolean isOverride = Boolean.parseBoolean(System.getProperty("gg.isOverride","false")); 
	private boolean isAppend = false; //no pass
	private boolean ignoreOutput = false; 
	private boolean isMergeIfExists = true; //no pass
	private String mergeLocation; 
	private String outRoot; 
	private String outputEncoding; 
	private String sourceFile; 
	private String sourceDir; 
	private String sourceFileName; 
	private String sourceEncoding; //no pass //? 难道process两次确定sourceEncoding
	private String outputFile;
	
	/** load xml data */
	public NodeModel loadXml(String file) {
		return loadXml(file,true);
	}	
	/** load xml data */
	public NodeModel loadXml(String file,boolean removeXmlNamespace) {
		try {
			if(removeXmlNamespace) {
				InputStream forEncodingInput = FileHelper.getInputStream(file);
				String encoding = XMLHelper.getXMLEncoding(forEncodingInput);
				forEncodingInput.close();
				
				InputStream input = FileHelper.getInputStream(file);
				String xml = IOHelper.toString(encoding,input);
				xml = XMLHelper.removeXmlns(xml);
				input.close();
				return NodeModel.parse(new InputSource(new StringReader(xml.trim())));
			}else {
				return NodeModel.parse(new InputSource(FileHelper.getInputStream(file)));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("loadXml error,file:"+file,e);
		}
	}

	/** load Properties data */
	public Properties loadProperties(String file) {
		try {
			Properties p = new Properties();
			InputStream in = FileHelper.getInputStream(file);
			if(file.endsWith(".xml")) {
				p.loadFromXML(in);
			}else {
				p.load(in);
			}
			in.close();
			return p;
		} catch (Exception e) {
			throw new IllegalArgumentException("loadProperties error,file:"+file,e);
		}
	}

    public void generateFile(String outputFile,String content) {
       generateFile(outputFile,content,false);
    }
	/**
	 * 生成文件   
	 * @param outputFile
	 * @param content
	 * @param append
	 */
	public void generateFile(String outputFile,String content,boolean append) {
		try {
			String realOutputFile = null;
			if(new File(outputFile).isAbsolute()) {
				realOutputFile = outputFile ;
			}else {
				realOutputFile = new File(getOutRoot(),outputFile).getAbsolutePath();
			}
			
			if(deleteGeneratedFile) {
				GLogger.println("[delete gg.generateFile()] file:"+realOutputFile+" by template:"+getSourceFile());
				new File(realOutputFile).delete();
			}else {
				File file = new File(realOutputFile);
				FileHelper.parnetMkdir(file);
				GLogger.println("[gg.generateFile()] outputFile:"+realOutputFile+" append:"+append+" by template:"+getSourceFile());
				IOHelper.saveFile(file, content,getOutputEncoding(),append);
			}
		} catch (Exception e) {
			GLogger.warn("gg.generateFile() occer error,outputFile:"+outputFile+" caused by:"+e,e);
			throw new RuntimeException("gg.generateFile() occer error,outputFile:"+outputFile+" caused by:"+e,e);
		}
	}
	
	public boolean isOverride() {
		return isOverride;
	}
	/**如果目标文件存在,控制是否要覆盖文件 */
	public void setOverride(boolean isOverride) {
		this.isOverride = isOverride;
	}

	public boolean isIgnoreOutput() {
		return ignoreOutput;
	}
	/** 控制是否要生成文件  */
	public void setIgnoreOutput(boolean ignoreOutput) {
		this.ignoreOutput = ignoreOutput;
	}

	public boolean isMergeIfExists() {
		return isMergeIfExists;
	}

	public void setMergeIfExists(boolean isMergeIfExists) {
		this.isMergeIfExists = isMergeIfExists;
	}

	public String getMergeLocation() {
		return mergeLocation;
	}

	public void setMergeLocation(String mergeLocation) {
		this.mergeLocation = mergeLocation;
	}

	public String getOutRoot() {
		return outRoot;
	}
	/** 生成的输出根目录 */
	public void setOutRoot(String outRoot) {
		this.outRoot = outRoot;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}
	/** 设置输出encoding */
	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}
	/** 得到源文件 */
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	/** 得到源文件所在目录 */
	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	/** 得到源文件的文件名称 */
	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	/** 得到源文件的encoding */
	public String getSourceEncoding() {
		return sourceEncoding;
	}

	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}
	
	public String getOutputFile() {
	    if(outputFile != null && new File(outputFile).isAbsolute()) {
	        return outputFile;
	    }else {
	        return new File(getOutRoot(),outputFile).getAbsolutePath();
	    }
    }
	
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    public boolean isExistsOutputFile() {
        return new File(outRoot,outputFile).exists();
    }
    
    public boolean outputFileMatchs(String regex) throws IOException {
        if(isExistsOutputFile()) {
            String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
            if(StringHelper.indexOfByRegex(content, regex) >= 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean outputFileContains(String s) throws IOException {
        if(isExistsOutputFile()) {
            String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
            return content.contains(s);
        }
        return false;
    }
    
    /** 得到property,查到不到则使用defaultValue */
	public String getProperty(String key,String defaultValue){
		return GeneratorProperties.getProperty(key, defaultValue);
	}
	
	public String insertAfter(String compareToken,String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile).getAbsoluteFile(), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
        return StringHelper.insertAfter(content, compareToken, str);
	}
	
	public String insertBefore(String compareToken,String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
        return StringHelper.insertBefore(content, compareToken, str);
	}
	
	public String append(String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
	    return new StringBuffer(content).append(str).toString();
	}
	
	public String prepend(String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
	    return new StringBuffer(content).insert(0,str).toString();
	}

//	public String getRequiredProperty(String key){
//		return GeneratorProperties.getRequiredProperty(key);
//	}

	/** 让用户输入property,windows则弹出输入框，linux则为命令行输入 */
	public String getInputProperty(String key) throws IOException {
		return getInputProperty(key, "Please input value for "+key+":");
	}
	
	public String getInputProperty(String key,String message) throws IOException {
		String v = GeneratorProperties.getProperty(key);
		if(v == null) {
			if(SystemHelper.isWindowsOS) {
				v = JOptionPane.showInputDialog(null,message,"template:"+getSourceFileName(),JOptionPane.OK_OPTION);
			}else {
				System.out.print("template:"+getSourceFileName()+","+message);
				v = new BufferedReader(new InputStreamReader(System.in)).readLine();
			}
			GeneratorProperties.setProperty(key, v);
		}
		return v;
	}
	
	public List<Map> queryForList(String sql,int limit) throws SQLException {
		Connection conn = DataSourceProvider.getConnection();
		return SqlExecutorHelper.queryForList(conn, sql, limit);
	}
	
	boolean deleteGeneratedFile = false;

    
    
    
}

package com.ic911.htools.web.hadoop;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.NodeStatusSynchronizer;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.domain.JobTracker;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.hadoop.domain.SecondaryNameNode;
import com.ic911.core.ssh2.SSH2LongConnectRunner;
import com.ic911.core.ssh2.SSH2Runner;
import com.ic911.core.ssh2.SSH2ShortConnectRunner;
import com.ic911.htools.entity.hadoop.Optimization;

/**
 * 优化向导
 * @author changcheng
 */
@Controller
@RequestMapping("/hadoop/optimization")
public class OptimizationController {

	private static final String CORESITE_PATH = "/conf/core-site.xml";
	private static final String HDFSSITE_PATH = "/conf/hdfs-site.xml";
	private static final String MAPREDSITE_PATH = "/conf/mapred-site.xml";
	
	/**
	 * 优化向导
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}else{
			if(configs.size()>1){
				return "redirect:/hadoop/optimization/list";
			}else{
				return "redirect:/hadoop/optimization/hostname/"+configs.iterator().next().getHostname();
			}
		}
		
	}
	
	/**
	 * hadoop集群列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}
		request.setAttribute("configs", configs);
		return "/hadoop/optimization/list";
	}
	
	/**
	 * 集群性能优化
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/hostname/{hostname}",method=RequestMethod.GET)
	public String init(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			
			return "/hadoop/optimization/index";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/start",method=RequestMethod.POST)
	public String start(String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			
			return "/hadoop/optimization/step1";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/step1",method=RequestMethod.POST)
	public String step1(Optimization optimization,HttpServletRequest request) {
		if(optimization.getHostname()!=null&&HadoopClusterServer.getMasterConfig(optimization.getHostname())!=null){
			
			request.setAttribute("optimization", optimization);
			
			return "/hadoop/optimization/step2";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/step2",method=RequestMethod.POST)
	public String step2(Optimization optimization,HttpServletRequest request) {
		if(optimization.getHostname()!=null&&HadoopClusterServer.getMasterConfig(optimization.getHostname())!=null){
			
			request.setAttribute("optimization", optimization);
			
			return "/hadoop/optimization/step3";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/step3",method=RequestMethod.POST)
	public String step3(Optimization optimization,HttpServletRequest request) {
		if(optimization.getHostname()!=null&&HadoopClusterServer.getMasterConfig(optimization.getHostname())!=null){
			
			request.setAttribute("optimization", optimization);
			
			return "/hadoop/optimization/step4";
		}
		return "redirect:/index";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/step4",method=RequestMethod.POST)
	public String step4(Optimization optimization,HttpServletRequest request) throws Exception  {
		if(optimization.getHostname()!=null&&HadoopClusterServer.getMasterConfig(optimization.getHostname())!=null){
			
			request.setAttribute("optimization", optimization);
			
			HashMap<String, String> mapredMap = new HashMap<String, String>(); //mapred-site中优化项列表
			HashMap<String, String> coreMap = new HashMap<String, String>(); //core-site中优化项列表
			HashMap<String, String> hdfsMap = new HashMap<String, String>(); //hdfs-site中优化项列表
			
			//优化参数
			String item1 = "io.sort.factor"; //排序文件的时候一次同时最多可并流的个数,默认值10
			String item2 = "io.sort.mb"; //排序内存使用限制,默认值100
			String item3 = "mapred.child.java.opts"; //jvms启动的子线程可以使用的最大内存,默认值-Xmx200m
			String item4 = "io.file.buffer.size"; //SequenceFiles在读写中可以使用的缓存大小,默认值4096
			String item5 = "fs.trash.interval"; //这个是开启hdfs文件删除自动转移到垃圾箱的选项，值为垃圾箱文件清除时间,默认值为0
			String item6 = "dfs.block.size"; //这个是hdfs里一个文件块的大小，默认值67108864,就是64M
			String item7 = "dfs.namenode.handler.count"; //hadoop系统里启动的任务线程数,默认值10
			
			mapredMap.put(item1, "100");
			mapredMap.put(item2, "200");
			mapredMap.put(item3, "-Xmx1024m");
			coreMap.put(item4, "131072");
			coreMap.put(item5, "1440");
			hdfsMap.put(item6, "134217728");
			hdfsMap.put(item7, "40");
			
			SSH2Runner runner = new SSH2LongConnectRunner(optimization.getHostname());
			MasterConfig config = HadoopClusterServer.getMasterConfig(optimization.getHostname());
			
			NodeStatusSynchronizer synchronizer = NodeMonitor.getSynchronizer();
			NameNode nn = synchronizer.getNodeStatus(optimization.getHostname());
			SecondaryNameNode snn = nn.getSecondaryNameNode();
			JobTracker jobTracker = nn.getJobTracker();
			
			List<String> pathList = new ArrayList<String>(); //配置文件列表
			String mapredPath = MAPREDSITE_PATH;
			String corePath = CORESITE_PATH;
			String hdfsPath = HDFSSITE_PATH;
			pathList.add(mapredPath);
			pathList.add(corePath);
			pathList.add(hdfsPath);
			
			//循环写入三个配置文件
			for(String path : pathList){
				byte[] data = runner.read(config.getHadoopSetupPath() + path);
				InputStream is = new ByteArrayInputStream(data);
				
				SAXReader saxReader = new SAXReader();
				
		        Document document = saxReader.read(is);
		        Element root = document.getRootElement();
		        
		        //遍历优化参数列表
		        Iterator<?> iter = mapredMap.entrySet().iterator();
		        while (iter.hasNext()) {
		        	Map.Entry e = (Map.Entry) iter.next();
		        	Boolean flag = false;
		        	//遍历根节点下的节点项
	        		for(Iterator it = root.elementIterator();it.hasNext();){
	        			Element element = (Element) it.next();
	        			//遍历各节点的name,value
	        			for(Iterator it2 = element.elementIterator();it2.hasNext();){
	        				Element sube = (Element) it2.next();
	        				//如果参数存在，修改
	    					if(e.getKey().equals(sube.getTextTrim())){
	    						flag = true;
	        					Element subeValue = element.element("value");
	        					subeValue.setText(e.getValue().toString());
	        					break;
	        				}
	    					
	        			}
	        		}
	        		//如果参数不存在,插入新的参数节点
	        		if(flag == false){
	    	        	Element node = root.addElement("property");
	    	        	Element nodeName = node.addElement("name");
	    	        	nodeName.addText(e.getKey().toString());
	    	        	Element nodeValue = node.addElement("value");
	    	        	nodeValue.addText(e.getValue().toString());
	        		}
				}
		        String doc = formatXML(document);
		        //写回配置文件
		        runner.write(doc.getBytes(), config.getHadoopSetupPath() + path);
		        
		        if(!snn.getHostname().equals(nn.getHostname())){
		        	TempNode tempSnnNode = new TempNode();
					tempSnnNode.setConfig(config);
					tempSnnNode.setHostname(snn.getHostname());
					tempSnnNode.setIp(snn.getIp());
					SSH2Runner snnRunner = new SSH2ShortConnectRunner(tempSnnNode);
		        	snnRunner.write(doc.getBytes(), tempSnnNode.getHadoopSetupPath() + path);
		        }
		        if(!jobTracker.getHostname().equals(nn.getHostname())){
		        	TempNode tempJobNode = new TempNode();
					tempJobNode.setConfig(config);
					tempJobNode.setHostname(jobTracker.getHostname());
					tempJobNode.setIp(jobTracker.getIp());
					SSH2Runner jobRunner = new SSH2ShortConnectRunner(tempJobNode);
					MasterConfig jobConfig = HadoopClusterServer.getMasterConfig(tempJobNode.getHostname());
		        	jobRunner.write(doc.getBytes(), jobConfig.getHadoopSetupPath() + path);
		        }
			}
			
			return "/hadoop/optimization/finish";
		}
		return "redirect:/index";
	}
	
	/**
	 * 格式化XML，返回标准换行缩进格式
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static String formatXML(Document doc) throws Exception {  
		StringWriter out=null;  
		try{  
			OutputFormat formate=OutputFormat.createPrettyPrint();  
			out=new StringWriter();  
			XMLWriter writer=new XMLWriter(out,formate);  
			writer.write(doc);  
		} catch (IOException e){  
			e.printStackTrace();  
		} finally{  
			out.close();   
		}  
		return out.toString();  
	}  

	
 }

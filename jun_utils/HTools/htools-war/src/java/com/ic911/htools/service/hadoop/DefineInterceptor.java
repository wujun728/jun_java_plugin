package com.ic911.htools.service.hadoop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.NodeMonitorInterceptor;
import com.ic911.core.hadoop.domain.DataNode;
import com.ic911.core.hadoop.domain.JobTracker;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.hadoop.domain.NodeStatus;
import com.ic911.core.hadoop.domain.SecondaryNameNode;
import com.ic911.core.mail.Email;
import com.ic911.core.mail.Letter;
import com.ic911.htools.entity.hadoop.AlarmMail;
import com.ic911.htools.entity.hadoop.MonitorStatus;
import com.ic911.htools.persistence.hadoop.MonitorStatusDao;
/**
 * 我替你写的注释，自定义拦截器
 * @author murenchao
 */
public class DefineInterceptor implements NodeMonitorInterceptor{
	@Resource
	private AlarmMailService alarmMailService;
	@Resource
	MonitorStatusDao monitorStatusDao;
	static Map<String,MonitorStatus> warningList = new HashMap<String,MonitorStatus>();
	static Map<String,Boolean> sendsw = new HashMap<String,Boolean>();
	@Override
	public void after() {
		System.out.println("节点监控执行结束");
		Set<NameNode> nodes= NodeMonitor.getSynchronizer().getAllNodeStatus();
		MonitorStatus ms = new MonitorStatus();
		Date currentTime = new Date();
		//遍历所有节点信息
		for(NameNode node : nodes){
			ms.setType("nn");
			ms.setIp(node.getIp());
			ms.setHostname(node.getHostname());
			ms.setStatus(node.getStatus());
			ms.setMoitorDate(currentTime);
			SimpleDateFormat from = new SimpleDateFormat("yyyy");
			ms.setYeas(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("MM");
			ms.setMonth(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("dd");
			ms.setDay(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("HH");
			ms.setHours(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("mm");
			ms.setMinute(Integer.parseInt(from.format(currentTime)));
			this.status(ms);
			//nameNode插入数据库
			this.monitorStatusDao.saveAndFlush(ms);
			//secodearyNameNode信息插入数据库
			this.secondaryNameNode(node.getSecondaryNameNode(), currentTime);
			//jobTracker信息插入数据库
			this.jobTracker(node.getJobTracker(),currentTime);
			//dataNode信息插入数据库
			this.dataNode(node.getDataNodes(), currentTime);
			//发送告警邮件
			send();
		}
	}
	/**
	 * 记录dataNode
	 * @param dns
	 * @param currentTime
	 */
	private void dataNode(Collection<DataNode> dns,Date currentTime){
		for(DataNode dn : dns){
			//时间，状态，hostname，ip
			MonitorStatus ms = new MonitorStatus();
			ms.setType("dn");
			ms.setIp(dn.getIp());
			ms.setHostname(dn.getHostname());
			ms.setStatus(dn.getStatus());
			ms.setMoitorDate(currentTime);
			SimpleDateFormat from = new SimpleDateFormat("yyyy");
			ms.setYeas(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("MM");
			ms.setMonth(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("dd");
			ms.setDay(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("HH");
			ms.setHours(Integer.parseInt(from.format(currentTime)));
			from = new SimpleDateFormat("mm");
			ms.setMinute(Integer.parseInt(from.format(currentTime)));
		    this.status(ms);
			//插入数据库
			this.monitorStatusDao.saveAndFlush(ms);
		}
	}
	/**
	 * 记录jobTracker
	 * @param jt
	 * @param currentTime
	 */
	private void jobTracker(JobTracker jt,Date currentTime){
		MonitorStatus ms = new MonitorStatus();
		ms.setType("jt");
		ms.setIp(jt.getIp());
		ms.setHostname(jt.getHostname());
		ms.setStatus(jt.getStatus());
		ms.setMoitorDate(currentTime);
		SimpleDateFormat from = new SimpleDateFormat("yyyy");
		ms.setYeas(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("MM");
		ms.setMonth(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("dd");
		ms.setDay(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("HH");
		ms.setHours(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("mm");
		ms.setMinute(Integer.parseInt(from.format(currentTime)));
		this.status(ms);
		//插入数据库	
		this.monitorStatusDao.saveAndFlush(ms);
	}
	/**
	 * 记录secodaryNameNode
	 * @param snn
	 * @param currentTime
	 */
	private void secondaryNameNode(SecondaryNameNode snn,Date currentTime){
		MonitorStatus ms = new MonitorStatus();
		ms.setType("snn");
		ms.setIp(snn.getIp());
		ms.setHostname(snn.getHostname());
		ms.setStatus(snn.getStatus());
		ms.setMoitorDate(currentTime);
		SimpleDateFormat from = new SimpleDateFormat("yyyy");
		ms.setYeas(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("MM");
		ms.setMonth(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("dd");
		ms.setDay(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("HH");
		ms.setHours(Integer.parseInt(from.format(currentTime)));
		from = new SimpleDateFormat("mm");
		ms.setMinute(Integer.parseInt(from.format(currentTime)));
		this.status(ms);
		//插入数据库
		this.monitorStatusDao.saveAndFlush(ms);
	}
	/**
	 * 记录发送信息的状态
	 * @param ms
	 */
	private void status(MonitorStatus ms){
		String key = ms.getType()+ms.getIp();
		if(ms.getStatus() == NodeStatus.FAULT || ms.getStatus() == NodeStatus.DEAD){
			if(!warningList.containsKey(key)){
				warningList.put(key,ms);
				sendsw.put(key, true);
			}//end if
		}else if(ms.getStatus() == NodeStatus.LIVE || ms.getStatus() == NodeStatus.TEMP){
			if(warningList !=null && warningList.containsKey(key) && (warningList.get(key).getStatus()==NodeStatus.FAULT || warningList.get(key).getStatus()==NodeStatus.DEAD)){
				warningList.put(ms.getType()+ms.getIp(), ms);
				sendsw.put(key, true);
			}//end if
		}//end if
	}
	/**
	 * 组织发送信息的内容
	 * @return
	 */
	private String sendContent(){
		StringBuffer nodeInfo = new StringBuffer();
		List<String> removeKey = new ArrayList<String>();
        if(warningList != null && warningList.size()>0){
        	for(String ip : warningList.keySet()){
        		MonitorStatus node = warningList.get(ip);
        		if(sendsw.get(ip)){
        			sendsw.put(ip, false);
	        		if(node.getType().equals("nn")){
	       				nodeInfo.append("NameNode "+node.getIp()+"节点");
	        			if(node.getStatus() == NodeStatus.DEAD || node.getStatus() == NodeStatus.FAULT){
	        				System.out.println("send namenode error "+ip);
	        				if(node.getStatus() == NodeStatus.DEAD){
	        					nodeInfo.append("死亡");
	        				}else{
	        					nodeInfo.append("故障");
	        				}//end if
	        			}else{
	        				System.out.println("send nameNode success "+ip);
	        				nodeInfo.append("恢复正常");
	        				removeKey.add(ip);
	        			}
	        		}else if(node.getType().equals("jt")){ 
	        			nodeInfo.append("<br/>JobTracker "+node.getIp()+"节点");
	        			if(node.getStatus() == NodeStatus.DEAD || node.getStatus() == NodeStatus.FAULT){
	        				System.out.println("send JobTracker error "+ip);
	        				if(node.getStatus() == NodeStatus.DEAD){
	        					nodeInfo.append("死亡");
	        				}else{
	        					nodeInfo.append("故障");
	        				}//end if
	        			}else{
	        				System.out.println("send jobTracker success "+ip);
	        				nodeInfo.append("恢复正常");
	        				removeKey.add(ip);
	        			}
	        		}else if(node.getType().equals("snn")){
	        			nodeInfo.append("<br/>SecondaryNameNode "+node.getIp()+"节点");
	        			if(node.getStatus() == NodeStatus.DEAD || node.getStatus() == NodeStatus.FAULT){
	        				System.out.println("send SecondaryNameNode error "+ip);
	        				if(node.getStatus() == NodeStatus.DEAD){
	        					nodeInfo.append("死亡");
	        				}else{
	        					nodeInfo.append("故障");
	        				}//end if
	        			}else{
	        				System.out.println("send SecondaryNameNode success "+ip);
	        				nodeInfo.append("恢复正常");
	        				removeKey.add(ip);
	        			}
	        		}else if(node.getType().equals("dn")){
	        			nodeInfo.append("<br/>DataNode "+node.getIp()+"节点");
	        			if(node.getStatus() == NodeStatus.DEAD || node.getStatus() == NodeStatus.FAULT){
	        				System.out.println("send DataNode error "+ip);
	        				if(node.getStatus() == NodeStatus.DEAD){
	        					nodeInfo.append("死亡");
	        				}else{
	        					nodeInfo.append("故障");
	        				}//end if
	        			}else{
	        				System.out.println("send DataNode success "+ip);
	        				nodeInfo.append("恢复正常");
	        				removeKey.add(ip);
	        			}
	        		}//end if
        		}//end if
        	}
        	for(String key : removeKey){
        		warningList.remove(key);
        		sendsw.remove(key);
        	}//end for
        	
        }//end if
        return nodeInfo.toString();
	}
	/**
	 * 信息发送
	 */
	private void send(){
		String content = sendContent();
		if(!content.equals("")){
			List<AlarmMail> alarmMails = (List<AlarmMail>)alarmMailService.findAll();
			if(alarmMails.size() > 0){
				String[] mails = new String[alarmMails.size()];
				for(int i=0;i<alarmMails.size();i++){
					mails[i] = alarmMails.get(i).getMail();
				}//end for
				try{
					Email.send(new Letter("Hadoop节点告警","info@911ic.com",mails,content,null,null,"info@911ic.com","031235","mail.911ic.com"));
				}catch(Exception e){
					e.printStackTrace();
				}
		    }//end if
		}//end if
	}
	
	@Override
	public void before() {
	//	System.out.println("节点监控马上开始");
		
	}

}

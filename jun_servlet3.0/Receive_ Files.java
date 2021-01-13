import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.minstone.docexch.DocExchFactory;
import com.minstone.docexch.DocExchangeApi;
import com.minstone.docexch.model.EDocument;
import com.minstone.docexch.model.EFile;
import com.minstone.docexch.model.ESystemSignReply;

import lotus.domino.AgentBase;
import lotus.domino.AgentContext;
import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.EmbeddedObject;
import lotus.domino.NotesException;
import lotus.domino.RichTextItem;
import lotus.domino.Session;
import lotus.domino.View;

public class JavaAgent extends AgentBase {
	/*
	 * @ CREATE BY CCTC-QZM 20110801
	 */
	Session session = null;
	AgentContext agentContext = null;
	Database db = null;
	Database sysDB = null;
	View sysV = null;
	String workBegin = "";
	Vector vUname = null;
	Vector vPhone = null;
	Vector vEmail = null;

	public void NotesMain() {
		//System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddd"); 
		try {
			session = getSession();
			agentContext = session.getAgentContext();
			db = agentContext.getCurrentDatabase();
			getSendInfo();   
			String dbPath = db.getFilePath();   //public/fsyx.nsf
			String dbName = db.getFileName();   //fsyx.nsf
			String path = dbPath.substring(0, dbPath.length() - dbName.length());
			sysDB = session.getDatabase(db.getServer(), path + "xtpzst.nsf");   //xtpzst.nsf是中心交换的系统配置
			sysV = sysDB.getView("vXTAnDanWeiDaiMaChaXun");
			System.out.println("=====>> 开始运行,接收省厅文件接口程序");
			
			getGatDocs(db, sysDB, sysV);

			System.out.println("=====>> 程序结束");
		} catch (Exception e) {
			e.printStackTrace();
			//错误信息发送 
			try{
				if(checkInterval()){
					//sendMail(e.getMessage());
					//createSendSmsForm(e.getMessage());
			        System.out.println("=====>> 程序出错 1");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		} finally {
			try {
				if (sysV != null) {
					sysV.recycle();
				}
				if (db != null) {
					db.recycle();
				}
				if (sysDB != null) {
					sysDB.recycle();
				}
				if (agentContext != null) {
					agentContext.recycle();
				}
				if (session != null) {
					session.recycle();
				}
				System.gc();
				
			} catch (NotesException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
	}


	// 接收省厅文件接口程序
	public void getGatDocs(Database db, Database sysDB, View sysV) {

		try {
			System.out.println("====>> 开始接收省厅接口程序");
			
			DocExchangeApi docExchApi = DocExchFactory.getInstance();
			// 读取需要接收的公文
			System.out.println("====>> docExchApi");
			EDocument[] docs = docExchApi.getNeedReceiveDocument();
			String fName = "";
			ArrayList al = new ArrayList();
			String stral = "";
			System.out.println("====>> 发现未收文档数为：" + docs.length);
			if (docs != null && docs.length > 0) {
				for (int i = 0; i < docs.length; i++) {
					try {
						System.out.println("====>> 接收并转换第" + i + "份交换文件。");

						boolean rcvOK = false;
						// 公文的全局唯一标识符，是一个128位的GUID。如8d4115a8-b81d-11e0-9c91-f6b0adb9d839  
						String docId = docs[i].getDocumentId();
						// String docMesId = docs[i].getMessageId();
						// 公文类型ID，该值由省厅统一定义，具体值和含义由省厅公文交换系统定义。doc.getMessageId()
						// String docTypeId = docs[i].getDocumentTypeId();
						// 获取公文要素相关的字段值，不同的类型的公文公文要素不同。
						String exchId = String.valueOf(docs[i].getExchangeId());
				
						String GWBT = docs[i].getFieldString("GWBT");// 公文标题
						String JJCD = docs[i].getFieldString("JJCD");// 紧急程度
						String FWJGDM = docs[i].getFieldString("FWJGDM");// 发文机关代码
						String FWJGMC = docs[i].getFieldString("FWJGMC");// 发文机关名称
						String FWZH = docs[i].getFieldString("FWZH");// 发文字号
						if(GWBT.contains("贸易有限公司涉嫌商业贿赂问题的通知")) GWBT = "关于查处广州康升贸易有限公司涉嫌商业贿赂问题的通知";
						
						String QFRXM = docs[i].getFieldString("QFRXM");// 签发人姓名
						String FWSJ = docs[i].getFieldString("FWSJ");// 发文时间
						String ZSDW = docs[i].getFieldString("ZSDW");// 主送					// String CWRQ = docs[i].getFieldString("CWRQ");//成文日期
						String SWDWDM = docs[i].getFieldString("SWDWDM");// 收文单位代码
						String SWDW = docs[i].getFieldString("SWDW");// 收文单位名						
						String FZ = docs[i].getFieldString("FZ");// 附注
						String CBRXM = docs[i].getFieldString("CBRXM");// 承办人姓名
						String GWLX = docs[i].getFieldString("GWLX");// 收文类型
						System.out.println("[文档ID]:"+docId);
						
						System.out.println("[公文标题]："+GWBT);
						System.out.println("[发文字号]:"+FWZH);
//						System.out.println("[紧急程度]："+JJCD);
						System.out.println("[发文机关代码]："+FWJGDM);
						System.out.println("[发文单位]:"+FWJGMC);						
						System.out.println("[签发人姓名]:"+QFRXM);
						System.out.println("[发文时间]:"+FWSJ);
						System.out.println("[主送单位]:"+ZSDW);
						System.out.println("[收文单位代码]:"+SWDWDM);
						System.out.println("[接收单位]:"+SWDW);
//						System.out.println("[公文类型]:"+GWLX);
						System.out.println("[附注]:"+FZ);
						System.out.println("[承办人姓名]:"+CBRXM);
						
						String SWDWDMArray[] = SWDWDM.toString().split(",");
						stral = GWBT;
						stral += "#" + FWSJ;
						stral += "#" + getStrTime(new Date());						
						stral += "#0";	
						al.add(stral);
						for (int iii = 0; iii < SWDWDMArray.length; iii++) {
							
							if (SWDWDMArray[iii].length() > 0) {//判断收文单位代码是否为空。
								
								if (SWDWDMArray[iii].substring(0, 4).equals("4401")) {//判断收文单位代码是否是广州市局的单位代码。
									
									System.out.println("收文单位机构代码SWDWDM：" + SWDWDMArray[iii]);// SWDWDMArray[iii]
									
									// 根据收文单位代码表查询收文单位的收文人员
									Document sysdoc = sysV.getDocumentByKey(SWDWDMArray[iii]);// SWDWDMArray[iii]
									if (sysdoc != null) {
										String strTime = getStrTime(new Date());
										Document doc = db.createDocument();// 创建本地收文doc
										// ............................................................获取附件start
										
										EFile[] attachs = docs[i].getAttachs();
										System.out.println("EFile长度：" + attachs.length);
										for(int a1 = 0;a1 < attachs.length;a1++)
											System.out.println(attachs[a1].getFileName() + "$$$filesize=" + attachs[a1].getFileSize());
					
										String fuJianID = "";
										
										String path = "/oadata/gzga1/domino/html/UODocTrail/" + doc.getUniversalID();
									 	
										Document fjDoc = null;
										RichTextItem body = null;
										if (attachs.length > 0) {
											// 创建附件文档
											// System.out
											// .println("GUID为" + docId
											// + "的公文收到附件 "
											// + attachs.length + "份");
											fjDoc = db.createDocument();
											// System.out
											// .println("fjDoc = db.createDocument();");
											fuJianID = fjDoc.getUniversalID();
											// System.out
											// .println("fuJianID = fjDoc.getUniversalID();;");
											fjDoc.replaceItemValue("Form","fHJGaoZhi1");
											fjDoc.replaceItemValue("tKZBenWenDangHao",fuJianID);
											body = fjDoc.createRichTextItem("Body");
											// System.out
											// .println("body = fjDoc.createRichTextItem(");
											
											for (int ii = 0; ii < attachs.length; ii++) {
												EFile ef = attachs[ii];
												String prefix = "";//updated @20160701
												byte[] attachContent = ef.getFileContent();
												// System.out.println("附件ef.getFileName()= "
												// + ef.getFileName());
												// System.out.println("附件ef.getFileSize()= "
												// + ef.getFileSize());
												
												fName = ef.getFileName();	
												System.out.println("fName:"+fName);
												System.out.println("fName Length:"+fName.length());
												if(fName.contains("贸易有限公司梅俊君向盒谢?犯罪蔰oc")) fName = "匿名举报广州康升贸易有限公司梅俊君向医院行贿犯罪事.doc";
//												if(fName.contains("雷霆14")) fName = "雷霆14澳门警方通缉人士名单.xls";
//												if(fName.length() > 40) fName = fName.substring(0,40) + "....doc";
												//File f = new File(path + "/" + ef.getFileName());
												
												//updated @20160701--start
												fName = fName.replaceAll("\\?", "\\^");
												if(fName.length()>60){
													prefix = fName.substring(fName.lastIndexOf("."));
													fName = fName.substring(0, 60-prefix.length()-2)+".."+prefix;
												}
												//updated @20160701--end
												
												
												File f = new File(path + "/" + fName);
												
												File fPath = new File(path);
												if (fPath.exists()) {
													// System.err.println("已存在目录 "
													// +
													// path);
												} else {
													fPath.mkdir();
													// System.err.println("创建文件目录 "
													// +
													// path);
												}
												OutputStream fopts = new FileOutputStream(f);
												fopts.write(attachContent);
												fopts.close();
												//body.embedObject(EmbeddedObject.EMBED_ATTACHMENT,null,path + "/" + ef.getFileName(),"");
												body.embedObject(EmbeddedObject.EMBED_ATTACHMENT,null,path + "/" + fName,"");
											}
											// 保存附件文档
											fjDoc.save(true);
										} else {
											System.out.println("GUID为" + docId	+ "的公文没有附件！");
										}
										fName = "";
										// ............................................................获取附件end
										// ............................................................获取正文start
										//System.out.println("获取正文 start.");
										String zwID = "";
										EFile textFile = docs[i].getText();
										if (textFile != null) {
											// 创建正文文档
											// System.out.println("GUID为" +
											// docId
											// + "的公文收到正文一份");
											if (fjDoc == null) {
												fjDoc = db.createDocument();
												fuJianID = fjDoc.getUniversalID();
												fjDoc.replaceItemValue("Form","fHJGaoZhi1");
												fjDoc.replaceItemValue("tKZBenWenDangHao",fuJianID);
												body = fjDoc.createRichTextItem("Body");
											}
											byte[] attachContent = textFile	.getFileContent();
											 System.out.println("正文textFile.getFileName()= "
											 + textFile.getFileName());
											 System.out.println("正文textFile.getFileSize()= "
											 + textFile.getFileSize());
											fName = textFile.getFileName();
											if(fName.length() > 40) fName = fName.substring(0,40) + "....doc";
											File f = new File(path + "/" + fName);
											//File f = new File(path + "/" + textFile.getFileName());
											File fPath = new File(path);
											if (fPath.exists()) {
												// System.err.println("已存在目录 " +
												// path);
											} else {
												fPath.mkdir();
												// System.err.println("创建文件目录 "
												// +
												// path);
											}
											OutputStream fopts = new FileOutputStream(f);
											fopts.write(attachContent);
											fopts.close();
											//body.embedObject(EmbeddedObject.EMBED_ATTACHMENT,null,path	+ "/" + textFile.getFileName(),"");
											body.embedObject(EmbeddedObject.EMBED_ATTACHMENT,null,path	+ "/" + fName,"");
											// 保存正文文档
											fjDoc.save(true);
										} else {
											System.out.println("GUID为" + docId	+ "的公文没有正文");
										}
										// ............................................................获取正文 end
										doc.replaceItemValue("Form","fFaSongXinXiWS");
										doc.replaceItemValue("tJHWenJianLiuShuiHao", docId);
										doc.replaceItemValue("GUID", docId);
										doc.replaceItemValue("tQianShou", "0");
										doc.replaceItemValue("tJHWenJianBiaoTi", GWBT);
										doc.replaceItemValue("tJHWenDangBianHao", FWZH);
										doc.replaceItemValue("tJHJinJiChengDu",getHjcd(JJCD));
										doc.replaceItemValue("FWJGDM", FWJGDM);
										doc.replaceItemValue("tJHFaSongRenDanWei", FWJGMC);
										doc.replaceItemValue("tQianFaRen",QFRXM);
										doc.replaceItemValue("tJHFaSongRiQi",strTime);
										doc.replaceItemValue("SWJGDM",SWDWDMArray[iii]);
										doc.replaceItemValue("tJHFaWenFanWei",ZSDW);
										//doc.replaceItemValue("tJHWenJianLeiXing1_1", GWLX);
										doc.replaceItemValue("tJHWenJianLeiXing1_1", getGwlx(GWLX));  //20140805添加getGwlx()函数
										doc.replaceItemValue("tBeiZhu", FZ);
										doc.replaceItemValue("tLianXiRen", CBRXM);
//										doc.replaceItemValue("CBRXM", CBRXM);
										//doc.replaceItemValue("tJHFaSongRen",QFRXM);
										// doc.replaceItemValue("tJHMiJi",FWSJ);
										// doc.replaceItemValue("tJHFaSongRen",FWSJ);
										// doc.replaceItemValue("tJSLianXiRen",FWSJ);
										doc.replaceItemValue("tKZFuJianBianHao", fuJianID);// 附件id
										doc.replaceItemValue("tFWDingGaoWenDangUNID", zwID);// 正文id
										
										// SWDWDMArray[iii]
										// SWDWDMSWJGDM

										String tXTQunZuMing = sysdoc.getItemValueString("tXTQunZuMing");
										System.out.println("收文单位名称："	+ tXTQunZuMing);
										Vector v = sysdoc.getItemValue("mXTChengYuan");
										for (int ii = 0; ii < v.size(); ii++) {
											System.out.println((ii + 1)	+ "收文人员: " + v.elementAt(ii));
										}
										doc.replaceItemValue("tJHFaWangDanWei", tXTQunZuMing);
										doc.replaceItemValue("aJSauthors", v);
										doc.replaceItemValue("rJSreaders", v);
										doc.replaceItemValue("Authores_1", db.getManagers());
										doc.replaceItemValue("Readers_1", db.getManagers());
										doc.replaceItemValue("dbManager", db.getManagers());
										doc.save(true);
										

										System.out.println("GUID为" + docId	+ "的公文已经保存至中心公文交换。");
										// System.out.println(mXTChengYuan +
										// "可签收办理。");
									} else {
										System.out.println("系统未找到单位代码为" + SWDWDMArray[iii]	+ "的单位，请与系统管理员联系。");
									}
								} else {
									System.out.println("收文单位代码（SWDWDMArray[iii]" + SWDWDMArray[iii]
										+ "）不是广州市局直属单位的单位代码，系统不予以接收。");/**/
								}
							} else {
								System.out.println("收文单位代码（SWDWDMArray[iii]）为空，系统不予以接收。");
							}
							
							
							//系统签收20140721
							
							ESystemSignReply systemSignReply =null;
							systemSignReply = new ESystemSignReply();
							systemSignReply.setMessageId(docId);
							systemSignReply.setReferenceMessageId(docId);
							systemSignReply.setMessageReceiveDate(new Timestamp(System
									.currentTimeMillis()));
							systemSignReply.setMessageReceiver(docExchApi.getLocalSystemId());
							systemSignReply.setReceiverIp(Inet4Address.getLocalHost().getHostAddress());
							
							systemSignReply.setFrom(docExchApi.getLocalSystemId());
							//systemSignReply.setTo("440000000000001");
							systemSignReply.setTo("440000000000001");
							
							
							boolean bSendSysOK=false;
							bSendSysOK = docExchApi.sendDocumentSignReply(systemSignReply);
							if(bSendSysOK){
								System.out.println("收文系统签收成功！");
							}else{
								System.out.println("收文系统签收失败！");
							}
						
						//系统签收20140721
							
							
						} // for end
						rcvOK = true;// false;	
						

						if (rcvOK) {
							// System.out.println("if (rcvOK) {");
							docExchApi.setReceiveDocumentResult(exchId,DocExchangeApi.RECEIVE_DOCUMENT_OK);
						} else {
							docExchApi.setReceiveDocumentResult(exchId,DocExchangeApi.RECEIVE_DOCUMENT_ERROR);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						//错误信息发送
						try{
							if(checkInterval()){
								sendMail(ex.getMessage());
								createSendSmsForm(ex.getMessage());
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				if(al.size() > 0){
					System.out.println("开始记录今天收文情况");
					checkTodaySw(al);
					System.out.println("记录今天收文情况结束");
				}
				System.out.println("共接收的外市交换文件" + docs.length + "份");
				// System.out.println("接收的外市交换文件的收文单位有" + SWDW );
			} else {
				System.out.println("没有需要接收的外市交换文件！");
			}
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			//错误信息发送
			try{
				if(checkInterval()){
					sendMail(e.getMessage());
					createSendSmsForm(e.getMessage());
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	/**
	 * 检查今天收文
	 * @param al
	 * @return
	 * 
	 */
	public boolean checkTodaySw(ArrayList al){
		Document cDoc = null;
		View vDoc = null;
		try{

			String strDate = getStrDate(new Date());
			vDoc = db.getView("vCheckTodaySw");
			cDoc = vDoc.getDocumentByKey(strDate);
			if(cDoc == null){
				cDoc = db.createDocument();
				cDoc.replaceItemValue("form", "fCheckTodaySw");
				cDoc.replaceItemValue("tDate", strDate);				
			}
			Vector vTitle = cDoc.getItemValue("tTitle");
			Vector vSwTime = cDoc.getItemValue("tSwTime");
			Vector vQsStatus = cDoc.getItemValue("tQsStatus");
//			Vector vUnid = cDoc.getItemValue("tUnid");
			Vector vFwTime = cDoc.getItemValue("tFwTime");
			String strtmp = "";
			String[] arrtmp = null;
			String msg = "收到 " + al.size() + " 份文,文件标题";
			for(int i = 0;i < al.size();i++){
				strtmp = (String)al.get(i);
				arrtmp = strtmp.split("#");
				System.out.println("[checkTodaySw]strtmp=" + strtmp + "$$$arrtmpLength=" + arrtmp.length);
				if(i == 0) msg += arrtmp[0];
				else msg += "," + arrtmp[0];
				vTitle.add(arrtmp[0]);
				vFwTime.add(arrtmp[1]);
				vSwTime.add(arrtmp[2]);
				vQsStatus.add(arrtmp[3]);
//				vUnid.add(arrtmp[4]);
			}
			cDoc.replaceItemValue("tTitle", vTitle);
			cDoc.replaceItemValue("tFwTime", vFwTime);
			cDoc.replaceItemValue("tSwTime", vSwTime);
//			cDoc.replaceItemValue("tUnid", vUnid);
			cDoc.replaceItemValue("tQsStatus", vQsStatus);
			cDoc.save();

			Vector senduser = new Vector();
			senduser.add("郑丽霞/newgzga");
			mailSend(senduser,msg);
			
			return true;
		}catch(Exception e){
			System.out.println("checkTodaySw发现错误：");
			e.printStackTrace();
		}finally{
			try{
				if(cDoc != null) cDoc.recycle();
				if(vDoc != null) vDoc.recycle();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	/***
	 * 检查发送短信的间隔是否已经达到1小时以上
	 * @return
	 */
	public boolean checkInterval(){
		Document prodoc = null;
		View view = null;
		try{
			view = db.getView("vfStgwtime");
			prodoc = view.getFirstDocument();
			if(prodoc == null){
				prodoc = db.createDocument();
				prodoc.replaceItemValue("form","fStgwtime");
			}			
			String sm = prodoc.getItemValueString("sendtime");			
			if (sm.equals("")){
				prodoc.replaceItemValue("sendtime", getStrTime(new Date()));
				prodoc.save();
				return true;
			}else{
				System.out.println("上次发送时间：" + sm);
				String curTime = getStrTime(new Date());
				String prevTime = sm;
				String strtmp = "";
				Vector vetmp = session.evaluate("@Adjust(@TextToTime('" + curTime + "') ; 0 ; 0 ; 0 ; -1 ; 0 ; 0)");
				strtmp = vetmp.get(0).toString();
				strtmp = strtmp.substring(0,strtmp.indexOf(" ZE8"));
				System.out.println("vetmp=" + strtmp);
				String curTime1 = strtmp.trim();
				if (compare_date(curTime1,prevTime) == 1){
					prodoc.replaceItemValue("sendtime", getStrTime(new Date()));
					prodoc.save();
					return true;
				}else return false;
			}			
		}catch(Exception e){
			System.out.println("checkInterval发现错误");
			e.printStackTrace();
		}finally{
			try{
				if(prodoc != null) prodoc.recycle();
				if(view != null) view.recycle();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 比较两个日期的大小，date1 大于 date2 的返回1；小于的返回-1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public  int compare_date(String date1,String date2){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		try{
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if((dt1.getTime() - dt2.getTime()) > 0){
				dt1 = null;
				dt2 = null;
				return 1;
			}else {
				dt1 = null;
				dt2 = null;
				return -1;
			}
		}catch(Exception e){
			System.out.println("compare_date发现错误");
			e.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * 当出现错误时，需要把错误信息发送给相关人员
	 */
	
	public void sendMail(String meg){
		Document memodoc = null;
		Document pzdoc = null;
		View view = null;
		Database pzdb = null;
		System.out.println("中心公文交换-邮件发送开始");
		try{
			
			memodoc = db.createDocument();
			memodoc.replaceItemValue("form", "memo");			
			memodoc.replaceItemValue("sendto", this.vEmail);			
			memodoc.replaceItemValue("subject", "中心公文交换的省厅收文代理发生错误，错误信息：" + meg);
			
			//发送邮件
						
			memodoc.send(); 
			System.out.println("中心公文交换-邮件发送结束");
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try{
				if(memodoc != null) memodoc.recycle();
				if(pzdoc != null) pzdoc.recycle();
				if(view != null) view.recycle();
				if(pzdb != null) pzdb.recycle();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 发送邮件给相关人员
	 */
	
	public void mailSend(Vector sendto,String meg){
		Document memodoc = null;
		System.out.println("中心公文交换-发送邮件开始");
		try{
			//
			memodoc = db.createDocument();
			memodoc.replaceItemValue("form", "memo");			
			memodoc.replaceItemValue("sendto", sendto);			
			memodoc.replaceItemValue("subject", meg);
			
			//发送短信
						
			memodoc.send(); 
			System.out.println("中心公文交换-发送邮件结束");
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try{
				if(memodoc != null) memodoc.recycle();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean createSendSmsForm(String meg){
		System.out.println("公文交换-发短信开始");
		Database smsdb = null;
		Document smsdoc = null;
		try{
			Vector vSendTo = new Vector(),vMobile = this.vPhone;
			
			Date date = new Date();
			String strtmp = "";
			for(int i = 0;i < this.vUname.size();i++){
				strtmp = (String)vMobile.get(i); 
				if (!strtmp.equals("Null")){
					vSendTo.add((String)this.vUname.get(i) + "=>" + (String)this.vPhone.get(i));					
				}
			}			
			
			smsdb = session.getDatabase(db.getServer(), "public/oasms.nsf", false);
			System.out.println("db=" + smsdb.getFilePath());
			
			smsdoc = smsdb.createDocument();
			smsdoc.replaceItemValue("Form","FormMessage");
			smsdoc.replaceItemValue("MessageContent","中心公文交换的省厅收文代理发现有报错，报错信息：" + meg);
			smsdoc.replaceItemValue("Accepter",vSendTo);
			smsdoc.replaceItemValue("AccepterMobiles",vMobile);
			smsdoc.replaceItemValue("SendStatus","未发送");
			smsdoc.replaceItemValue("Sender","系统管理员");
			smsdoc.replaceItemValue("MessageType","错误提醒");
			smsdoc.replaceItemValue("SendType","定时发送");
			
			smsdoc.replaceItemValue("PlanSendDate",getYear(date) + "-" + getMonth(date) + "-" + getDay(date));
			smsdoc.replaceItemValue("PlanSendTime",getHMS(date));
			smsdoc.replaceItemValue("UnitName","系统");
			smsdoc.save();
			date = null;
			System.out.println("公文交换-发短信结束");
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try{
				if(smsdoc != null) smsdoc.recycle();
				if(smsdb != null) smsdb.recycle();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	//获取发送的配置信息
	public boolean getSendInfo(){
		Database pzdb = null;
		View view = null;
		Document pzdoc = null;
		try{
			//获取配置信息
			String svr = "newicsvr-gzs/newgzga";
			String path = "public";
			pzdb = session.getDatabase(svr, path + "/xtpzst.nsf");
			if(pzdb == null)	System.out.println("找不到配置数据库");
			view = pzdb.getView("vZxgwbctz");
			if(view == null)	System.out.println("找不到配置视图");
			pzdoc = view.getFirstDocument();
			if(pzdoc == null)	System.out.println("找不到配置文件");
			this.vUname = pzdoc.getItemValue("tUname_0");
			this.vPhone = pzdoc.getItemValue("tPhoneNum_0");
			this.vEmail = pzdoc.getItemValue("tEmailAddress_0");
			return true;
		}catch(Exception e){
			e.printStackTrace();			
		}finally{
			try{
				if(pzdoc != null) pzdoc.recycle();
				if(view != null) view.recycle();
				if(pzdb != null) pzdb.recycle();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String getHjcd(String hjcd)
	// 获取公文紧急程度
	{
		String temp = "";
		if (hjcd.equals("0")) {
			temp = "平件";
		}
		if (hjcd.equals("1")) {
			temp = "急件";
		}
		if (hjcd.equals("2")) {
			temp = "紧急";
		}
		if (hjcd.equals("3")) {
			temp = "特急";
		}
		if (hjcd.equals("4")) {
			temp = "特提";
		}
		return temp;
	}

	public String getGwlx(String gwlx)
	// 获取公文类型
	{
		String temp = "";
		if (gwlx.equals("0")) {
			temp = "党委公文";
		}
		if (gwlx.equals("1")) {
			temp = "行政机关公文";
		}
		if (gwlx.equals("2")) {
			temp = "其他机关公文";
		}
		return temp;
	}
	/**
	 * 获取字符类型的带分秒的日期
	 * @param date
	 * @return
	 */
	public String getStrTime(Date date){
		try{
		String temp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		temp = sdf.format(date);
		// temp="";
		return temp;
		}catch(Exception e){
			System.out.println("getStrTime出现错误");
			e.printStackTrace();
		}
		return "";
	}
	public String getStrDate(Date date){
		try{
			String temp = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			temp = sdf.format(date);
			// temp="";
			return temp;
		}catch(Exception e){
			System.out.println("getStrDate出现错误");
			e.printStackTrace();			
		}
		return "";
	}
	public String getYear(Date date)
	// 获取年份
	{
		String temp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		temp = sdf.format(date);
		// temp="";
		return temp;
	}

	public String getMonth(Date date)
	// 获取月份
	{
		String temp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		temp = sdf.format(date);
		// temp="";
		if (temp.length() == 1) {
			temp = "0" + temp;
		}
		return temp;
	}

	public String getDay(Date date)
	// 获取日
	{
		String temp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		temp = sdf.format(date);
		// temp="";
		if (temp.length() == 1) {
			temp = "0" + temp;
		}
		return temp;
	}
	//获取时分秒
	public String getHMS(Date date){
		String temp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		temp = sdf.format(date);
		
		return temp;
	}
	


	

	/**
	 * 把字节数组保存为一个文件
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}
}
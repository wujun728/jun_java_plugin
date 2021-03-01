package cn.ipanel.apps.payment.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.ipanel.apps.commons.util.FileUtil;
import cn.ipanel.apps.payment.pojo.BankBillRecord;
import cn.ipanel.apps.payment.pojo.BankSignRecord;
import cn.ipanel.apps.payment.pojo.BankSignRecordBean;
import cn.ipanel.apps.payment.pojo.BankbillRecordBean;

public class FileParse {

	public static  BankBillRecord parseBillFile(byte[] b){
		ByteArrayInputStream is=new ByteArrayInputStream(b);
		BankBillRecord billrecord=new BankBillRecord();
		int totalsize=Integer.parseInt(get(is,10));
		String successsize=get(is,10);
		String successmoney=get(is,15);
		String failsize=get(is,10);
		String failmoney=get(is,15);
		getenter(is);
		billrecord.setTotalsize(totalsize+"");
		billrecord.setSuccesssize(successsize);
		billrecord.setSuccessmoney(CommonsFiend.moneyIntTofloat(Integer.parseInt(successmoney)));
		billrecord.setFailmoney(CommonsFiend.moneyIntTofloat(Integer.parseInt(failmoney)));
		billrecord.setFailsize(failsize);
		List list=new ArrayList();
		billrecord.setRecordlist(list);
		for(int i=0;i<totalsize;i++){
			BankbillRecordBean record=new BankbillRecordBean();
			
			record.setBankSerialNumber(get(is, 20));
			record.setSerialNumber(get(is, 20));
			record.setUserNumber(get(is,20));
			record.setMoney(CommonsFiend.moneyIntTofloat(Integer.parseInt(get(is, 15))));
			record.setType(get(is, 20));
			record.setIssuccess(get(is, 1));
			record.setBankAccount(get(is, 20));
			getenter(is);
			list.add(record);
		}
		return billrecord;
	}
	
	public static  BankSignRecord parseUserSignFile(byte[] b){
		ByteArrayInputStream is=new ByteArrayInputStream(b);
		BankSignRecord signrecord=new BankSignRecord();
		int totalsize=Integer.parseInt(get(is,10));
		String successsize=get(is,10);
		String failsize=get(is,10);
		signrecord.setTotalsize(totalsize+"");
		signrecord.setSuccesssize(successsize);
		signrecord.setFailsize(failsize);
		getenter(is);
		List list=new ArrayList();
		signrecord.setRecordlist(list);
		for(int i=0;i<totalsize;i++){
			BankSignRecordBean record=new BankSignRecordBean();
			record.setBankSerialNumber(get(is, 20));
			record.setSerialNumber(get(is, 20));
			record.setUserNumber(get(is, 20));
			record.setUsername(get(is, 30));
			record.setIsSuccess(get(is, 1));
			record.setIdCard(get(is, 20));
			record.setType(get(is, 1));
			list.add(record);
			getenter(is);
		}
		return signrecord;
	}
	
	private static String get(InputStream is,int len){
		if(is!=null){
			byte[] b=new byte[len];
			try {
				is.read(b);
				return new String(b).trim();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	public static void getenter(InputStream is){
		if(is!=null){
			
			try {
				int t=0;
				while((t=is.read())!=-1){
					if(t=='\n'){
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		byte[] b=FileUtil.readFileByByte("c:/dd");
		BankBillRecord bean=parseBillFile(b);
		System.out.println(bean);
	}
}

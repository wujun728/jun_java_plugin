package com.jun.web.biz.beans;

import java.util.List;

//��ҳ��
public class Page {
	private int allRecordNO;//�ܼ�¼��
	private int perPageNO = 10;//ÿҳ��ʾ��¼��
	private int allPageNO;//��ҳ��(�ܼ�¼��/ÿҳ��ʾ��¼��)
	private int currPageNO = 1;//��ʾ�ĵ�ǰҳ��
    public Page(){}
	public int getAllRecordNO() {
		return allRecordNO;
	}
	public void setAllRecordNO(int allRecordNO) {
		this.allRecordNO = allRecordNO;
		if(this.allRecordNO % this.perPageNO == 0){
			this.allPageNO = this.allRecordNO / this.perPageNO;
		}else{
			this.allPageNO = this.allRecordNO / this.perPageNO + 1;
		}
	}
	public int getPerPageNO() {
		return perPageNO;
	}
	public void setPerPageNO(int perPageNO) {
		this.perPageNO = perPageNO;
	}
	public int getAllPageNO() {
		return allPageNO;
	}
	public void setAllPageNO(int allPageNO) {
		this.allPageNO = allPageNO;
	}
	public int getCurrPageNO() {
		return currPageNO;
	}
	public void setCurrPageNO(int currPageNO) {
		this.currPageNO = currPageNO;
	}
 
}

package org.lxh.eldemo.vo ;
public class Dept {
	private int deptno ;
	private String dname ;
	private String loc ;
	public void setDeptno(int deptno){
		this.deptno = deptno ;
	}
	public void setDname(String dname){
		this.dname = dname ;
	}
	public void setLoc(String loc){
		this.loc = loc ;
	}
	public int getDeptno(){
		return this.deptno ;
	}
	public String getDname(){
		return this.dname ;
	}
	public String getLoc(){
		return this.loc ;
	}
}
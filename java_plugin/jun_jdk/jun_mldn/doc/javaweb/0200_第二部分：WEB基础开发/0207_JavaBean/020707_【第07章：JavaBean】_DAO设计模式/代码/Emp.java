package cn.mldn.lxh.vo ;
import java.util.Date ;
public class Emp {
	private int empno ;
	private String ename ;
	private String job ;
	private Date hiredate ;
	private float sal ;
	public void setEmpno(int empno){
		this.empno = empno ;
	}
	public void setEname(String ename){
		this.ename = ename ;
	}
	public void setJob(String job){
		this.job = job ;
	}
	public void setHiredate(Date hiredate){
		this.hiredate = hiredate ;
	}
	public void setSal(float sal){
		this.sal = sal ;
	}
	public int getEmpno(){
		return this.empno ;
	}
	public String getEname(){
		return this.ename ;
	}
	public String getJob(){
		return this.job ;
	}
	public Date getHiredate(){
		return this.hiredate ;
	}
	public float getSal(){
		return this.sal ;
	}
}
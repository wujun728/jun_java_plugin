package com.ky26.winterHomeWork.Exercise6;

public class EleApplianceTest {
	public static void main(String[] args) {
		/*TV t1=new TV("100,","100,","100,","类型1,","液晶,","100");
		t1.work();
		Refrigerator r1=new Refrigerator("200,","300,","300,","类型3,","2000L");
		r1.work();
		r1.show();*/
		EleAppliance e1=new TV();
		e1.work();
		EleAppliance e2=new Refrigerator();
		e2.work();
//		e2.show();//show()是子类独有，不能调用
	}
}
class EleAppliance{
	private String power;
	private String ratedVoltage;
	private String ratedPower;
	private String type;
	EleAppliance(){
		
	}
	public EleAppliance(String power, String ratedVoltage, String ratedPower, String type) {
		this.power = power;
		this.ratedVoltage = ratedVoltage;
		this.ratedPower = ratedPower;
		this.type = type;
	}
	
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getRatedVoltage() {
		return ratedVoltage;
	}
	public void setRatedVoltage(String ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}
	public String getRatedPower() {
		return ratedPower;
	}
	public void setRatedPower(String ratedPower) {
		this.ratedPower = ratedPower;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	void work() {
		
	}
}
class TV extends EleAppliance{
	private String kind;
	private String maxVolume;
	TV(){
		
	}
	public TV(String power, String ratedVoltage, String ratedPower, String type, String kind, String maxVolume) {
		super(power, ratedVoltage, ratedPower, type);
		this.kind=kind;
		this.maxVolume=maxVolume;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getMaxVolume() {
		return maxVolume;
	}
	public void setMaxVolume(String maxVolume) {
		this.maxVolume = maxVolume;
	}
	void work() {
		System.out.println("电视机的工作内容是播放节目");
	}
}
class Refrigerator extends EleAppliance{
	private String capacity;
	Refrigerator(){
		
	}
	public Refrigerator(String power, String ratedVoltage, String ratedPower, String type, String capacity) {
		super(power, ratedVoltage, ratedPower, type);
		this.capacity=capacity;
	}
	
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	void work() {
		System.out.println("冰箱的工作是保存食物");
	}
	void show() {
		System.out.println("冰箱的工作信息:"+getPower()+getRatedVoltage()+getRatedPower()+getType()+getCapacity());
	}
}

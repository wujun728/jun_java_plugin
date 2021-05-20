package org.frameworkset.spi.remote.http.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpAddress {
	private String address;
	private static Logger logger = LoggerFactory.getLogger(HttpAddress.class);

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	private String originAddress;
	private String healthPath;
	private transient Thread healthCheck;
	private String routing;
	private Map<String,Object> attributes;
	/**
	 * 服务器状态：0 正常 1 异常 2 removed
	 */
	private volatile int status= 0;
	public HttpAddress(){

	}

	public String getHealthPath() {
		return healthPath;
	}

	public void setHealthPath(String healthPath) {
		this.healthPath = healthPath;
	}
	private void initAddress(){
		String[] infos = this.originAddress.split("\\|");

		if(infos.length == 2){
			this.address = infos[0];
			this.routing = infos[1];
		}
		else{
			this.address = infos[0];
		}
	}
	public HttpAddress(String address){
		if (!address.startsWith("http://") && !address.startsWith("https://")) {
			address = "http://" + address;
		}
		this.originAddress = address;
		initAddress();
		this.healthPath = this.getPath(this.address,"/");
	}

	public HttpAddress(String address,String healthPath){
		if (!address.startsWith("http://") && !address.startsWith("https://")) {
			address = "http://" + address;
		}
		this.originAddress = address;
		initAddress();
		if(healthPath != null) {
			this.healthPath = this.getPath(this.address, healthPath.trim());
		}
	}

	public HttpAddress(String originAddress,String address,String routing,String healthPath){

		this.originAddress = originAddress;
		this.address = address;
		this.routing = routing;
		if(healthPath != null) {
			this.healthPath = this.getPath(this.address, healthPath.trim());
		}
	}
	private String getPath(String host,String path){
		String url = path.equals("") || path.startsWith("/")?
				new StringBuilder().append(host).append(path).toString()
				:new StringBuilder().append(host).append("/").append(path).toString();
		return url;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		this.healthPath = this.getPath(address,"/");
	}

	public int getStatus() {
		return status;
	}

	/**
	 * 没有配置health检查地址的情况下，被动恢复异常状态节点为正常状态
	 *
	 */
	public void recover(){
		if(status == 1){//failed
			synchronized (this){
				if(status == 1){
					status = 0;
					logger.info("Recover failed node {} to normal node.",originAddress);
				}
			}
		}
	}
	public void setStatus(int status) {
		synchronized (this) {
			if(status == 0){
				this.status = status;
				return;
			}
			else if (this.status == 2) {//删除节点，无需修改状态
				return;
			}
			this.status = status;
		}


		if(status == 1 && healthCheck != null){
			synchronized(healthCheck){
				healthCheck.notifyAll();
			}
		}
	}
	
	public void onlySetStatus(int status) {
		synchronized (this) {
			if(status == 0)
				this.status = status;
			else if (this.status != 2)//如果没有移除，则设置故障状态
				this.status = status;
		}
		
	}
	public String toString(){
		return new StringBuilder().append(this.originAddress).append("|status=").append(status).toString();
	}
	public boolean ok(){
		return this.status == 0;
	}
	public boolean okOrFailed(){
		return this.status == 0 || this.status == 1;
	}

	public boolean failedCheck(){
		return this.status == 1;
	}

	public void setHealthCheck(Thread healthCheck) {
		this.healthCheck = healthCheck;
	}
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj instanceof  HttpAddress){
			String other = ((HttpAddress)obj).getAddress();
			return other != null && other.equals(this.getAddress());
		}
		return  false;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(String key,Object value){
		if(attributes == null){
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key,value);
	}
	public Object getAttribute(String key){
		if(attributes != null) {
			return attributes.get(key);
		}
		else{
			return null;
		}
	}
}

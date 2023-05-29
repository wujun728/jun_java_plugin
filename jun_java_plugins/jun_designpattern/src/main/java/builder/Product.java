package builder;

public class Product {
	private String head;
	private String body;
	private String foot;
	
	Product() {
	}
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFoot() {
		return foot;
	}
	public void setFoot(String foot) {
		this.foot = foot;
	}
	
	@Override
	public String toString() {
		return head + "-" + body + "-" + foot;
	}
}

package po;

import java.io.Serializable;

public class Mail implements Serializable{
	
	private static final long serialVersionUID = -8140693840257585779L;
	private String mailId;
	private String country;
	private Double weight;
	
	
	public Mail() {
	}
	public Mail(String mailId, String country, double weight) {
		this.mailId = mailId;
		this.country = country;
		this.weight = weight;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Mail [mailId=" + mailId + ", country=" + country + ", weight="
				+ weight + "]";
	}
	
}

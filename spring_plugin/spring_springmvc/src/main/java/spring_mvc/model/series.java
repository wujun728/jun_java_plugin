package spring_mvc.model;

public class series {
	private String name;
	private String type;
	private Integer[] data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer[] getData() {
		return data;
	}
	public void setData(Integer[] data) {
		this.data = data;
	}
	public series(String name, String type, Integer[] data) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
	}
}

package cn.wuwenyao.db.doc.generator.entity;

/***
 * 列信息
 * 
 * @author wwy
 *
 */
public class TableFieldInfo {

	/***
	 * 列名
	 */
	private String field;

	/***
	 * 类型
	 */
	private String type;

	/***
	 * 是否能为空
	 */
	private String nullAble;

	/***
	 * 键
	 */
	private String key;

	/***
	 * 默认值
	 */
	private String defaultValue;

	/***
	 * 额外信息
	 */
	private String extra;

	/***
	 * 备注信息
	 */
	private String remark;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNullAble() {
		return nullAble;
	}

	public void setNullAble(String nullAble) {
		this.nullAble = nullAble;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

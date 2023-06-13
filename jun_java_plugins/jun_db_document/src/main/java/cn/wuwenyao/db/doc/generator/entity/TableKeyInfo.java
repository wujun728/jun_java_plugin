package cn.wuwenyao.db.doc.generator.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/***
 * 表的索引信息
 * 
 * @author wwy
 *
 */
public class TableKeyInfo implements Comparable{

	public static final String PRIMARY_KEY = "PRIMARY";


	/***
	 * 索引名称
	 */
	private String name;

	/***
	 * 包含那些字段
	 */
	private List<String> columns;

	/***
	 * 是否唯一
	 */
	private Boolean unique;

	/***
	 * 索引类型
	 */
	private String indexType;

	/***
	 * 索引注释
	 */
	private String indexComment;

	public TableKeyInfo() {

	}

	public String getColumnCombine() {
		return StringUtils.join(columns, ",");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getIndexComment() {
		return indexComment;
	}

	public void setIndexComment(String indexComment) {
		this.indexComment = indexComment;
	}

	@Override
	public int compareTo(Object o) {
		if(this.equals(o)){
			return 0;
		}
		TableKeyInfo keyInfo2 = (TableKeyInfo) o;
		String name2 = keyInfo2.getName();
		if(PRIMARY_KEY.equalsIgnoreCase(this.name)){
			return -1;
		}
		if(PRIMARY_KEY.equalsIgnoreCase(name2)){
			return 1;
		}
		return this.name.compareTo(name2);
	}
}

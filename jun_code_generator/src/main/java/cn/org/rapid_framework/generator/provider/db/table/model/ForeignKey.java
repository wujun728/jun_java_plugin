/*
 * Created on Jan 1, 2005
 *
 */
package cn.org.rapid_framework.generator.provider.db.table.model;


import java.util.List;

import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.util.ListHashtable;
import cn.org.rapid_framework.generator.util.StringHelper;

/**
 * @author chris
 *
 */
public class ForeignKey implements java.io.Serializable{

	
	protected String   relationShip   = null;
	protected String   firstRelation  = null;
	protected String   secondRelation = null;
	protected Table parentTable ;
	protected String   tableName;
	protected ListHashtable columns ;
	protected ListHashtable parentColumns;
	
	public ForeignKey(Table aTable, String tblName) {
		super();
		parentTable = aTable;
		tableName   = tblName;
		columns = new ListHashtable();
		parentColumns = new ListHashtable();
	}

	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return tableName;
	}
	public String getParentTableName() {
		return parentTable.getSqlName();
	}
	/**
	 * @param col
	 * @param seq
	 */
	public void addColumn(String col,  String parentCol, Integer seq) {
		columns.put(seq, col);
		parentColumns.put(seq,parentCol);
	}
	public String getColumn(String parentCol) {
		// return the associated column given the parent column
		Object key = parentColumns.getKeyForValue(parentCol);
		String col = (String) columns.get(key);
		//System.out.println("get Column for" +parentCol);
		//System.out.println("key = "+key);
		//System.out.println("col="+col);
		//System.out.println("ParentColumns = "+parentColumns.toString());
		return col;
	}
	public ListHashtable getColumns() {
		return columns;
	}
	/**
	 * 
	 */
	private void initRelationship() {
		firstRelation   = "";
		secondRelation  = "";
		Table foreignTable = null;
		try {
			foreignTable = (Table) TableFactory.getInstance().getTable(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List parentPrimaryKeys    = parentTable.getPrimaryKeyColumns();
		List foreignPrimaryKeys   = foreignTable.getPrimaryKeyColumns();
		
		if (hasAllPrimaryKeys(parentPrimaryKeys,parentColumns))
			firstRelation = "one";
		else
			firstRelation = "many";

		if (hasAllPrimaryKeys(foreignPrimaryKeys,columns))
			secondRelation = "one";
		else
			secondRelation = "many";

		relationShip = firstRelation + "-to-" + secondRelation;
		 
	}
	private boolean hasAllPrimaryKeys(List pkeys, ListHashtable cols) {
		boolean hasAll = true;
		// if size is not equal then false
		int numKeys = pkeys.size();
		if (numKeys != cols.size())
			return false;
		
		for (int i=0;i<numKeys;i++) {
			Column col = (Column) pkeys.get(i);
			String colname = col.getColumnName();
			if (!cols.contains(colname))
				return false;
		}
		
		return hasAll;
	}
	public boolean isParentColumnsFromPrimaryKey() {
		boolean isFrom = true;
		List keys = parentTable.getPrimaryKeyColumns();
		int numKeys = getParentColumns().size();
		for (int i=0;i<numKeys;i++) {
			String pcol = (String) getParentColumns().getOrderedValue(i);
			if (!primaryKeyHasColumn(pcol)) {
				isFrom=false;
				break;
			}
		}
		return  isFrom;
	}
	private boolean primaryKeyHasColumn(String aColumn) {
		boolean isFound = false;
		int numKeys = parentTable.getPrimaryKeyColumns().size();
		for (int i=0;i<numKeys;i++) {
			Column sqlCol = (Column)parentTable.getPrimaryKeyColumns().get(i);
			String colname = sqlCol.getColumnName();
			if (colname.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}
	public boolean getHasImportedKeyColumn(String aColumn) {
		boolean isFound = false;
		List cols = getColumns().getOrderedValues();
		int numCols = cols.size();
		for (int i=0;i<numCols;i++) {
			String col = (String) cols.get(i);
			if (col.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return  isFound;
	}
	/**
	 * @return Returns the firstRelation.
	 */
	public String getFirstRelation() {
		if (firstRelation == null)
			initRelationship();
		return firstRelation;
	}
	public Table getSqlTable() {
		Table table = null;
		try {
			table = (Table) TableFactory.getInstance().getTable(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	/**
	 * @return Returns the parentTable.
	 */
	public Table getParentTable() {
		return parentTable;
	}
	/**
	 * @return Returns the relationShip.
	 */
	public String getRelationShip() {
		if (relationShip == null)
			initRelationship();
		return relationShip;
	}
	/**
	 * @return Returns the secondRelation.
	 */
	public String getSecondRelation() {
		if (secondRelation == null)
			initRelationship();
		return secondRelation;
	}
	/**
	 * @return Returns the parentColumns.
	 */
	public ListHashtable getParentColumns() {
		return parentColumns;
	}

	public boolean getHasImportedKeyParentColumn(String aColumn) {
		
		boolean isFound = false;
		List cols = getParentColumns().getOrderedValues();
		int numCols = cols.size();
		for (int i=0;i<numCols;i++) {
			String col = (String) cols.get(i);
			if (col.equals(aColumn)) {
				isFound = true;
				break;
			}
		}
		return  isFound;
	}
	
	/**
	 * 外键引用使用的值对象
	 * @author badqiu
	 *
	 */
	public static class ReferenceKey implements java.io.Serializable{
		public String schemaName;
		public String tableName;
		public String columnSqlName;
		public ReferenceKey(String schemaName, String tableName,String columnSqlName) {
			this.schemaName = StringHelper.defaultIfEmpty(schemaName, null);
			this.tableName = tableName;
			this.columnSqlName = columnSqlName;
		}
		
		public String toString() {
			if(StringHelper.isBlank(schemaName)) {
				return tableName+"("+columnSqlName+")";
			}else {
				return schemaName+"."+tableName+"("+columnSqlName+")";
			}
		}

		public static String toString(ReferenceKey k) {
			if(k == null) return null;
			return k.toString();
		}
		
		/**
		 * 解析foreignKey字符串,格式: fk_table_name(fk_column) 或者 schema_name.fk_table_name(fk_column)
		 * @param foreignKey
		 * @return
		 */
		public static ReferenceKey fromString(String foreignKey) {
			if(StringHelper.isBlank(foreignKey)) {
				return null;
			}
			if(!foreignKey.trim().matches(".*\\w+\\(.*\\)")) {
				throw new IllegalArgumentException("Illegal foreignKey:["+foreignKey+"] ,example value: fk_table_name(fk_column) ");
			}
			String schemaName = foreignKey.substring(0,Math.max(foreignKey.lastIndexOf("."),0));
			String tableSqlName = foreignKey.substring(Math.max(foreignKey.lastIndexOf(".")+1,0),foreignKey.indexOf("("));
			String columnSqlName = foreignKey.substring(foreignKey.indexOf("(")+1,foreignKey.indexOf(")"));
			return new ReferenceKey(schemaName,tableSqlName,columnSqlName);
		}
	}
}

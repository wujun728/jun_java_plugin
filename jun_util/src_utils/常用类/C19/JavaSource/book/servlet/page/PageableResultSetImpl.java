package book.servlet.page;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
/**
 * 支持分页结果集的实现类
 */
public class PageableResultSetImpl implements PageableResultSet {
	// 存储数据的结果集
	private ResultSet rs;
	// 记录的总行数
	private int rowsCount;
	// 每页的记录条数，如果为0，则不分页，即一页显示所有记录
	protected int pageSize;
	//　当前页码，从1开始
    protected int curPage;

    // 构造函数
	public PageableResultSetImpl(java.sql.ResultSet rs) throws SQLException {
		if (rs == null) {
			throw new SQLException("传入的ResuleSet为null");
		}
		// 获取结果集的记录数
		rs.last();
		rowsCount = rs.getRow();
		rs.beforeFirst();
		this.rs = rs;
		
		pageSize = 0;
		curPage = 1;
	} 
	// 获得总的页数
	public int getPageCount() {
		// 没有记录则没有页数
	    if(rowsCount==0){
	    	return 0;
	    }
	    // 如果每页记录数为0，表示用一页显示所有记录，所以页数就为1
	    if(pageSize==0){
	    	return 1;
	    }
	    // 计算总页数，总行数/页记录数
	    double tmpD=(double)rowsCount/pageSize;
	    int tmpI=(int)tmpD;
	    // 如果不能整除，则加1，最后一页没满。
	    if(tmpD>tmpI){ 
	    	tmpI++;
	    }
	    return tmpI;
	}
	// 获取当前页的记录数
	public int getPageRowsCount() {
		// 如果不分页，则当前页的记录数为所有记录
	    if(pageSize==0){
	    	return rowsCount;
	    }
	    // 如果没有记录，则返回0
	    if(rowsCount==0){
	    	return 0;
	    }
	    // 如果当前页不是最后一页，则返回页记录数
	    if(curPage!=getPageCount()){
	    	return pageSize;
	    }
	    // 否则为最后一页，则返回多余的记录数
	    return rowsCount-(getPageCount()-1)*pageSize;
	}
	// 获得每页的最大记录数
	public int getPageSize() {
	    return pageSize;
	}
	// 获得总的记录数
	public int getRowsCount() {
	    return rowsCount;
	}
	// 获得当前的页码
	public int getCurPage() {
	    return curPage;
	}
	// 转入到某页
	public void gotoPage(int page) {
	    if (rs == null){
	        return;
	    }
	    // 如果参数页码小于1或者大于总页数，则自动调整参数页码的值
	    if (page < 1){
	        page = 1;
	    } else if (page > getPageCount()){
	        page = getPageCount();
	    }
	    // 定位到参数页。将结果集指针移到参数页的开始位置
	    int row = (page - 1) * pageSize + 1;
	    try {
	        rs.absolute(row);
	        curPage = page;
	    } catch (java.sql.SQLException e) {
	    }
	}
	// 转到当前页的第一条记录
	public void pageFirst() throws java.sql.SQLException {
	    int row=(curPage-1)*pageSize+1;
	    rs.absolute(row);
	}
	// 转到当前页的最后一条记录
	public void pageLast() throws java.sql.SQLException {
	    int row=(curPage-1)*pageSize+getPageRowsCount();
	    rs.absolute(row);
	}
	// 设置页面最大记录的大小
	public void setPageSize(int pageSize) {
	    if(pageSize>=0){
	        this.pageSize = pageSize;
	        curPage=1;
	    }
	} 
	/***下面的方法都是实现的ResultSet接口定义的方法，直接使用装饰模式，调用rs的相应方法即可***/
	public boolean next() throws SQLException {
		return rs.next();
	}

	public void close() throws SQLException {
		rs.close();
	}

	public boolean wasNull() throws SQLException {
		return rs.wasNull();
	}

	public String getString(int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return rs.getBoolean(columnIndex);
	}

	public byte getByte(int columnIndex) throws SQLException {
		return rs.getByte(columnIndex);
	}

	public short getShort(int columnIndex) throws SQLException {
		return rs.getShort(columnIndex);
	}

	public int getInt(int columnIndex) throws SQLException {
		return rs.getInt(columnIndex);
	}

	public long getLong(int columnIndex) throws SQLException {
		return rs.getLong(columnIndex);
	}

	public float getFloat(int columnIndex) throws SQLException {
		return rs.getFloat(columnIndex);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return rs.getDouble(columnIndex);
	}
	
	@Deprecated
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		return rs.getBigDecimal(columnIndex, scale);
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return rs.getBytes(columnIndex);
	}

	public Date getDate(int columnIndex) throws SQLException {
		return rs.getDate(columnIndex);
	}

	public Time getTime(int columnIndex) throws SQLException {
		return rs.getTime(columnIndex);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return rs.getTimestamp(columnIndex);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return rs.getAsciiStream(columnIndex);
	}
	@Deprecated
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return rs.getUnicodeStream(columnIndex);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return rs.getBinaryStream(columnIndex);
	}

	public String getString(String columnName) throws SQLException {
		return rs.getString(columnName);
	}

	public boolean getBoolean(String columnName) throws SQLException {
		return rs.getBoolean(columnName);
	}

	public byte getByte(String columnName) throws SQLException {
		return rs.getByte(columnName);
	}

	public short getShort(String columnName) throws SQLException {
		return rs.getShort(columnName);
	}

	public int getInt(String columnName) throws SQLException {
		return rs.getInt(columnName);
	}

	public long getLong(String columnName) throws SQLException {
		return rs.getLong(columnName);
	}

	public float getFloat(String columnName) throws SQLException {
		return rs.getFloat(columnName);
	}

	public double getDouble(String columnName) throws SQLException {
		return rs.getDouble(columnName);
	}
	@Deprecated
	public BigDecimal getBigDecimal(String columnName, int scale)
			throws SQLException {
		return rs.getBigDecimal(columnName, scale);
	}

	public byte[] getBytes(String columnName) throws SQLException {
		return rs.getBytes(columnName);
	}

	public Date getDate(String columnName) throws SQLException {
		return rs.getDate(columnName);
	}

	public Time getTime(String columnName) throws SQLException {
		return rs.getTime(columnName);
	}

	public Timestamp getTimestamp(String columnName) throws SQLException {
		return rs.getTimestamp(columnName);
	}

	public InputStream getAsciiStream(String columnName) throws SQLException {
		return rs.getAsciiStream(columnName);
	}
	@Deprecated
	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return rs.getUnicodeStream(columnName);
	}

	public InputStream getBinaryStream(String columnName) throws SQLException {
		return rs.getBinaryStream(columnName);
	}

	public SQLWarning getWarnings() throws SQLException {
		return rs.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		rs.clearWarnings();
	}

	public String getCursorName() throws SQLException {
		return rs.getCursorName();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return rs.getMetaData();
	}

	public Object getObject(int columnIndex) throws SQLException {
		return rs.getObject(columnIndex);
	}

	public Object getObject(String columnName) throws SQLException {
		return rs.getObject(columnName);
	}

	public int findColumn(String columnName) throws SQLException {
		return rs.findColumn(columnName);
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return rs.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnName) throws SQLException {
		return rs.getCharacterStream(columnName);
	}
	@Deprecated
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return rs.getBigDecimal(columnIndex);
	}
	@Deprecated
	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		return rs.getBigDecimal(columnName);
	}
	
	public boolean isBeforeFirst() throws SQLException {
		return rs.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		return rs.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		return rs.isFirst();
	}

	public boolean isLast() throws SQLException {
		return rs.isLast();
	}

	public void beforeFirst() throws SQLException {
		rs.beforeFirst();
	}

	public void afterLast() throws SQLException {
		rs.afterLast();
	}

	public boolean first() throws SQLException {
		return rs.first();
	}

	public boolean last() throws SQLException {
		return rs.last();
	}

	public int getRow() throws SQLException {
		return rs.getRow();
	}

	public boolean absolute(int row) throws SQLException {
		return rs.absolute(row);
	}

	public boolean relative(int rows) throws SQLException {
		return rs.relative(rows);
	}

	public boolean previous() throws SQLException {
		return rs.previous();
	}

	public void setFetchDirection(int direction) throws SQLException {
		rs.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		return rs.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		rs.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		return rs.getFetchSize();
	}

	public int getType() throws SQLException {
		return rs.getType();
	}

	public int getConcurrency() throws SQLException {
		return rs.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		return rs.rowUpdated();
	}

	public boolean rowInserted() throws SQLException {
		return rs.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		return rs.rowDeleted();
	}

	public void updateNull(int columnIndex) throws SQLException {
		rs.updateNull(columnIndex);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		rs.updateBoolean(columnIndex, x);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		rs.updateByte(columnIndex, x);
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		rs.updateShort(columnIndex, x);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		rs.updateInt(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		rs.updateLong(columnIndex, x);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		rs.updateFloat(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		rs.updateDouble(columnIndex, x);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		rs.updateBigDecimal(columnIndex, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		rs.updateString(columnIndex, x);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		rs.updateBytes(columnIndex, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		rs.updateDate(columnIndex, x);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		rs.updateTime(columnIndex, x);
	}

	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		rs.updateTimestamp(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		rs.updateCharacterStream(columnIndex, x, length);
	}

	public void updateObject(int columnIndex, Object x, int scale)
			throws SQLException {
		rs.updateObject(columnIndex, x, scale);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		rs.updateObject(columnIndex, x);
	}

	public void updateNull(String columnName) throws SQLException {
		rs.updateNull(columnName);
	}

	public void updateBoolean(String columnName, boolean x) throws SQLException {
		rs.updateBoolean(columnName, x);
	}

	public void updateByte(String columnName, byte x) throws SQLException {
		rs.updateByte(columnName, x);
	}

	public void updateShort(String columnName, short x) throws SQLException {
		rs.updateShort(columnName, x);
	}

	public void updateInt(String columnName, int x) throws SQLException {
		rs.updateInt(columnName, x);
	}

	public void updateLong(String columnName, long x) throws SQLException {
		rs.updateLong(columnName, x);
	}

	public void updateFloat(String columnName, float x) throws SQLException {
		rs.updateFloat(columnName, x);
	}

	public void updateDouble(String columnName, double x) throws SQLException {
		rs.updateDouble(columnName, x);
	}

	public void updateBigDecimal(String columnName, BigDecimal x)
			throws SQLException {
		rs.updateBigDecimal(columnName, x);
	}

	public void updateString(String columnName, String x) throws SQLException {
		rs.updateString(columnName, x);
	}

	public void updateBytes(String columnName, byte[] x) throws SQLException {
		rs.updateBytes(columnName, x);
	}

	public void updateDate(String columnName, Date x) throws SQLException {
		rs.updateDate(columnName, x);
	}

	public void updateTime(String columnName, Time x) throws SQLException {
		rs.updateTime(columnName, x);
	}

	public void updateTimestamp(String columnName, Timestamp x)
			throws SQLException {
		rs.updateTimestamp(columnName, x);
	}

	public void updateAsciiStream(String columnName, InputStream x, int length)
			throws SQLException {
		rs.updateAsciiStream(columnName, x, length);
	}

	public void updateBinaryStream(String columnName, InputStream x, int length)
			throws SQLException {
		rs.updateBinaryStream(columnName, x, length);
	}

	public void updateCharacterStream(String columnName, Reader reader,
			int length) throws SQLException {
		rs.updateCharacterStream(columnName, reader, length);
	}

	public void updateObject(String columnName, Object x, int scale)
			throws SQLException {
		rs.updateObject(columnName, x, scale);
	}

	public void updateObject(String columnName, Object x) throws SQLException {
		rs.updateObject(columnName, x);
	}

	public void insertRow() throws SQLException {
		rs.insertRow();
	}

	public void updateRow() throws SQLException {
		rs.updateRow();
	}

	public void deleteRow() throws SQLException {
		rs.deleteRow();
	}

	public void refreshRow() throws SQLException {
		rs.refreshRow();
	}

	public void cancelRowUpdates() throws SQLException {
		rs.cancelRowUpdates();
	}

	public void moveToInsertRow() throws SQLException {
		rs.moveToInsertRow();
	}

	public void moveToCurrentRow() throws SQLException {
		rs.moveToCurrentRow();
	}

	public Statement getStatement() throws SQLException {
		return rs.getStatement();
	}

	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		return rs.getObject(arg0, arg1);
	}

	public Ref getRef(int i) throws SQLException {
		return rs.getRef(i);
	}

	public Blob getBlob(int i) throws SQLException {
		return rs.getBlob(i);
	}

	public Clob getClob(int i) throws SQLException {
		return rs.getClob(i);
	}

	public Array getArray(int i) throws SQLException {
		return rs.getArray(i);
	}

	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		return rs.getObject(arg0, arg1);
	}

	public Ref getRef(String colName) throws SQLException {
		return rs.getRef(colName);
	}

	public Blob getBlob(String colName) throws SQLException {
		return rs.getBlob(colName);
	}

	public Clob getClob(String colName) throws SQLException {
		return rs.getClob(colName);
	}

	public Array getArray(String colName) throws SQLException {
		return rs.getArray(colName);
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return rs.getDate(columnIndex, cal);
	}

	public Date getDate(String columnName, Calendar cal) throws SQLException {
		return rs.getDate(columnName, cal);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return rs.getTime(columnIndex, cal);
	}

	public Time getTime(String columnName, Calendar cal) throws SQLException {
		return rs.getTime(columnName, cal);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return rs.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnName, Calendar cal)
			throws SQLException {
		return rs.getTimestamp(columnName, cal);
	}

	public URL getURL(int columnIndex) throws SQLException {
		return rs.getURL(columnIndex);
	}

	public URL getURL(String columnName) throws SQLException {
		return rs.getURL(columnName);
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		rs.updateRef(columnIndex, x);
	}

	public void updateRef(String columnName, Ref x) throws SQLException {
		rs.updateRef(columnName, x);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		rs.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnName, Blob x) throws SQLException {
		rs.updateBlob(columnName, x);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		rs.updateClob(columnIndex, x);
	}

	public void updateClob(String columnName, Clob x) throws SQLException {
		rs.updateClob(columnName, x);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		rs.updateArray(columnIndex, x);
	}

	public void updateArray(String columnName, Array x) throws SQLException {
		rs.updateArray(columnName, x);
	}
}

#dbutil

使用方法：

```
	public static void main(String[] args) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:wenbin",
					"", "");
			DBContextHolder.setContextConnection(conn);
			DBUTil.save(Object);
			DBUTil.delete(Object);
			DBUTil.update(Object);
			DBUTil.getResult(sql, Class)
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
```
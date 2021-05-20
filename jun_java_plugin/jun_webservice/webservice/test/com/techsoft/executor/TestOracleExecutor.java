package com.techsoft.executor;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.Assert;
import junit.framework.TestCase;
import oracle.jdbc.OracleConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.techsoft.DBException;
import com.techsoft.DataExecutor;
import com.techsoft.SQLParam;
import com.techsoft.TestConsts;

public class TestOracleExecutor extends TestCase {
	private Connection conn = null;
	private OracleConnection oraconn = null;
	private static DataExecutor executor = OracleExecutor.getInstance();
	private static String dropTable = "drop table TESTORACLEEXECUTOR";
	private static String createTable = "create table TESTORACLEEXECUTOR \n"
			+ "( \n" + "  id             NUMBER(7) not null,\n"
			+ "  strfield       VARCHAR2(200),\n" + "  clobfield      CLOB,\n"
			+ "  blobfield      BLOB,\n" + "  datefield      DATE,\n"
			+ "  timestampfield TIMESTAMP(6),\n" + "  longfield      LONG,\n"
			+ "  numfields      NUMBER(20),\n"
			+ "  floatfields    NUMBER(30,6),\n" + "  primary key (id)\n" + ")";

	private static String insertSql = "insert into TESTORACLEEXECUTOR t \n"
			+ "  (t.id, \n" + "   t.strfield, \n" + "   t.clobfield, \n"
			+ "   t.blobfield, \n" + "   t.datefield, \n"
			+ "   t.timestampfield, \n" + "   t.longfield, \n"
			+ "   t.numfields, \n" + "   t.floatfields) \n" + "values (\n"
			+ "  ?,\n" + "  ?,\n" + "  ?,\n" + "  ?,\n" + "  ?,\n" + "  ?,\n"
			+ "  ?,\n" + "  ?,\n" + "  ?\n" + ")";

	private static String paramSql = "begin \n" + "  select t.id, \n"
			+ "         t.strfield, \n" + "         t.clobfield, \n"
			+ "         t.blobfield, \n" + "         t.datefield, \n"
			+ "         t.timestampfield, \n" + "         t.longfield, \n"
			+ "         t.numfields, \n" + "         t.floatfields \n"
			+ "    into :id, \n" + "         :strfield, \n"
			+ "         :clobfield, \n" + "         :blobfield, \n"
			+ "         :datefield, \n" + "         :timestampfield, \n"
			+ "         :longfield, \n" + "         :numfields, \n"
			+ "         :floatfields \n" + "    from TESTORACLEEXECUTOR t; \n"
			+ "end; \n";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		conn = TestConsts.getPool().getConnection();
		oraconn = (OracleConnection) TestConsts.getPool().getNativeConnection(
				conn);
		try {
			PreparedStatement statement = oraconn.prepareCall(createTable);
			statement.execute();
			oraconn.commit();
		} catch (Exception e) {
			oraconn.rollback();
		}
	}

	@After
	protected void tearDown() throws Exception {
		try {
			PreparedStatement statement = oraconn.prepareCall(dropTable);
			statement.execute();
			oraconn.commit();
		} catch (Exception e) {
			oraconn.rollback();
		}
		conn.close();
		conn = null;
		super.tearDown();
	}

	@Test
	public void testsetParam() throws SQLException, IOException, DBException {
		CallableStatement statement = oraconn.prepareCall(insertSql);
		SQLParam param1 = new SQLParam();
		param1.setDtype("2");
		param1.setDtypename("NUMBER");
		param1.setIndex("1");
		param1.setIotype("in");
		param1.setName("id");
		param1.setValue(12);
		param1.buildPositions();
		executor.setParam(param1, statement, oraconn);

		SQLParam param2 = new SQLParam();
		param2.setDtype("12");
		param2.setDtypename("VARCHAR");
		param2.setIndex("2");
		param2.setIotype("in");
		param2.setName("strfield");
		param2.setValue("sdfasdfasdf");
		param2.buildPositions();
		executor.setParam(param2, statement, oraconn);

		SQLParam param3 = new SQLParam();
		param3.setDtype("2005");
		param3.setDtypename("CLOB");
		param3.setIndex("3");
		param3.setIotype("in");
		param3.setName("clobfield");
		param3.setValue("bbbbbbbbbb");
		param3.buildPositions();
		executor.setParam(param3, statement, oraconn);

		SQLParam param4 = new SQLParam();
		param4.setDtype("2004");
		param4.setDtypename("BLOB");
		param4.setIndex("4");
		param4.setIotype("in");
		param4.setName("blobfield");
		param4.setValue("222222222222");
		param4.buildPositions();
		executor.setParam(param4, statement, oraconn);

		SQLParam param5 = new SQLParam();
		param5.setDtype("91");
		param5.setDtypename("DATE");
		param5.setIndex("5");
		param5.setIotype("in");
		param5.setName("datefield");
		param5.setValue("2005-01-04 12:12:12");
		param5.buildPositions();
		executor.setParam(param5, statement, oraconn);

		SQLParam param6 = new SQLParam();
		param6.setDtype("93");
		param6.setDtypename("TIMESTAMP");
		param6.setIndex("6");
		param6.setIotype("in");
		param6.setName("timestampfield");
		param6.setValue("2005-01-04 12:12:12:111");
		param6.buildPositions();
		executor.setParam(param6, statement, oraconn);

		SQLParam param7 = new SQLParam();
		param7.setDtype("-1");
		param7.setDtypename("LONGVARCHAR");
		param7.setIndex("7");
		param7.setIotype("in");
		param7.setName("longfield");
		param7.setValue("asdfsadfasdfsa");
		param7.buildPositions();
		executor.setParam(param7, statement, oraconn);

		SQLParam param8 = new SQLParam();
		param8.setDtype("2");
		param8.setDtypename("NUMBER");
		param8.setIndex("8");
		param8.setIotype("in");
		param8.setName("numfields");
		param8.setValue("58677");
		param8.buildPositions();
		executor.setParam(param8, statement, oraconn);

		SQLParam param9 = new SQLParam();
		param9.setDtype("2");
		param9.setDtypename("NUMBER");
		param9.setIndex("9");
		param9.setIotype("in");
		param9.setName("floatfields");
		param9.setValue(333333333333333333333344.333377);
		param9.buildPositions();
		executor.setParam(param9, statement, oraconn);

		statement.execute();
		oraconn.commit();
	}

	@Test
	public void testgetParam() throws SQLException, IOException, DBException {
		this.testsetParam();
		CallableStatement statement = oraconn.prepareCall(paramSql);
		statement.registerOutParameter(1, 2);
		statement.registerOutParameter(2, 12);
		statement.registerOutParameter(3, 2005);
		statement.registerOutParameter(4, 2004);
		statement.registerOutParameter(5, 91);
		statement.registerOutParameter(6, 93);
		statement.registerOutParameter(7, -1);
		statement.registerOutParameter(8, 2);
		statement.registerOutParameter(9, 2);
		statement.execute();

		SQLParam param1 = new SQLParam();
		param1.setDtype("2");
		param1.setDtypename("NUMBER");
		param1.setIndex("1");
		param1.setIotype("out");
		param1.setName("id");
		param1.buildPositions();
		executor.getParam(param1, statement, oraconn);

		SQLParam param2 = new SQLParam();
		param2.setDtype("12");
		param2.setDtypename("VARCHAR");
		param2.setIndex("2");
		param2.setIotype("out");
		param2.setName("strfield");
		param2.buildPositions();
		executor.getParam(param2, statement, oraconn);

		SQLParam param3 = new SQLParam();
		param3.setDtype("2005");
		param3.setDtypename("CLOB");
		param3.setIndex("3");
		param3.setIotype("out");
		param3.setName("clobfield");
		param3.buildPositions();
		executor.getParam(param3, statement, oraconn);

		SQLParam param4 = new SQLParam();
		param4.setDtype("2004");
		param4.setDtypename("BLOB");
		param4.setIndex("4");
		param4.setIotype("out");
		param4.setName("blobfield");
		param4.buildPositions();
		executor.getParam(param4, statement, oraconn);

		SQLParam param5 = new SQLParam();
		param5.setDtype("91");
		param5.setDtypename("DATE");
		param5.setIndex("5");
		param5.setIotype("out");
		param5.setName("datefield");
		param5.buildPositions();
		executor.getParam(param5, statement, oraconn);

		SQLParam param6 = new SQLParam();
		param6.setDtype("93");
		param6.setDtypename("TIMESTAMP");
		param6.setIndex("6");
		param6.setIotype("out");
		param6.setName("timestampfield");
		param6.buildPositions();
		executor.getParam(param6, statement, oraconn);

		SQLParam param7 = new SQLParam();
		param7.setDtype("-1");
		param7.setDtypename("LONGVARCHAR");
		param7.setIndex("7");
		param7.setIotype("out");
		param7.setName("longfield");
		param7.buildPositions();
		executor.getParam(param7, statement, oraconn);

		SQLParam param8 = new SQLParam();
		param8.setDtype("2");
		param8.setDtypename("NUMBER");
		param8.setIndex("8");
		param8.setIotype("out");
		param8.setName("numfields");
		param8.buildPositions();
		executor.getParam(param8, statement, oraconn);

		SQLParam param9 = new SQLParam();
		param9.setDtype("2");
		param9.setDtypename("NUMBER");
		param9.setIndex("9");
		param9.setIotype("out");
		param9.setName("floatfields");
		param9.buildPositions();
		executor.getParam(param9, statement, oraconn);
		
		System.out.println(param1.getValue());
		System.out.println(param2.getValue());
		System.out.println(param3.getValue());
		System.out.println(param4.getValue());
		System.out.println(param5.getValue());
		System.out.println(param6.getValue());
		System.out.println(param7.getValue());
		System.out.println(param8.getValue());
		System.out.println(param9.getValue());
	}

	@Test
	public void testqueryMetaData() {
		Assert.assertEquals(true, true);
	}

	@Test
	public void testqueryData() {
		Assert.assertEquals(true, true);
	}

	@Test
	public void testsaveData() {
		Assert.assertEquals(true, true);
	}

	@Test
	public void testserializeResultSet() {
		Assert.assertEquals(true, true);
	}
}

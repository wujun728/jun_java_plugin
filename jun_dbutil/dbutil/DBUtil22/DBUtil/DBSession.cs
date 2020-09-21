using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Data;
using System.Data.Common;
using System.Reflection;
using System.Linq;
using System.Text;

namespace DBUtil
{
    public enum DataBaseType
    {
        MsSql,
        MySql,
        Oracle,
        OracleManaged,
        Sqlite,
        Oledb
    }
    public class DBSession
    {

        #region 构造函数、属性
        public string ConnectionString { get; set; }

        private string _providerName = null;
        public string ProviderName
        {
            get { return _providerName; }
        }

        private string _paramCharacter = null;
        public string ParamCharacter
        {
            get { return _paramCharacter; }
        }


        private DbProviderFactory _dbfactory = null;
        private DbProviderFactory DBFactory
        {
            get { return _dbfactory; }
        }

        private DataBaseType _DBaseType = DataBaseType.MsSql;
        private DataBaseType DBaseType
        {
            get { return _DBaseType; }
        }
        public DBSession(DataBaseType dataBaseType)
        {
            this._DBaseType = dataBaseType;
            SetProviderName(dataBaseType, string.Empty);
        }
        public DBSession(DataBaseType dataBaseType, string connectionString)
        {
            this._DBaseType = dataBaseType;
            SetProviderName(dataBaseType, connectionString);
            this.ConnectionString = connectionString;
        }
        private void SetProviderName(DataBaseType dataBaseType, string connectionString)
        {
            switch (dataBaseType)
            {
                case DataBaseType.MsSql:
                    _providerName = "System.Data.SqlClient";
                    _paramCharacter = "@";
                    break;
                case DataBaseType.MySql:
                    _providerName = "MySql.Data.MySqlClient";
                    _paramCharacter = "@";
                    break;
                case DataBaseType.Oracle:
                    _providerName = "System.Data.OracleClient";
                    _paramCharacter = ":";
                    break;
                case DataBaseType.OracleManaged:
                    _providerName = "Oracle.ManagedDataAccess.Client";
                    _paramCharacter = ":";
                    break;
                case DataBaseType.Sqlite:
                    _providerName = "System.Data.SQLite";
                    _paramCharacter = "@";
                    break;
                case DataBaseType.Oledb:
                    _providerName = "System.Data.OleDb";
                    _paramCharacter = "@";
                    break;
                default:
                    _providerName = "System.Data.OleDb";
                    _paramCharacter = "@";
                    break;
            }
            if (!string.IsNullOrWhiteSpace(connectionString))
            {
                this.ConnectionString = connectionString;
            }
            else if (ConfigurationManager.ConnectionStrings[dataBaseType.ToString()] != null)
            {
                this.ConnectionString = ConfigurationManager.ConnectionStrings[dataBaseType.ToString()].ConnectionString;
            }
            else
            {
                throw new ArgumentNullException("connectionString", dataBaseType.ToString() + "的connectionString不能为空！");
            }
            try
            {
                _dbfactory = DbProviderFactories.GetFactory(ProviderName);
            }
            catch (ConfigurationErrorsException ex)
            {

                throw new ConfigurationErrorsException(ex.Message + "(" + ProviderName + ")", ex);
            }

        }
        #endregion

        #region Create
        private DbConnection CreateConnection()
        {
            DbConnection dbconn = DBFactory.CreateConnection();
            dbconn.ConnectionString = ConnectionString;
            return dbconn;
        }


        private DbCommand CreateCommand(CommandType commandType = CommandType.Text)
        {
            DbCommand dbcommand = DBFactory.CreateCommand();
            dbcommand.CommandType = commandType;
            return dbcommand;
        }
        private DbCommand CreateCommand(string strSql, CommandType commandType = CommandType.Text)
        {
            DbCommand dbcommand = DBFactory.CreateCommand();
            dbcommand.CommandText = strSql;
            dbcommand.CommandType = commandType;
            return dbcommand;
        }

        private DbCommand CreateCommand(string strSql, DbConnection dbconn, CommandType commandType = CommandType.Text)
        {
            DbCommand dbcommand = DBFactory.CreateCommand();
            dbcommand.Connection = dbconn;
            dbcommand.CommandText = strSql;
            dbcommand.CommandType = commandType;
            return dbcommand;
        }

        private DbDataAdapter CreateDataAdapter()
        {
            DbDataAdapter dbadapter = DBFactory.CreateDataAdapter();
            return dbadapter;
        }

        private DbDataAdapter CreateDataAdapter(DbCommand dbCommand)
        {
            DbDataAdapter dbadapter = DBFactory.CreateDataAdapter();
            dbadapter.SelectCommand = dbCommand;
            return dbadapter;
        }

        private DbParameter CreateDbParameter()
        {
            DbParameter dbparameter = DBFactory.CreateParameter();
            return dbparameter;
        }

        #endregion


        /// <summary>
        /// 验证SQL语句是否会执行出错
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="msg">错误消息</param>
        /// <returns>是否出错</returns>
        public bool ValidateSql(string strSql, out string msg)
        {
            bool bResult = false;
            msg = string.Empty;
            using (DbConnection conn = CreateConnection())
            {
                DbCommand cmd = conn.CreateCommand();
                conn.Open();
                try
                {
                    if (DBaseType == DataBaseType.MsSql)
                    {
                        cmd.CommandText = "set noexec on;";
                        cmd.ExecuteNonQuery();

                        cmd.CommandText = strSql;
                        cmd.ExecuteNonQuery();

                        cmd.CommandText = "set noexec off;";
                        cmd.ExecuteNonQuery();
                    }
                    else if (DBaseType == DataBaseType.Oracle || DBaseType == DataBaseType.OracleManaged)
                    {
                        cmd.CommandText = "explain plan for " + strSql;
                    }
                    else
                    {
                        throw new NotImplementedException("未能实现" + DBaseType + "方式的验证方法！");
                    }
                    cmd.ExecuteNonQuery();
                    bResult = true;
                }
                catch (Exception ex)
                {
                    msg = ex.Message;
                    bResult = false;
                }
                finally
                {
                    cmd.Dispose();
                    conn.Close();
                }
            }
            return bResult;
        }

        #region 底层 crud

        /// <summary>
        /// 查询单个值是否存在
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <returns>是否存在</returns>
        public bool Exists(string strSql)
        {
            object obj = GetSingle<object>(strSql);
            int cmdresult;
            if ((Object.Equals(obj, null)) || (Object.Equals(obj, System.DBNull.Value)))
            {
                cmdresult = 0;
            }
            else
            {
                cmdresult = int.Parse(obj.ToString());
            }
            if (cmdresult == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /// <summary>
        /// 查询单个值是否存在
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="cmdParms">参数化</param>
        /// <returns>是否存在</returns>
        public bool Exists(string strSql, params IDataParameter[] cmdParms)
        {
            object obj = GetSingle<object>(strSql, 0, cmdParms);
            int cmdresult;
            if ((Object.Equals(obj, null)) || (Object.Equals(obj, System.DBNull.Value)))
            {
                cmdresult = 0;
            }
            else
            {
                cmdresult = int.Parse(obj.ToString());
            }
            if (cmdresult == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /// <summary>
        /// 获取单个值
        /// </summary>
        /// <typeparam name="T">返回类型</typeparam>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <returns>返回值</returns>
        public T GetSingle<T>(string strSql, int times = 0)
        {
            return GetSingle<T>(strSql, times, null);
        }

        /// <summary>
        /// 获取单个值
        /// </summary>
        /// <typeparam name="T">返回类型</typeparam>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <param name="cmdParms">参数化</param>
        /// <returns>返回值</returns>
        public T GetSingle<T>(string strSql, int times, params IDataParameter[] cmdParms)
        {
            using (DbConnection connection = CreateConnection())
            {
                using (DbCommand cmd = CreateCommand())
                {
                    try
                    {
                        PrepareCommand(cmd, connection, null, strSql, cmdParms, times);
                        object obj = cmd.ExecuteScalar();
                        cmd.Parameters.Clear();
                        return (T)Convert.ChangeType(obj, typeof(T));
                    }
                    catch (Exception ex)
                    {
                        connection.Close();

                        throw ex;
                    }
                    finally
                    {
                        cmd.Dispose();
                        connection.Close();
                    }
                }
            }
        }

        /// <summary>
        /// 返回一行值
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <returns>一行数据</returns>
        public DataRow QueryRow(string strSql, int times = 30)
        {
            return QueryRow(strSql, times, null);
        }

        /// <summary>
        /// 返回一行值
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <param name="cmdParms">参数化</param>
        /// <returns>一行数据</returns>
        public DataRow QueryRow(string strSql, int times = 30, params IDataParameter[] cmdParms)
        {
            using (DbConnection connection = CreateConnection())
            {
                DataSet ds = new DataSet("ds");
                using (DbCommand cmd = CreateCommand())
                {
                    PrepareCommand(cmd, connection, null, strSql, cmdParms, times);

                    try
                    {
                        DataAdapter ada = CreateDataAdapter(cmd);
                        ada.Fill(ds);
                        if (ds.Tables[0].Rows.Count > 0)
                            return ds.Tables[0].Rows[0];
                        else
                            return null;
                    }
                    catch (Exception ex)
                    {

                        throw ex;
                    }
                    finally
                    {
                        cmd.Dispose();
                        connection.Close();
                    }
                }
            }
        }

        /// <summary>
        /// 返回DataTable
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <returns>DataTable数据</returns>
        public DataTable QueryTable(string strSql, int times = 30)
        {
            return QueryTable(strSql, times, null);
        }

        /// <summary>
        /// 返回DataTable
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <param name="cmdParms">参数化</param>
        /// <returns>DataTable数据</returns>
        public DataTable QueryTable(string strSql, int times = 30, params IDataParameter[] cmdParms)
        {
            using (DbConnection connection = CreateConnection())
            {
                DbCommand cmd = CreateCommand();
                PrepareCommand(cmd, connection, null, strSql, cmdParms, times);
                using (DbDataAdapter da = CreateDataAdapter(cmd))
                {
                    DataSet ds = new DataSet("ds");
                    try
                    {
                        da.Fill(ds);
                        cmd.Parameters.Clear();
                    }
                    catch (Exception ex)
                    {
                        connection.Close();

                        throw ex;
                    }
                    finally
                    {
                        cmd.Dispose();
                        connection.Close();
                    }
                    return (ds == null || ds.Tables.Count <= 0) ? new DataTable("dt") : ds.Tables[0];
                }
            }
        }

        /// <summary>
        /// 返回DataTable集合
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <returns>DataTable集合</returns>
        public List<DataTable> Query(string strSql, int times = 30)
        {
            return Query(strSql, times, null);
        }
        public List<DataTable> Query(string strSql, int times = 30, params IDataParameter[] cmdParms)
        {
            using (DbConnection connection = CreateConnection())
            {
                DbCommand cmd = connection.CreateCommand();
                PrepareCommand(cmd, connection, null, strSql, cmdParms, times);
                using (DbDataAdapter da = CreateDataAdapter(cmd))
                {
                    List<DataTable> lstTab = new List<DataTable>();
                    DataSet ds = new DataSet();
                    try
                    {
                        da.Fill(ds, "ds");
                        cmd.Parameters.Clear();

                        if (ds != null && ds.Tables.Count > 0)
                        {
                            foreach (DataTable table in ds.Tables)
                            {
                                lstTab.Add(table);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        connection.Close();

                        throw ex;
                    }
                    finally
                    {
                        cmd.Dispose();
                        connection.Close();
                    }
                    return lstTab;
                }
            }
        }

        /// <summary>
        /// 执行Sql语句
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="times">超时时间</param>
        /// <returns>受影响行数</returns>
        public int ExecuteSql(string strSql, int times = 30)
        {
            return ExecuteSql(strSql, times, null);
        }

        /// <summary>
        /// 执行Sql语句
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <param name="cmdParms">参数化</param>
        /// <returns>受影响行数</returns>
        public int ExecuteSql(string strSql, params IDataParameter[] cmdParms)
        {
            return ExecuteSql(strSql, 30, cmdParms);
        }
        public int ExecuteSql(string strSql, int times = 30, params IDataParameter[] cmdParms)
        {

            using (DbConnection connection = CreateConnection())
            {
                using (DbCommand cmd = CreateCommand())
                {
                    try
                    {
                        PrepareCommand(cmd, connection, null, strSql, cmdParms, times);
                        int rows = cmd.ExecuteNonQuery();
                        cmd.Parameters.Clear();
                        return rows;
                    }
                    catch (System.Data.SqlClient.SqlException e)
                    {
                        throw e;
                    }
                    finally
                    {
                        cmd.Dispose();
                        connection.Close();
                    }
                }
            }

        }



        /// <summary>
        /// 执行多条SQL语句，实现数据库事务。
        /// </summary>
        /// <param name="strSqlList">多条SQL语句</param>	
        /// <returns>受影响行数</returns>
        public int ExecuteSqlTran(List<String> strSqlList)
        {
            using (DbConnection conn = CreateConnection())
            {
                conn.Open();
                DbCommand cmd = CreateCommand();
                cmd.Connection = conn;
                DbTransaction tx = conn.BeginTransaction();
                cmd.Transaction = tx;
                try
                {
                    int count = 0;
                    for (int n = 0; n < strSqlList.Count; n++)
                    {
                        string strsql = strSqlList[n];
                        if (strsql.Trim().Length > 1)
                        {
                            cmd.CommandText = strsql;
                            count += cmd.ExecuteNonQuery();
                        }
                    }
                    tx.Commit();
                    return count;
                }
                catch
                {
                    tx.Rollback();
                    return -1;
                }
            }
        }

        /// <summary>
        /// 执行多条SQL语句，实现数据库事务。
        /// </summary>
        /// <param name="strSqlList">SQL语句/参数化数组 键值对集合</param>
        /// <returns>受影响行数</returns>
        public int ExecuteSqlTran(Hashtable strSqlList)
        {
            using (DbConnection conn = CreateConnection())
            {
                conn.Open();
                using (DbTransaction trans = conn.BeginTransaction())
                {
                    DbCommand cmd = CreateCommand();
                    try
                    {
                        int count = 0;
                        //循环
                        foreach (DictionaryEntry myDE in strSqlList)
                        {
                            string cmdText = myDE.Key.ToString();
                            DbParameter[] cmdParms = (DbParameter[])myDE.Value;
                            PrepareCommand(cmd, conn, trans, cmdText, cmdParms);
                            count += cmd.ExecuteNonQuery();
                            cmd.Parameters.Clear();
                        }
                        trans.Commit();
                        return count;
                    }
                    catch (Exception ex)
                    {
                        trans.Rollback();
                        return -1;
                    }
                }
            }
        }

        /// <summary>
        /// 返回 DbDataReader
        /// </summary>
        /// <param name="strSql">Sql语句</param>
        /// <returns>DbDataReader对象</returns>
        public DbDataReader ExecuteReader(string strSql)
        {
            DbConnection connection = CreateConnection();
            DbCommand cmd = CreateCommand(strSql, connection);
            try
            {
                connection.Open();
                DbDataReader myReader = cmd.ExecuteReader(CommandBehavior.CloseConnection);
                return myReader;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        
        private static void PrepareCommand(DbCommand cmd, DbConnection conn, DbTransaction trans, string cmdText, IDataParameter[] cmdParms, int times = 30)
        {
            if (conn.State != ConnectionState.Open)
                conn.Open();
            cmd.Connection = conn;
            cmd.CommandText = cmdText;
            if (trans != null)
                cmd.Transaction = trans;
            cmd.CommandType = CommandType.Text;//cmdType;
            if (times > 0)//（以秒为单位）。默认为 30 秒
            {
                cmd.CommandTimeout = times;
            }
            if (cmdParms != null && cmdParms.Length > 0)
            {
                foreach (IDataParameter parameter in cmdParms)
                {
                    if ((parameter.Direction == ParameterDirection.InputOutput
                        || parameter.Direction == ParameterDirection.Input)
                        && (parameter.Value == null))
                    {
                        parameter.Value = DBNull.Value;
                    }
                    cmd.Parameters.Add(parameter);
                }
            }
        }

        #endregion

        #region 插入

        /// <summary>
        /// 插入NameValueCollection 数据，如：Request.From
        /// </summary>
        /// <typeparam name="T">对象类型</typeparam>
        /// <param name="model">数据</param>
        /// <param name="excludeFields">排除字段</param>
        /// <param name="excludeAttr">排除特性（如：标示的 主键列）</param>
        /// <returns>受影响行数>0 为True，否则为False</returns>
        public bool Insert<T>(NameValueCollection model, string[] excludeFields, params Attribute[] excludeAttr)
            where T : new()
        {
            return Insertmodel<T>(model, excludeFields, excludeAttr);
        }

        /// <summary>
        /// 插入键值对集合的 数据，如：Hashtable、Dictionary
        /// </summary>
        /// <typeparam name="T">对象类型</typeparam>
        /// <param name="model">数据</param>
        /// <param name="excludeFields">排除字段</param>
        /// <param name="excludeAttr">排除特性（如：标示的 主键列）</param>
        /// <returns>受影响行数>0 为True，否则为False</returns>
        public bool Insert<T>(IDictionary model, string[] excludeFields, params Attribute[] excludeAttr)
            where T : new()
        {
            return Insertmodel<T>(model, excludeFields, excludeAttr);
        }

        /// <summary>
        /// 插入对象，表名与类名是一一对应的
        /// </summary>
        /// <typeparam name="T">对象类型</typeparam>
        /// <param name="model">数据</param>
        /// <param name="excludeFields">排除字段</param>
        /// <param name="excludeAttr">排除特性（如：标示的 主键列）</param>
        /// <returns>受影响行数>0 为True，否则为False</returns>
        public bool Insert<T>(T model, string[] excludeFields, params Attribute[] excludeAttr)
            where T : new()
        {
            return Insertmodel<T>(model, excludeFields, excludeAttr);
        }
        private bool Insertmodel<T>(object model, string[] excludeFields, params Attribute[] excludeAttr)
        {
            Type tyTable = typeof(T);
            Hashtable ht = DBUtil.ConvertHashTable<T>(model, excludeFields, excludeAttr);
            StringBuilder sb_beforeSQL = new StringBuilder();
            sb_beforeSQL.Append("insert into " + tyTable.Name + " (");
            StringBuilder sb_afterSQl = new StringBuilder();
            sb_afterSQl.Append(") values (");

            string insert_sql = string.Empty;
            DbParameter[] paras = null;
            List<DbParameter> lstParam = new List<DbParameter>();
            foreach (DictionaryEntry entry in ht)
            {
                string name = entry.Key.ToString();
                string parameterName = ParamCharacter + name;
                object objvalue = entry.Value;

                sb_beforeSQL.Append(name + ",");
                sb_afterSQl.Append(parameterName + ",");

                DbParameter para = CreateDbParameter();
                para.ParameterName = parameterName;
                para.Value = (objvalue ?? DBNull.Value);

                if (objvalue != null)
                {
                    para.DbType = DBUtil.GetValueDbType(objvalue, para);
                }
                lstParam.Add(para);
            }
            insert_sql = sb_beforeSQL.ToString().TrimEnd(',');
            insert_sql += sb_afterSQl.ToString().TrimEnd(',') + ")";
            paras = lstParam.ToArray();
            int res = ExecuteSql(insert_sql, paras);
            return res > 0 ? true : false;
        }

        /// <summary>
        /// 根据表名插入 NameValueCollection 数据（不需要反射情况下使用）
        /// </summary>
        /// <param name="kv">NameValueCollection 数据</param>
        /// <param name="tableName">表名</param>
        /// <param name="excludeFields">排除字段</param>
        /// <returns>受影响行数>0 为True，否则为False</returns>
        public bool Insert(NameValueCollection kv, string tableName, string[] excludeFields)
        {
            return InsertKV(kv, tableName, excludeFields);
        }

        /// <summary>
        /// 根据表名插入 键值对(如：Hashtable、Dictionary)集合数据 （不需要反射情况下使用）
        /// </summary>
        /// <param name="kv">键值对集合数据</param>
        /// <param name="tableName">表名</param>
        /// <param name="excludeFields">排除字段</param>
        /// <returns>受影响行数>0 为True，否则为False</returns>
        public bool Insert(IDictionary kv, string tableName, string[] excludeFields)
        {
            return InsertKV(kv, tableName, excludeFields);
        }
        private bool InsertKV<KV>(KV kv, string tableName, string[] excludeFields)
        {
            Hashtable ht = new Hashtable();

            if (kv is IDictionary)
            {
                ht = new Hashtable(kv as IDictionary);
            }
            else if (kv is NameValueCollection)
            {
                ht = (kv as NameValueCollection).ToHashtable();
            }

            List<string> lstExcludeField = new List<string>();
            if (excludeFields != null && excludeFields.Length > 0)
            {
                lstExcludeField = excludeFields.Select(t => t.ToLower()).ToList();
            }

            StringBuilder sb_beforeSQL = new StringBuilder();
            sb_beforeSQL.Append("insert into " + tableName + " (");
            StringBuilder sb_afterSQl = new StringBuilder();
            sb_afterSQl.Append(") values (");

            string insert_sql = string.Empty;
            DbParameter[] paras = null;
            List<DbParameter> lstParam = new List<DbParameter>();
            foreach (DictionaryEntry entry in ht)
            {
                string name = entry.Key.ToString().ToLower();
                string parameterName = ParamCharacter + name;
                object objvalue = entry.Value;

                if (lstExcludeField.Count > 0 && lstExcludeField.Contains(name))
                {
                    continue;
                }

                sb_beforeSQL.Append(name + ",");
                sb_afterSQl.Append(parameterName + ",");

                DbParameter para = CreateDbParameter();
                para.ParameterName = parameterName;
                para.Value = (objvalue ?? DBNull.Value);

                if (objvalue != null)
                {
                    para.DbType = DBUtil.GetValueDbType(objvalue, para);
                }
                lstParam.Add(para);
            }
            insert_sql = sb_beforeSQL.ToString().TrimEnd(',');
            insert_sql += sb_afterSQl.ToString().TrimEnd(',') + ")";
            paras = lstParam.ToArray();
            int res = ExecuteSql(insert_sql, paras);
            return res > 0 ? true : false;
        }

        #endregion

        #region 更新

        /// <summary>
        /// 更新 NameValueCollection集合数据
        /// </summary>
        /// <typeparam name="T">对象类型</typeparam>
        /// <param name="model"></param>
        /// <param name="excludeFields"></param>
        /// <param name="uniqueName"></param>
        /// <param name="excludeAttr"></param>
        /// <returns></returns>
        public int Update<T>(NameValueCollection model, string[] excludeFields, string uniqueName = "Id", params Attribute[] excludeAttr)
        {
            return UpdateModel<T>(model, excludeFields, uniqueName, excludeAttr);
        }
        public int Update<T>(IDictionary model, string[] excludeFields, string uniqueName = "Id", params Attribute[] excludeAttr)
        {
            return UpdateModel<T>(model, excludeFields, uniqueName, excludeAttr);
        }
        public int Update<T>(T model, string[] excludeFields, string uniqueName = "Id", params Attribute[] excludeAttr)
        {
            return UpdateModel<T>(model, excludeFields, uniqueName, excludeAttr);
        }
        private int UpdateModel<T>(object model, string[] excludeFields, string uniqueName = "Id", params Attribute[] excludeAttr)
        {
            Type tyTable = typeof(T);
            Hashtable ht = DBUtil.ConvertHashTable<T>(model, excludeFields, excludeAttr);

            StringBuilder sb_beforeSQL = new StringBuilder();
            sb_beforeSQL.Append("update " + tyTable.Name + " set ");

            StringBuilder sb_afterSQl = new StringBuilder();
            sb_afterSQl.Append(" where " + uniqueName.ToLower() + "=" + (ParamCharacter + uniqueName.ToLower()) + "");

            string update_sql = string.Empty;
            DbParameter[] paras = null;
            List<DbParameter> lstParam = new List<DbParameter>();
            foreach (DictionaryEntry kv in ht)
            {
                string name = kv.Key.ToString().ToLower();
                string parameterName = ParamCharacter + name;

                if (!name.Equals(uniqueName, StringComparison.OrdinalIgnoreCase))
                {
                    sb_beforeSQL.Append(kv.Key + "=" + parameterName + ",");
                }

                object objvalue = kv.Value;
                DbParameter para = CreateDbParameter();
                para.ParameterName = parameterName;
                para.Value = (objvalue ?? DBNull.Value);

                if (objvalue != null)
                {
                    para.DbType = DBUtil.GetValueDbType(objvalue, para);
                }
                lstParam.Add(para);
            }
            update_sql = sb_beforeSQL.ToString().TrimEnd(',');
            update_sql += sb_afterSQl.ToString();
            paras = lstParam.ToArray();
            int res = ExecuteSql(update_sql, paras);
            return res;
        }


        public int Update(NameValueCollection model, string tableName, string uniqueName = "Id", params string[] excludeFields)
        {
            return UpdateModel(model, tableName, uniqueName, excludeFields);
        }
        public int Update(IDictionary model, string tableName, string uniqueName = "Id", params string[] excludeFields)
        {
            return UpdateModel(model, tableName, uniqueName, excludeFields);
        }
        private int UpdateModel(object kv, string tableName, string uniqueName = "Id", params string[] excludeFields)
        {
            Hashtable ht = new Hashtable();

            if (kv is IDictionary)
            {
                ht = new Hashtable(kv as IDictionary);
            }
            else if (kv is NameValueCollection)
            {
                ht = (kv as NameValueCollection).ToHashtable();
            }

            List<string> lstExcludeField = new List<string>();
            if (excludeFields != null && excludeFields.Length > 0)
            {
                lstExcludeField = excludeFields.Select(t => t.ToLower()).ToList();
            }

            StringBuilder sb_beforeSQL = new StringBuilder();
            sb_beforeSQL.Append("update " + tableName + " set ");

            StringBuilder sb_afterSQl = new StringBuilder();
            sb_afterSQl.Append(" where " + uniqueName.ToLower() + "=" + (ParamCharacter + uniqueName.ToLower()) + "");

            string update_sql = string.Empty;
            DbParameter[] paras = null;
            List<DbParameter> lstParam = new List<DbParameter>();
            foreach (DictionaryEntry Entry in ht)
            {
                string name = Entry.Key.ToString().ToLower();
                string parameterName = ParamCharacter + name;

                if (lstExcludeField.Count > 0 && lstExcludeField.Contains(name))
                {
                    continue;
                }

                if (!name.Equals(uniqueName, StringComparison.OrdinalIgnoreCase))
                {
                    sb_beforeSQL.Append(Entry.Key + "=" + parameterName + ",");
                }

                object objvalue = Entry.Value;
                DbParameter para = CreateDbParameter();
                para.ParameterName = parameterName;
                para.Value = (objvalue ?? DBNull.Value);

                if (objvalue != null)
                {
                    para.DbType = DBUtil.GetValueDbType(objvalue, para);
                }
                lstParam.Add(para);
            }
            update_sql = sb_beforeSQL.ToString().TrimEnd(',');
            update_sql += sb_afterSQl.ToString();
            paras = lstParam.ToArray();
            int res = ExecuteSql(update_sql, paras);
            return res;
        }
        #endregion



        public int Delete<M>(object value, string uniqueName = "Id")
        {
            string tableName = (typeof(M).Name);
            return Delete(tableName, value, uniqueName);
        }

        public int Delete(string tableName, object value, string uniqueName = "Id")
        {
            string parameterName = (ParamCharacter + uniqueName);
            string del_sql = "delete from " + tableName + " where " + uniqueName + "=" + parameterName;
            DbParameter para = CreateDbParameter();
            para.ParameterName = parameterName;
            para.Value = value;
            return ExecuteSql(del_sql, para);
        }

        public int UpdateBatch<M>(string whereColName, object WhereValue, Hashtable ht)
        {
            StringBuilder sb_beforeSQL = new StringBuilder();
            sb_beforeSQL.Append("update " + (typeof(M)).Name + " set ");

            StringBuilder sb_afterSQl = new StringBuilder();
            sb_afterSQl.Append(" where " + whereColName.ToLower() + "=" + (ParamCharacter + whereColName.ToLower()) + "");

            string update_sql = string.Empty;
            DbParameter[] paras = null;

            List<DbParameter> lstParam = new List<DbParameter>();
            DbParameter wherePara = CreateDbParameter();
            wherePara.ParameterName = (ParamCharacter + whereColName.ToLower());
            wherePara.Value = WhereValue ?? DBNull.Value;
            wherePara.DbType = DBUtil.GetValueDbType(WhereValue, wherePara);

            foreach (DictionaryEntry Entry in ht)
            {
                string name = Entry.Key.ToString().ToLower();
                string parameterName = ParamCharacter + name;

                sb_beforeSQL.Append(Entry.Key + "=" + parameterName + ",");

                object objvalue = Entry.Value;
                DbParameter para = CreateDbParameter();
                para.ParameterName = parameterName;
                para.Value = (objvalue == null ? DBNull.Value : objvalue);

                if (objvalue != null)
                {
                    para.DbType = DBUtil.GetValueDbType(objvalue, para);
                }
                lstParam.Add(para);
            }
            update_sql = sb_beforeSQL.ToString().TrimEnd(',');
            update_sql += sb_afterSQl.ToString();
            paras = lstParam.ToArray();
            int res = ExecuteSql(update_sql, paras);
            return res;
        }

        public DataTable GetDataTableByPager(int pageSize, int currentPage, string fields, string orderString, string whereString, string tablename, out int totalCount)
        {
            totalCount = 0;
            return null;
        }
        public List<T> ConvertToListObject<T>(string strSql, params DbParameter[] paras)
        {
            return QueryTable(strSql, 30, paras).ConvertToListObject<T>();
        }

        public List<string> GetListColumn<T>(string columnName, bool isDistinct)
        {
            string tableName = typeof(T).Name;
            string sel_sql = "select " + (isDistinct ? "distinct" : "") + columnName + " from " + tableName;
            return QueryTable(sel_sql).GetFirstCol<string>();
        }
        public List<string> GetListColumn(string sel_Column_Sql)
        {
            return QueryTable(sel_Column_Sql).GetFirstCol<string>();
        }

        public List<KeyValuePair<string, string>> GetListKeyValue<T>(string colKeyName, string colValName, string joinWhere = "")
        {
            string tableName = typeof(T).Name;
            string sel_sel = "select distinct a." + colKeyName + " as text,a." + colValName + " as id" + " from " + tableName + " a " + joinWhere;
            return QueryTable(sel_sel).ConvertToListObject<KeyValuePair<string, string>>();
        }
    }
}

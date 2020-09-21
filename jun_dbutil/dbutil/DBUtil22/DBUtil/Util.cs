using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;

namespace DBUtil
{
    public static class DBUtil
    {


        public static DbType GetValueDbType(object objvalue, DbParameter para)
        {
            Type tyval = objvalue.GetType();
            Type typeVal = Nullable.GetUnderlyingType(tyval) ?? tyval;

            DbType dbType = DbType.AnsiString;
            DateTime dtime;
            Guid gid;
            if (typeVal.IsEnum && Enum.GetUnderlyingType(typeVal) == typeof(byte))
            {
                dbType = DbType.Int32;
            }
            else if (typeVal == typeof(byte[]))
            {
                dbType = DbType.Binary;
            }
            else if (DateTime.TryParse(objvalue.ToString(), out dtime)
               && !Regex.IsMatch(objvalue.ToString(), @"^\d{4}[.-/]{1}\d{1,2}$"))
            {
                dbType = DbType.DateTime;
            }
            else if (Guid.TryParse(objvalue.ToString(), out gid))
            {
                dbType = DbType.String;
                para.Value = objvalue.ToString();
            }
            else
            {

            }
            return dbType;
        }

        public static Hashtable ConvertHashTable<T>(object KV, string[] excludeFields, params Attribute[] excludeAttr)
        {
            Hashtable ht = new Hashtable();

            List<string> lstExcludeField = new List<string>();
            if (excludeFields != null && excludeFields.Length > 0)
            {
                lstExcludeField = excludeFields.Select(t => t.ToLower()).ToList();
            }
            List<string> lstAllField = new List<string>();
            Type tyTable = typeof(T);
            PropertyInfo[] pyInfos = tyTable.GetProperties(BindingFlags.NonPublic | BindingFlags.Instance | BindingFlags.Public | BindingFlags.DeclaredOnly);
            foreach (PropertyInfo pInfo in pyInfos)
            {
                if (excludeAttr != null && excludeAttr.Length > 0)
                {
                    bool isBK = false;
                    Attribute[] attrs = pInfo.GetCustomAttributes(true) as Attribute[];
                    if (attrs != null && attrs.Length > 0)
                    {
                        foreach (var attr in attrs)
                        {
                            foreach (var item in excludeAttr)
                            {
                                if (attr.GetType() == item.GetType())
                                {
                                    isBK = true;
                                    break;
                                }
                            }
                            if (isBK)
                            {
                                break;
                            }
                        }
                        if (isBK)
                        {
                            continue;
                        }
                    }
                }
                string name = pInfo.Name.ToString().ToLower();
                object objvalue = null;

                if (KV is T && !lstExcludeField.Contains(name))
                {
                    objvalue = pInfo.GetValue((T)KV, null);
                    ht.Add(name, objvalue);
                }

                //存储对象 所有小写形式的属性
                lstAllField.Add(name);
            }

            if (KV is IDictionary)
            {
                IDictionary dict = ((IDictionary)KV);
                foreach (DictionaryEntry entry in dict)
                {
                    string name = entry.Key.ToString().ToLower();
                    if (lstAllField.Contains(name) && !lstExcludeField.Contains(name))
                    {
                        ht.Add(name, entry.Value);
                    }
                }
            }
            else if (KV is NameValueCollection)
            {
                NameValueCollection nvc = KV as NameValueCollection;
                foreach (string key in nvc.AllKeys)
                {
                    string name = key.ToLower();
                    if (lstAllField.Contains(name) && !lstExcludeField.Contains(name))
                    {
                        ht.Add(name, nvc[key]);
                    }
                }
            }

            return ht;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="data"></param>
        /// <param name="columnName"></param>
        /// <returns></returns>
        public static List<T> GetFirstCol<T>(this DataTable data, string columnName = "")
        {
            List<T> lst = new List<T>();
            if (data == null || data.Rows.Count <= 0)
            {
                return lst;
            }
            else
            {
                foreach (DataRow dr in data.Rows)
                {
                    if (!string.IsNullOrWhiteSpace(columnName))
                    {
                        if (dr[columnName] != null)
                        {
                            T t = (T)Convert.ChangeType(dr[columnName], typeof(T));
                            lst.Add(t);
                        }
                    }
                    else
                    {
                        if (dr[0] != null)
                        {
                            T t = (T)Convert.ChangeType(dr[0], typeof(T));
                            lst.Add(t);
                        }
                    }
                }
            }
            return lst;
        }


        public static Dictionary<Key, Val> GetDict<Key, Val>(this DataTable data, string ColumnNameKey, string ColumnVal)
        {
            Dictionary<Key, Val> dict = new Dictionary<Key, Val>();
            if (data == null || data.Rows.Count <= 0)
            {
                return new Dictionary<Key, Val>();
            }
            else
            {
                foreach (DataRow dr in data.Rows)
                {
                    Key k = (Key)Convert.ChangeType(dr[ColumnNameKey], typeof(Key));
                    Val v = (Val)Convert.ChangeType(dr[ColumnVal], typeof(Val));
                    dict[key] = v;
                    //dict.Add(k, v);
                }
            }
            return dict;
        }


        public static string SQLIn<T>(string field, bool isNot = false, params T[] members)
        {
            string result = string.Empty;
            if (members == null || members.Length <= 0)
            {
                return string.Empty;
            }
            List<string> lst = new List<string>();
            foreach (T obj in members)
            {

                if (obj != null)
                {
                    string val = obj.ToString();
                    if (val.Contains("'"))
                    {
                        continue;
                    }
                    lst.Add("'" + obj.ToString() + "'");
                }
            }
            result = " and " + field + " " + (isNot ? "not" : "") + " in (" + string.Join(",", lst) + ") ";
            return result;
        }

        public static Hashtable ToHashtable(this NameValueCollection param)
        {
            Hashtable ht = new Hashtable();
            foreach (string key in param.AllKeys)
            {
                string name = key;
                string value = param[key];
                ht.Add(name, value);
            }
            return ht;
        }


        /// <summary>
        /// 将数据库中的值转换为对象
        /// </summary>
        /// <param name="obj"></param>
        /// <param name="dt"></param>
        /// <returns></returns>
        public static T ConvertToObjectFromDB<T>(T obj, DataRow row)
        {
            Type type = obj.GetType();
            System.Reflection.PropertyInfo[] propInfo = type.GetProperties();
            for (int i = 0; i < propInfo.Length; i++)
            {
                if (row.Table.Columns[propInfo[i].Name] != null && row[propInfo[i].Name] != System.DBNull.Value)
                {
                    object objVal = row[propInfo[i].Name];
                    Type typeVal = Nullable.GetUnderlyingType(propInfo[i].PropertyType) ?? propInfo[i].PropertyType;
                    int mark = 0;
                    try
                    {
                        if (typeVal.Name == "Guid")
                        {
                            mark = 1;
                            propInfo[i].SetValue(obj, Guid.Parse(objVal.ToString()), null);
                        }
                        else
                        {
                            if (typeVal.IsEnum && objVal != null)
                            {
                                Type tyEnum = Enum.GetUnderlyingType(typeVal);
                                if (tyEnum.IsAssignableFrom(typeof(int)))
                                {
                                    mark = 2;
                                    propInfo[i].SetValue(obj, Enum.Parse(typeVal, objVal.ToString()), null);
                                }
                                else
                                {
                                    mark = 3;
                                    propInfo[i].SetValue(obj, Convert.ChangeType(objVal, typeVal), null);
                                }
                            }
                            else
                            {
                                if (objVal == null || string.IsNullOrWhiteSpace(objVal.ToString()))
                                {
                                    mark = 4;
                                    if (IsNullableType(propInfo[i].PropertyType))
                                    {
                                        objVal = null;
                                    }
                                    propInfo[i].SetValue(obj, objVal, null);
                                }
                                else
                                {
                                    mark = 5;
                                    propInfo[i].SetValue(obj, Convert.ChangeType(objVal, typeVal), null);
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        throw new ArgumentException("SetValue出错！(" + mark + ")", propInfo[i].Name + ":" + objVal, ex);
                    }
                }
            }
            return obj;
        }

        private static bool IsNullableType(Type theType)
        {
            return (theType.IsGenericType && theType.
              GetGenericTypeDefinition().Equals
              (typeof(Nullable<>)));
        }

        /// <summary>
        /// 将datatable转换为List对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="type"></param>
        /// <param name="dt"></param>
        /// <returns></returns>
        public static List<T> ConvertToListObject<T>(this DataTable dt)
        {

            List<T> objs = new List<T>();
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                T obj = (T)Activator.CreateInstance(typeof(T));
                obj = ConvertToObjectFromDB(obj, dt.Rows[i]);
                objs.Add(obj);
            }
            return objs;
        }

        /// <summary>
        /// 创建指定 列名字的数据表
        /// </summary>
        /// <param name="columnNames">数据列名</param>
        /// <returns></returns>
        public static DataTable CreateDataTable(params string[] columnNames)
        {
            DataTable data = new DataTable();
            if (columnNames != null && columnNames.Length > 0)
            {
                data.Columns.AddRange(columnNames.Select(t => new DataColumn(t)).ToArray());
            }
            return data;
        }


        //public static List<T> ReaderToList<T>(IDataReader objReader)
        //{
        //    using (objReader)
        //    {
        //        var list = new List<T>();
        //        var modelType = typeof(T);
        //        while (objReader.Read())
        //        {
        //            var model = Activator.CreateInstance<T>();
        //            for (var i = 0; i < objReader.FieldCount; i++)
        //            //{
        //            //    if (!IsNullOrDbNull(objReader[i]))
        //            //    {
        //            //        var pi = modelType.GetProperty(objReader.GetName(i), BindingFlags.GetProperty | BindingFlags.Public | BindingFlags.Instance | BindingFlags.IgnoreCase);
        //            //        if (pi != null)
        //            //        {
        //            //            pi.SetValue(model, CheckType(objReader[i], pi.PropertyType), null);
        //            //        }
        //            //    }
        //            }
        //            list.Add(model);
        //        }
        //        return list;
        //    }
        //}



    }
}

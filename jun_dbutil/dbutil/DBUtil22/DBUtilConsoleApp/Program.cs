using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Linq;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Web.Script.Serialization;
using DBUtil;

namespace DBUtilConsoleApp
{
    class Program
    {
        static void Main(string[] args)
        {
            DBSession db = new DBSession(DataBaseType.MsSql);
            string sel_sql = string.Empty;
            DataTable dt = new DataTable();


            //Test();



        }

        static void SWatch(Action act)
        {
            Stopwatch wat = new Stopwatch();
            wat.Start();
            act();
            wat.Stop();
            long lng = wat.ElapsedMilliseconds;
            
            Console.WriteLine(lng);
        }

        long SWatch(Action<object> act, object o)
        {
            Stopwatch wat = new Stopwatch();
            wat.Start();
            act(o);
            wat.Stop();
            return wat.ElapsedMilliseconds;
        }

        static void Test()
        {
            DBSession db = new DBSession(DataBaseType.MsSql);
            string sel_sql = string.Empty;
            DataTable dt = new DataTable();

            //sel_sql = "select top 10 * from product order by id desc";
            //dt = db.QueryTable(sel_sql);


            //db = new DBSession(DataBaseType.MySql);
            //sel_sql = "select * from product  order by id desc  limit  1,10 ";
            //dt = db.QueryTable(sel_sql);

            //db = new DBSession(DataBaseType.Oracle);
            //sel_sql = "Select  * From Cc_Qa Where Rownum<=10 Order By added Desc ";
            //dt = db.QueryTable(sel_sql);

            //db = new DBSession(DataBaseType.OracleManaged);
            //sel_sql = "Select  * From Cc_Qa Where Rownum<=10 Order By added Desc ";
            //dt = db.QueryTable(sel_sql);


            //db = new DBSession(DataBaseType.Sqlite);
            //sel_sql = "select * from base_datalog order by opertime desc limit 1,10";
            //dt = db.QueryTable(sel_sql);

            //db = new DBSession(DataBaseType.Oledb);
            //sel_sql = "Select  * From product  Order By settime Desc ";
            //dt = db.QueryTable(sel_sql);
        }

    }
}

Oracle中TO_DATE格式2009-04-14 10:53TO_DATE格式(以时间:2007-11-02   13:45:25为例) 
   
        Year:      
        yy two digits 两位年                显示值:07 
        yyy three digits 三位年                显示值:007 
        yyyy four digits 四位年                显示值:2007 
            
        Month:      
        mm    number     两位月              显示值:11 
        mon    abbreviated 字符集表示          显示值:11月,若是英文版,显示nov     
        month spelled out 字符集表示          显示值:11月,若是英文版,显示november 
          
        Day:      
        dd    number         当月第几天        显示值:02 
        ddd    number         当年第几天        显示值:02 
        dy    abbreviated 当周第几天简写    显示值:星期五,若是英文版,显示fri 
        day    spelled out   当周第几天全写    显示值:星期五,若是英文版,显示friday        
        ddspth spelled out, ordinal twelfth 
             
              Hour: 
              hh    two digits 12小时进制            显示值:01 
              hh24 two digits 24小时进制            显示值:13 
              
              Minute: 
              mi    two digits 60进制                显示值:45 
              
              Second: 
              ss    two digits 60进制                显示值:25 
              
              其它 
              Q     digit         季度                  显示值:4 
              WW    digit         当年第几周            显示值:44 
              W    digit          当月第几周            显示值:1 
              
        24小时格式下时间范围为： 0:00:00 - 23:59:59....      
        12小时格式下时间范围为： 1:00:00 - 12:59:59 .... 
            
1. 日期和字符转换函数用法（to_date,to_char） 
         
select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') as nowTime from dual;   //日期转化为字符串   
select to_char(sysdate,'yyyy') as nowYear   from dual;   //获取时间的年   
select to_char(sysdate,'mm')    as nowMonth from dual;   //获取时间的月   
select to_char(sysdate,'dd')    as nowDay    from dual;   //获取时间的日   
select to_char(sysdate,'hh24') as nowHour   from dual;   //获取时间的时   
select to_char(sysdate,'mi')    as nowMinute from dual;   //获取时间的分   
select to_char(sysdate,'ss')    as nowSecond from dual;   //获取时间的秒 
    
select to_date('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')    from dual//
2.      
    select to_char( to_date(222,'J'),'Jsp') from dual      
    
    显示Two Hundred Twenty-Two    
3.求某天是星期几      
   select to_char(to_date('2002-08-26','yyyy-mm-dd'),'day') from dual;      
   星期一      
   select to_char(to_date('2002-08-26','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = American') from dual;      
   monday      
   设置日期语言      
   ALTER SESSION SET NLS_DATE_LANGUAGE='AMERICAN';      
   也可以这样      
   TO_DATE ('2002-08-26', 'YYYY-mm-dd', 'NLS_DATE_LANGUAGE = American')    
4. 两个日期间的天数      
    select floor(sysdate - to_date('20020405','yyyymmdd')) from dual;    
5. 时间为null的用法      
   select id, active_date from table1      
   UNION      
   select 1, TO_DATE(null) from dual;      
   
   注意要用TO_DATE(null)    
6.月份差   
   a_date between to_date('20011201','yyyymmdd') and to_date('20011231','yyyymmdd')      
   那么12月31号中午12点之后和12月1号的12点之前是不包含在这个范围之内的。      
   所以，当时间需要精确的时候，觉得to_char还是必要的 
      
7. 日期格式冲突问题      
    输入的格式要看你安装的ORACLE字符集的类型, 比如: US7ASCII, date格式的类型就是: '01-Jan-01'      
    alter system set NLS_DATE_LANGUAGE = American      
    alter session set NLS_DATE_LANGUAGE = American      
    或者在to_date中写      
    select to_char(to_date('2002-08-26','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = American') from dual;      
    注意我这只是举了NLS_DATE_LANGUAGE，当然还有很多，      
    可查看      
    select * from nls_session_parameters      
    select * from V$NLS_PARAMETERS    
8.      
   select count(*)      
   from ( select rownum-1 rnum      
       from all_objects      
       where rownum <= to_date('2002-02-28','yyyy-mm-dd') - to_date('2002-      
       02-01','yyyy-mm-dd')+1      
      )      
   where to_char( to_date('2002-02-01','yyyy-mm-dd')+rnum-1, 'D' )      
        not in ( '1', '7' )      
   
   查找2002-02-28至2002-02-01间除星期一和七的天数      
   在前后分别调用DBMS_UTILITY.GET_TIME, 让后将结果相减(得到的是1/100秒, 而不是毫秒).    
9. 查找月份     
    select months_between(to_date('01-31-1999','MM-DD-YYYY'),to_date('12-31-1998','MM-DD-YYYY')) "MONTHS" FROM DUAL;      
    1      
   select months_between(to_date('02-01-1999','MM-DD-YYYY'),to_date('12-31-1998','MM-DD-YYYY')) "MONTHS" FROM DUAL;      
    1.03225806451613 
       
10. Next_day的用法      
    Next_day(date, day)      
    
    Monday-Sunday, for format code DAY      
    Mon-Sun, for format code DY      
    1-7, for format code D    
11      
   select to_char(sysdate,'hh:mi:ss') TIME from all_objects      
   注意：第一条记录的TIME 与最后一行是一样的      
   可以建立一个函数来处理这个问题      
   create or replace function sys_date return date is      
   begin      
   return sysdate;      
   end;      
   
   select to_char(sys_date,'hh:mi:ss') from all_objects;   
     
12.获得小时数      
     extract()找出日期或间隔值的字段值 
    SELECT EXTRACT(HOUR FROM TIMESTAMP '2001-02-16 2:38:40') from offer      
    SQL> select sysdate ,to_char(sysdate,'hh') from dual;      
    
    SYSDATE TO_CHAR(SYSDATE,'HH')      
    -------------------- ---------------------      
    2003-10-13 19:35:21 07      
    
    SQL> select sysdate ,to_char(sysdate,'hh24') from dual;      
    
    SYSDATE TO_CHAR(SYSDATE,'HH24')      
    -------------------- -----------------------      
    2003-10-13 19:35:21 19    
       
13.年月日的处理      
   select older_date,      
       newer_date,      
       years,      
       months,      
       abs(      
        trunc(      
         newer_date-      
         add_months( older_date,years*12+months )      
        )      
       ) days 
       
   from ( select      
        trunc(months_between( newer_date, older_date )/12) YEARS,      
        mod(trunc(months_between( newer_date, older_date )),12 ) MONTHS,      
        newer_date,      
        older_date      
        from ( 
              select hiredate older_date, add_months(hiredate,rownum)+rownum newer_date      
              from emp 
             )      
      )    
14.处理月份天数不定的办法      
   select to_char(add_months(last_day(sysdate) +1, -2), 'yyyymmdd'),last_day(sysdate) from dual    
16.找出今年的天数      
   select add_months(trunc(sysdate,'year'), 12) - trunc(sysdate,'year') from dual    
   闰年的处理方法      
   to_char( last_day( to_date('02'    | | :year,'mmyyyy') ), 'dd' )      
   如果是28就不是闰年    
17.yyyy与rrrr的区别      
   'YYYY99 TO_C      
   ------- ----      
   yyyy 99 0099      
   rrrr 99 1999      
   yyyy 01 0001      
   rrrr 01 2001    
18.不同时区的处理      
   select to_char( NEW_TIME( sysdate, 'GMT','EST'), 'dd/mm/yyyy hh:mi:ss') ,sysdate      
   from dual;    
19.5秒钟一个间隔      
   Select TO_DATE(FLOOR(TO_CHAR(sysdate,'SSSSS')/300) * 300,'SSSSS') ,TO_CHAR(sysdate,'SSSSS')      
   from dual    
   2002-11-1 9:55:00 35786      
   SSSSS表示5位秒数    
20.一年的第几天      
   select TO_CHAR(SYSDATE,'DDD'),sysdate from dual 
        
   310 2002-11-6 10:03:51    
21.计算小时,分,秒,毫秒      
    select      
     Days,      
     A,      
     TRUNC(A*24) Hours,      
     TRUNC(A*24*60 - 60*TRUNC(A*24)) Minutes,      
     TRUNC(A*24*60*60 - 60*TRUNC(A*24*60)) Seconds,      
     TRUNC(A*24*60*60*100 - 100*TRUNC(A*24*60*60)) mSeconds      
    from      
    (      
     select      
     trunc(sysdate) Days,      
     sysdate - trunc(sysdate) A      
     from dual      
   )    

   select * from tabname      
   order by decode(mode,'FIFO',1,-1)*to_char(rq,'yyyymmddhh24miss');      
   
   //      
   floor((date2-date1) /365) 作为年      
   floor((date2-date1, 365) /30) 作为月      
   d(mod(date2-date1, 365), 30)作为日.
23.next_day函数      返回下个星期的日期,day为1-7或星期日-星期六,1表示星期日 
   next_day(sysdate,6)是从当前开始下一个星期五。后面的数字是从星期日开始算起。      
   1 2 3 4 5 6 7      
   日 一 二 三 四 五 六    
   
   --------------------------------------------------------------- 
   
   select    (sysdate-to_date('2003-12-03 12:55:45','yyyy-mm-dd hh24:mi:ss'))*24*60*60 from ddual 
   日期 返回的是天 然后 转换为ss 
     
24,round[舍入到最接近的日期](day:舍入到最接近的星期日) 
   select sysdate S1, 
   round(sysdate) S2 , 
   round(sysdate,'year') YEAR, 
   round(sysdate,'month') MONTH , 
   round(sysdate,'day') DAY from dual
25,trunc[截断到最接近的日期,单位为天] ,返回的是日期类型 
   select sysdate S1,                     
     trunc(sysdate) S2,                 //返回当前日期,无时分秒 
     trunc(sysdate,'year') YEAR,        //返回当前年的1月1日,无时分秒 
     trunc(sysdate,'month') MONTH ,     //返回当前月的1日,无时分秒 
     trunc(sysdate,'day') DAY           //返回当前星期的星期天,无时分秒 
   from dual
26,返回日期列表中最晚日期 
   select greatest('01-1月-04','04-1月-04','10-2月-04') from dual
27.计算时间差 
     注:oracle时间差是以天数为单位,所以换算成年月,日 
     
      select floor(to_number(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))/365) as spanYears from dual       
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-年 
      select ceil(moths_between(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))) as spanMonths from dual       
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-月 
      select floor(to_number(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))) as spanDays from dual            
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-天 
      select floor(to_number(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))*24) as spanHours from dual        
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-时 
      select floor(to_number(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))*24*60) as spanMinutes from dual   
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-分 
      select floor(to_number(sysdate-to_date('2007-11-02 15:55:03','yyyy-mm-dd hh24:mi:ss'))*24*60*60) as spanSeconds from dual
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//时间差-秒
28.更新时间 
     注:oracle时间加减是以天数为单位,设改变量为n,所以换算成年月,日 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate+n*365,'yyyy-mm-dd hh24:mi:ss') as newTime from dual       
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//改变时间-年 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),add_months(sysdate,n) as newTime from dual                //改变时间-月 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate+n,'yyyy-mm-dd hh24:mi:ss') as newTime from dual           
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//改变时间-日 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate+n/24,'yyyy-mm-dd hh24:mi:ss') as newTime from dual        
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//改变时间-时 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate+n/24/60,'yyyy-mm-dd hh24:mi:ss') as newTime from dual     
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//改变时间-分 
     select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate+n/24/60/60,'yyyy-mm-dd hh24:mi:ss') as newTime from dual  
　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　//改变时间-秒
29.查找月的第一天,最后一天 
     SELECT Trunc(Trunc(SYSDATE, 'MONTH') - 1, 'MONTH') First_Day_Last_Month, 
       Trunc(SYSDATE, 'MONTH') - 1 / 86400 Last_Day_Last_Month, 
       Trunc(SYSDATE, 'MONTH') First_Day_Cur_Month, 
       LAST_DAY(Trunc(SYSDATE, 'MONTH')) + 1 - 1 / 86400 Last_Day_Cur_Month 
   FROM dual; 
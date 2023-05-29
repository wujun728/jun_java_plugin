select * from T_DATE t

select add_months(d1,2) from t_date where id=1;

select last_day(d1) from t_date where id=1;

update t_date set d3=to_date('2016-12-20','YYYY-MM-DD') where id=1;

update t_date set d3=to_date('2016-12-20 18:31:34','YYYY-MM-DD HH24:MI:SS') where id=1;

select months_between(d1,d3) from t_date where id=1;

select next_day(d1,2) from t_date where id=1;

select trunc(d1,'YYYY') from t_date where id=1;
select trunc(d1,'MM') from t_date where id=1;
select trunc(d1,'DD') from t_date where id=1;
select trunc(d1,'HH') from t_date where id=1;
select trunc(d1,'MI') from t_date where id=1;


select extract(year from sysdate) from dual;
select extract(month from sysdate) from dual;
select extract(day from sysdate) from dual;
select extract(Hour from systimestamp) from dual;
select extract(minute from systimestamp) from dual;
select extract(second from systimestamp) from dual;

select to_char(d1,'YYYY-MM-DD') from t_date where id=1;
select to_char(d1,'YYYY-MM-DD HH24:MI:SS') from t_date where id=1;
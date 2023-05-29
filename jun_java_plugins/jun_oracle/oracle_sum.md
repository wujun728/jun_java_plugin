select max(sal)  from emp ;
select min(sal)  from emp ;
select avg(sal)  from emp ;
select sum(sal)  from emp ;
select count(ename)  from emp ;

select ename,nvl(sal,0) from emp;

select e.*,rownum from emp e;

 select * from (select a.*,rownum rn from (select * from emp) a where rownum<=10) where rn>5;
 
 select 2+1 from dual;
select 2-1 from dual;
select 2*1 from dual;
select 2/1 from dual;

select * from emp where sal between 900 and 1500;

select * from emp where sal>=900 and sal<=1500;

select distinct ename from bonus

select ename,hiredate from emp where ename in (select distinct ename from bonus)

select * from emp where ename like '%M%'
select * from emp where ename like 'M%'
select * from emp where ename like '_M%'
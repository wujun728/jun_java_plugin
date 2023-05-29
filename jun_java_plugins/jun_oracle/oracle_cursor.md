set serverout on;
declare cursor cu_emp is select empno,ename,sal from emp;
e_no number;
e_name varchar2(10);
e_sal number;
begin
  open cu_emp;
  fetch cu_emp into e_no,e_name,e_sal;
  while cu_emp%found loop
    dbms_output.put_line('编号：'||e_no||'，姓名：'||e_name||'，基本薪资：'||e_sal);
    fetch cu_emp into e_no,e_name,e_sal;
  end loop;
  close cu_emp;
end;


set serverout on;
declare cursor cu_emp is select empno,ename,sal from emp;
e_no emp.empno%type;
e_name emp.ename%type;
e_sal emp.sal%type;
begin
  open cu_emp;
  fetch cu_emp into e_no,e_name,e_sal;
  while cu_emp%found loop
    dbms_output.put_line('编号：'||e_no||'，姓名：'||e_name||'，基本薪资：'||e_sal);
    fetch cu_emp into e_no,e_name,e_sal;
  end loop;
  close cu_emp;
end;

set serverout on;
declare cursor cu_emp is select * from emp;
e emp%rowtype;
begin
  open cu_emp;
  fetch cu_emp into e;
  while cu_emp%found loop
    dbms_output.put_line('编号：'||e.empno||'，姓名：'||e.ename||'，基本薪资：'||e.sal);
    fetch cu_emp into e;
  end loop;
  close cu_emp;
end;


set serverout on;
declare cursor cu_emp is select * from emp where sal>2000 and sal<3000;
e emp%rowtype;
begin
  open cu_emp;
  fetch cu_emp into e;
  while cu_emp%found loop
    dbms_output.put_line('编号：'||e.empno||'，姓名：'||e.ename||'，基本薪资：'||e.sal);
    fetch cu_emp into e;
  end loop;
  close cu_emp;
end;

begin
  if sql%isopen then
     dbms_output.put_line('sql游标已打开');
  else
     dbms_output.put_line('sql游标未打开'); 
  end if;
end;

declare e_count number;
begin
  select count(*) into e_count from emp;
  dbms_output.put_line('游标捕获的记录数：'||sql%rowcount); 
end;

declare e_count number;
begin
  select count(*) into e_count from emp;
  dbms_output.put_line('游标捕获的记录数：'||sql%rowcount); 
end;


begin
  update emp set ename='sb3' where empno=111;
  if sql%rowcount=1 then
    dbms_output.put_line('已更新');
  else
    dbms_output.put_line('未更新');
  end if;
end;

begin
  update emp set ename='sb3' where empno=111;
  if sql%found then
    dbms_output.put_line('已更新');
  else
    dbms_output.put_line('未更新');
  end if;
end;

declare type emptype is ref cursor return emp%rowtype;
cu_emp emptype;
e_count number;
e emp%rowtype;
begin
  select count(*) into e_count from emp where job='PRESIDENT1';
  if e_count=0 then
    open cu_emp for select * from emp;
  else
    open cu_emp for select * from emp where job='PRESIDENT';
  end if;
  fetch cu_emp into e;
  while cu_emp%found loop
    dbms_output.put_line('编号：'||e.empno||'，姓名：'||e.ename||'，基本薪资：'||e.sal);
    fetch cu_emp into e;
  end loop;
  close cu_emp;
end;


declare type customType is ref cursor;
e_count number;
e emp%rowtype;
s salgrade%rowType;
cType customType;
begin
  select count(*) into e_count from emp where job='PRESIDENT1';
  if e_count=0 then
    open cType for select * from salgrade;
    fetch cType into s;
    while cType%found loop
      dbms_output.put_line('等级：'||s.grade||'，最低薪资：'||s.losal||'，最高薪资：'||s.hisal);
      fetch cType into s;
    end loop;
    close cType;
  else
    open cType for select * from emp where job='PRESIDENT';
    fetch cType into e;
    while cType%found loop
      dbms_output.put_line('编号：'||e.empno||'，姓名：'||e.ename||'，基本薪资：'||e.sal);
      fetch cType into e;
    end loop;
    close cType;
  end if;
end;
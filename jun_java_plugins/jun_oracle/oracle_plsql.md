set serverout on;
declare n number:=1;
        v varchar2(20):='world';
begin
   dbms_output.put_line('hello'||n||v);
end;


set serverout on;
declare emp_count number;
begin
  select count(*) into emp_count from emp where sal>=3000;
  if emp_count>0 then
    dbms_output.put_line('有'||emp_count||'个员工的基本薪资大于等于3000');
  else
    dbms_output.put_line('没有员工的基本薪资大于等于3000'); 
  end if;
end;


set serverout on;
declare emp_count number;
begin
  select count(*) into emp_count from emp where sal>=3000;
  if emp_count=1 then
    dbms_output.put_line('有1个员工的基本薪资大于等于3000');
  else if emp_count>1 then
    dbms_output.put_line('有超过1个员工的基本薪资大于等于3000');
  else
    dbms_output.put_line('没有员工的基本薪资大于等于3000'); 
  end if;
  end if;
end;

set serverout on;
declare emp_count number;
begin
  select count(*) into emp_count from emp where sal>=3000;
  case emp_count
    when 0 then dbms_output.put_line('没有员工的基本薪资大于等于3000');
    when 1 then dbms_output.put_line('有1个员工的基本薪资大于等于3000');
    when 2 then dbms_output.put_line('有2个员工的基本薪资大于等于3000');
    when 3 then dbms_output.put_line('有3个员工的基本薪资大于等于3000');
    else dbms_output.put_line('超过3个员工的基本薪资大于等于3000');
  end case;
end;

set serverout on;
declare g_id number:=2;
        g_losal number;
        g_hisal number;
begin
  loop
    if(g_id>4) then
      exit;
    end if;
    
    select losal,hisal into g_losal,g_hisal from salgrade where grade=g_id;
    dbms_output.put_line(g_id || '等级的最低薪资'|| g_losal || '，最高薪资：' || g_hisal);
    
    g_id:=g_id+1;
    
  end loop;
end;


set serverout on;
declare g_id number:=2;
        g_losal number;
        g_hisal number;
begin

  while g_id<5 loop
  
     select losal,hisal into g_losal,g_hisal from salgrade where grade=g_id;
     dbms_output.put_line(g_id || '等级的最低薪资'|| g_losal || '，最高薪资：' || g_hisal);   
     g_id:=g_id+1;
  end loop;

end;


set serverout on;
declare g_losal number;
        g_hisal number;
begin
  for g_id in 2..4 loop
    select losal,hisal into g_losal,g_hisal from salgrade where grade=g_id;
    dbms_output.put_line(g_id || '等级的最低薪资'|| g_losal || '，最高薪资：' || g_hisal);   
  end loop;
end;
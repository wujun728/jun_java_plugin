create function getBookCount return number as
begin
  declare book_count number;
  begin
    select count(*) into book_count from t_book;
    return book_count;
  end;
end getBookCount;


set serveroutput on;
begin
  dbms_output.put_line('表t_book有'|| getBookCount() ||'条数据');
end;


create function getTableCount(table_name varchar2) return number as
begin
  declare recore_count number;
  query_sql varchar2(300);
  begin
    query_sql:='select count(*) from ' || table_name;
    execute immediate query_sql into recore_count;
    return recore_count;
  end;
end getTableCount;


begin
  dbms_output.put_line('表有'|| getTableCount('t_bookType') ||'条数据');
end;


create procedure addBook(bookName in varchar2,typeId in number) as
begin
  declare maxId number;
  begin
    select max(id) into maxId from t_book;
    insert into t_book values(maxId+1,bookName,typeId);
    commit;
  end;
end addBook;

execute addBook('java好东西',1);


create procedure addBook2(bN in varchar2,typeId in number) as
begin
  declare maxId number;
  n number;
  begin
    select count(*) into n from t_book where bookName=bN;
    if(n>0) then
     return;
    end if;
    select max(id) into maxId from t_book;
    insert into t_book values(maxId+1,bN,typeId);
    commit;
  end;
end addBook2;

execute addBook2('java好东西33',1);


create procedure addBook3(bN in varchar2,typeId in number,n1 out number,n2 out number) as
begin
  declare maxId number;
  n number;
  begin
    select count(*) into n1 from t_book;
    select count(*) into n from t_book where bookName=bN;
    if(n>0) then
     return;
    end if;
    select max(id) into maxId from t_book;
    insert into t_book values(maxId+1,bN,typeId);
    select count(*) into n2 from t_book;
    commit;
  end;
end addBook3;

declare n1 number;
        n2 number;
begin
  addBook3('喝喝33223',2,n1,n2);
  dbms_output.put_line('n1='||n1);
  dbms_output.put_line('n2='||n2);
end;
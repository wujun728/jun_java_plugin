create package pkg_book as
   function getbookcount return number;
   function getTableCount(table_name varchar2) return number;
   procedure addBook(bookName in varchar2,typeId in number);
end pkg_book;


create package body pkg_book as

       function getBookCount return number as
          begin
            declare book_count number;
            begin
              select count(*) into book_count from t_book;
              return book_count;
            end;
        end getBookCount;
        
        
        function getTableCount(table_name varchar2) return number as
           begin
              declare recore_count number;
              query_sql varchar2(300);
              begin
                query_sql:='select count(*) from ' || table_name;
                execute immediate query_sql into recore_count;
                return recore_count;
              end;
          end getTableCount;
          
          
          procedure addBook(bookName in varchar2,typeId in number) as
            begin
              declare maxId number;
              begin
                select max(id) into maxId from t_book;
                insert into t_book values(maxId+1,bookName,typeId);
                commit;
              end;
            end addBook;


end pkg_book;



set serveroutput on;
begin
  dbms_output.put_line('表t_book有'|| pkg_book.getBookCount() ||'条数据');
end;



create user TEST identified by 123456 default tablespace users;

grant create session to TEST;

alter user TEST account lock;

alter user TEST account unlock;

alter user TEST identified by 123;

drop user TEST cascade;
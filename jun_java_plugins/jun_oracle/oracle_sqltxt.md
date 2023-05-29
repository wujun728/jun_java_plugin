select t.*, t.rowid from T_BOOK t

insert into t_book values(6,'xx7',2);

delete from t_book where id=6;

update t_book set bookname='xx4' where id=4;



create trigger tr_book
before insert
on t_book
begin
  if user!='cc' then
    raise_application_error(-20001,'权限不足');
  end if;
end;


create trigger tr_book2
before update or delete
on t_book
begin
  if user!='CC' then
    raise_application_error(-20001,'权限不足');
  end if;
end;

create trigger tr_book_log
after insert or update or delete
on t_book
begin
  if updating then
    insert into t_book_log values(user,'update',sysdate);
  else if inserting then
    insert into t_book_log values(user,'insert',sysdate);
  else if deleting then
    insert into t_book_log values(user,'delete',sysdate);
  end if;
  end if;
  end if;
end;

create trigger tr_book_add
after insert
on t_book
for each row
begin
  update t_booktype set num=num+1 where id=:new.typeId;
end;

create trigger tr_book_delete
after delete
on t_book
for each row
begin
  update t_booktype set num=num-1 where id=:old.typeId;
end;


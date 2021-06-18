create table t_news (
  id varchar2(255) primary key not null,
  address varchar2(255) ,
  create_time timestamp(6) ,
  description varchar2(255) ,
  news_time timestamp(6) ,
  title varchar2(255) ,
  user_id varchar2(255) 
) ;
--  省市区域字符串连接拼接测试
create  table    t_tree(id  varchar(3)  ,  pid  varchar(3)  ,  name  varchar(10));
insert  into    t_tree  values('002'  ,  0  ,  '浙江省');
insert  into    t_tree  values('001'  ,  0  ,  '广东省');
insert  into    t_tree  values('003'  ,  '002'  ,  '衢州市');
insert  into    t_tree  values('004'  ,  '002'  ,  '杭州市')  ;
insert  into    t_tree  values('005'  ,  '002'  ,  '湖州市');
insert  into    t_tree  values('006'  ,  '002'  ,  '嘉兴市')  ;
insert  into    t_tree  values('007'  ,  '002'  ,  '宁波市');
insert  into    t_tree  values('008'  ,  '002'  ,  '绍兴市')  ;
insert  into    t_tree  values('009'  ,  '002'  ,  '台州市');
insert  into    t_tree  values('010'  ,  '002'  ,  '温州市')  ;
insert  into    t_tree  values('011'  ,  '002'  ,  '丽水市');
insert  into    t_tree  values('012'  ,  '002'  ,  '金华市')  ;
insert  into    t_tree  values('013'  ,  '002'  ,  '舟山市');
insert  into    t_tree  values('014'  ,  '004'  ,  '上城区')  ;
insert  into    t_tree  values('015'  ,  '004'  ,  '下城区');
insert  into    t_tree  values('016'  ,  '004'  ,  '拱墅区')  ;
insert  into    t_tree  values('017'  ,  '004'  ,  '余杭区')  ;
insert  into    t_tree  values('018'  ,  '011'  ,  '金东区')  ;
insert  into    t_tree  values('019'  ,  '001'  ,  '广州市')  ;
insert  into    t_tree  values('020'  ,  '001'  ,  '深圳市')  ;


with  RECURSIVE  cte  as
(
    select  a.id,cast(a.name  as  varchar(100))  from    t_tree  a  where  id='002'
    union  all  
    select  k.id,cast(c.name||'>'||k.name  as  varchar(100))  as  name    from    t_tree  k  inner  join  cte  c  on  c.id  =  k.pid
)select  id,name  from  cte  ;
-----------------------------------
SQL技巧：WITH RECURSIVE递归运算
https://blog.51cto.com/u_15101584/2623141
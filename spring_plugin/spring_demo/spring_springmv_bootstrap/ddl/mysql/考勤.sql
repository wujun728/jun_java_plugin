select * from t_import_excel;
select * from t_import_excel where is_modify in (1,2,3);
select
	user_name,
	date_format(start_time, "%Y-%m-%d %T") start_time,
	date_format(end_time, "%Y-%m-%d %T") end_time,
	(case remark
			when '工作日' then (hour(timediff(end_time,start_time))-8)
					else hour(timediff(end_time,start_time))
			end
	) diff_hour,
	remark,
  year,
  is_modify
from t_import_excel
where year='7月'
order by start_time asc,end_time asc;

select * from t_import_excel where start_date is null or is_modify=1;
update t_import_excel set 
start_date=end_date , start_time=str_to_date(concat(end_date,' 09:00:00'),"%Y-%m-%d %T"),is_modify=1
where start_date is null;

select * from t_import_excel where (end_date is null or is_modify=2) and date_format(start_time,"%T")='00:00:00';
update t_import_excel set 
end_date=start_date , end_time=str_to_date(concat(start_date,' 00:00:00'),"%Y-%m-%d %T"),is_modify=2
where (end_date is null or is_modify=2) and date_format(start_time,"%T")='00:00:00';

select * from t_import_excel where (end_date is null or is_modify=3) and date_format(start_time,"%T")!='00:00:00';
update t_import_excel set 
end_date=start_date , end_time=str_to_date(concat(start_date,' 18:00:00'),"%Y-%m-%d %T"),is_modify=3
where (end_date is null or is_modify=3) and date_format(start_time,"%T")!='00:00:00';
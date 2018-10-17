select * from (
	select 
	p.id as post,
	p.name as postName,
	p.update_by as updateBy,
	p.create_by as createBy,
	p.update_time as updateTime,
	p.create_time as createTime  
	from t_position p  
 ) a 
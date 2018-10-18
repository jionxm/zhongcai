select * from (
	select 
	g.id as id,
	g.name as name,
	g.update_by as updateBy,
	g.create_by as createBy,
	g.update_time as updateTime,
	g.create_time as createTime  
	from t_group g 
 ) a 
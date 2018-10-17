select * from (
	select 
	t.id as id,
	t.name as name,
	t.dimension as dimension,
	t.update_by as updateBy,
	t.create_by as createBy,
	t.update_time as updateTime,
	t.create_time as createTime  
	
	from t_tester t 
 ) a 
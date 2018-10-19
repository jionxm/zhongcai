select * from (
	select 
	g.id as id,
	g.name as name,
	t.name as createBy,
	t2.name as updateBy,
	g.update_time as updateTime,
	g.create_time as createTime  
	from t_group g 
	LEFT JOIN t_employee t ON t.id=g.create_by 
	LEFT JOIN t_employee t2 ON t2.id=g.update_by 
 ) a 
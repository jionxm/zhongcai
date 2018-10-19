select * from (
	select 
	c.id as id,
	c.name as name,
	c.choose_id as chooseId,
	c.score as score,
	t.name as createBy,	
	c.create_time as createTime,
	t2.name as updateBy,
	c.update_time as updateTime
	
	from t_choose_value c 
	LEFT JOIN t_employee t ON t.id=c.create_by 
	LEFT JOIN t_employee t2 ON t2.id=c.update_by 
 ) a 


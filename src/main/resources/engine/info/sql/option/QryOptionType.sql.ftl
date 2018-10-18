select * from (
	select 
	c.id as id,
	c.name as name,
	c.create_by as createBy,	
	c.create_time as createTime,
	c.update_by as updateBy,
	c.update_time as updateTime
	
	from t_choose c 
 ) a 
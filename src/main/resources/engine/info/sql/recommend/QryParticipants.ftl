select * from (
	select 
	p.id as id,
	p.recommend_id as recommendId,
	p.emp_id as empId,	
	p.plan_date as planDate,
	p.update_by as updateBy,
	p.create_by as createBy,
	p.update_time as updateTime,
	p.create_time as createTime  
	from t_participants p  
	LEFT JOIN t_recommend r ON p.recommendId=r.id
  	LEFT JOIN t_employee e ON p.id=e.id
 ) a 
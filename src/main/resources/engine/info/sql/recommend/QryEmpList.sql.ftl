select * from (
	select 
	r.id as id,
	r.recommend_id as recommendId,
	r.emp_id as empId,
	e.name as empName,
	e.position_id as positionId,
	e.org_id as orgId,
	p.name as postName,
	o.name as orgName,
	r.update_by as updateBy,
	r.create_by as createBy,
	r.update_time as updateTime,
	r.create_time as createTime
	
	from t_participants r  
	LEFT JOIN t_employee e ON r.emp_id=e.id
	LEFT JOIN t_position p ON e.position_id=p.id
	LEFT JOIN t_org o ON e.org_id=o.id
 ) a 
select * from (
	select 
	g.id as id,
	g.group_id as groupId,
	g.emp_id as empId,
	e.name as empName,
	e.position_id as positionId,
	e.org_id as orgId,
	p.name as postName,
	o.name as orgName
	
	from t_group_emp g  
	LEFT JOIN t_employee e ON g.emp_id=e.id
	LEFT JOIN t_position p ON e.position_id=p.id
	LEFT JOIN t_org o ON e.org_id=o.id
	where g.group_id = #{data.ctlId }
 ) a 
SELECT
	g.id as id,
	g.group_id as groupId,
	g.emp_id as empId,
	e.name as empName,
	e.org_id as orgId,
	o.name as orgName
	
From t_group_emp g  
	LEFT JOIN t_employee e ON g.emp_id=e.id
	LEFT JOIN t_org o ON e.org_id=o.id
	where g.group_id = #{data.ztqzId }

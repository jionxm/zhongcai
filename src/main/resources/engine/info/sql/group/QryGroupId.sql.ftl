select * from (
	select 
	g.id as id,
	g.group_id as groupId,
	g.emp_id as empId,
	e.name as empName
	
	from t_group_emp g  
	LEFT JOIN t_employee e ON g.emp_id=e.id
	where g.id = #{data.id }
 ) a 
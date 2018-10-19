select * from (
	select 
	g.id as id,
	g.emp_id as empId,
	g.group_id as groupId 
	from t_group_emp e 
 ) a 
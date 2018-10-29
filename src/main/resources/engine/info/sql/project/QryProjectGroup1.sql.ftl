select * from (
	select 
	p.id as id,
	p.group_id as groupId,
	g.name as groupName,	
	p.test_id as testId,
	t.title as testName,
	p.create_by as createBy,
	e1.name as createByName,
	p.update_by as updateBy,
	e2.name as updateByName,
	p.create_time as createTimeName,
	p.update_time as updateTimeName
	from t_project_group p
	LEFT JOIN t_group g ON g.id=p.group_id
	LEFT JOIN t_test t ON t.id=p.test_id
	left join t_employee e1 on e1.id = p.create_by
	left join t_employee e2 on e2.id = p.update_by
 ) a 
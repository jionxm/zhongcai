select * from (
	select 	
	p.id as id,
	p.project_id as projectId,
	p1.type as type,
	p1.name as projectName,
	p.create_by as createBy,
	e1.name as createByName,
	p.update_by as updateBy,
	e2.name as updateByName,
	p.create_time as createTimeName,
	p.update_time as updateTimeName
	from t_project_group p
	left join t_project p1 on p1.id = p.project_id
	left join t_employee e1 on e1.id = p.create_by
	left join t_employee e2 on e2.id = p.update_by
 ) a 
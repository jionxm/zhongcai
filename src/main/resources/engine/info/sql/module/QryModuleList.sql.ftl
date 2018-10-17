select * from (
	select 
	s.id as id,
	s.name as name,
	s.proj_id as projId,
	s.remark as remark,
	p.name as projName,
	s.create_time as createTime,
	s.create_by as createBy,
	s.update_time as updateTime,
	s.update_by as updateBy,
	e.name as createByName,
	e1.name as updateByName
	from t_module s
	left join t_project p on p.id=s.proj_id 
	left join t_employee e on e.id = s.create_by
	left join t_employee e1 on e1.id = s.update_by
 ) a   
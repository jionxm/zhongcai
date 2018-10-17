select * from (
	select 
	s.id as id,
	s.name as name,
	s.proj_id as projId,
	p.name as projName,
	s.start_time as startTime,
	s.end_time as endTime,
  s.test_start_time as testStartTime,
	s.test_end_time as testEndTime,
	s.status as status,
	d.name as statusName,
	s.create_time as createTime,
	s.create_by as createBy,
	e.name as createByName,
	s.aim as aim
	from t_sprint s
	left join t_project p on p.id=s.proj_id 
	left join t_dict d on d.code=s.status and d.cata_code='t_sprint.status' 
	left join t_employee e on e.id = s.create_by
 ) a 
select * from (
	select 
	b.id as id,
	b.proj_id as projId,
	p.name as projName,
	b.sprint_id as sprintId,
	s.name as sprintName,
	b.sha1 as sha1,
	b.version as version,
	b.branch as branch,
	b.storage_location as strgLocation,
	b.type as type,
	d.name as typeName, 
	b.commit_time as commitTime,
	b.commitor as commitor,
	e.name as commitorName,
	b.remark as remark
	
	from t_baseline b
	left join t_project p on p.id=b.proj_id 
	left join t_dict d on d.code=b.type and d.cata_code='t_baseline.type' 
	left join t_employee e on e.id = b.commitor
	left join t_sprint s on s.id = b.sprint_id
	left join t_commit_log c on c.sha1 = b.sha1
	
	order by c.date desc
 ) a 
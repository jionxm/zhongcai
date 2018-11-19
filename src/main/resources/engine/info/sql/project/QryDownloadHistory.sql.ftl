select * from (
	select 
		s.id as id,
		s.project_id as projectId,
		s.type as type,
		s.path as path,	
		s.create_by as createBy,
		s.create_time as createTime,
		d.name as typeName,
		p.name as projectName  
	from t_statistics_record s
		LEFT JOIN t_project p ON p.id = s.project_id
		LEFT JOIN t_dict d ON d.code = s.type and d.cata_code="t_test.type"
 ) a 
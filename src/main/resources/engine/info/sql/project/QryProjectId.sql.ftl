select * from (
	select 	
	p.id as id,
	p.project_id as projectId
	from t_project_group p
 ) a 
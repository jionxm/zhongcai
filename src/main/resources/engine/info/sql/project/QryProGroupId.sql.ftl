select * from (
	select 
	p.id
	from t_project_group p
	where p.project_id=#{data.projectId}
 ) a 
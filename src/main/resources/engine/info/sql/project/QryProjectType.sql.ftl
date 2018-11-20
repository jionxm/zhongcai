select * from (
	select 	
	p.id as projectId,
	p.type as type
	from t_project p
	where id = #{data.ctlProjectId }
 ) a 
select * from (
	select 
		id as projectId,
		name as projectName
	from t_project
 ) a 
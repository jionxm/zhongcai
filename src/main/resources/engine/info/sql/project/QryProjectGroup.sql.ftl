select * from (
	select 
	p.id as id,
	g.name as groupName
	
	from t_project_group p
	LEFT JOIN t_group g ON g.id=p.group_id
 ) a 
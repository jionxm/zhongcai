select * from (
	select 
	g.id as id,
	g.group_id as groupid,	
	g.test_id as testid,
	d.name as name,
	t.dimension as dimension,
	n.number as number,
	g.project_id as projectid
	from t_project_group g
	left join t_tester t on t.test_id=g.test_id
	left join t_dict d on d.code=t.name
	left join t_tester_number n on n.dimension=t.id
 ) a 


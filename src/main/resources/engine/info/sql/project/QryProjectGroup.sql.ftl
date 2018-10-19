select * from (
	select 
	p.id as id,
	p.group_id as groupId,
	g.name as groupName,	
	p.test_id as testId,
	t.title as testName,
	p.state as state
	from t_project_group p
	LEFT JOIN t_group g ON g.id=p.group_id
	LEFT JOIN t_test t ON t.id=p.test_id
	where p.project_id = #{data.ctlId}
 ) a 
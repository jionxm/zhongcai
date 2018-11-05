select * from (
	select 
	group_id as value,
	g.name as text 
	from t_project_group p
	Left Join t_group g on p.group_id=g.id
	where p.test_id=#{data.eq_testid}
 ) a 
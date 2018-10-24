select * from (
	select 
	t.id
	from t_tester t
	where t.test_id=(select test_id from t_project_group where project_id = #{data.projectId})
 ) a 
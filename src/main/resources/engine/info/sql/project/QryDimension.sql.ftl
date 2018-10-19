select * from (
	select 
		g.id as proGroupId,
		t.id as dimension,
		t.name as dimensionName
	from t_test ts 
		LEFT JOIN t_tester t ON t.test_id = ts.id
		LEFT JOIN t_project_group g ON g.test_id = ts.id
		where g.id = #{data.ctlProGroupId }
 ) a 
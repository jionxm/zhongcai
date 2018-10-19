select * from (
	select 
		g.id as proGroupId,
		t.id as dimension,
		td.name as dimensionName
	from t_test ts 
		LEFT JOIN t_tester t ON t.test_id = ts.id
		LEFT JOIN t_project_group g ON g.test_id = ts.id
		LEFT JOIN t_dict td ON td.code = t.name and td.cata_code="t_employee.identity"
		where g.id = #{data.ctlProGroupId }
 ) a 
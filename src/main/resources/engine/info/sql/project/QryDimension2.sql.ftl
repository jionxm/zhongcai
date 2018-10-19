select * from (
	select 
		n.id as id,
		n.number as number,
		g.id as proGroupId,
		t.id as dimension,
		t.name as dimensionName
	from t_tester_number  n
		LEFT JOIN t_project_group g ON g.id = n.pro_group_id
		LEFT JOIN t_tester t ON t.id = n.dimension
		where g.id = #{data.ctlProGroupId }
 ) a 
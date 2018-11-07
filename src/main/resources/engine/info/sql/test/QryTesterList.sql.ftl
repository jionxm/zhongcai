select * from (
	select 
	t.id as id,
	t.test_id as testId,
	t1.state as testState,
	d.name as name,
	t.dimension as dimension,
	t.update_by as updateBy,
	t.create_by as createBy,
	t.update_time as updateTime,
	t.create_time as createTime  
	
	from t_tester t 
	LEFT JOIN t_dict d ON d.code=t.name
	LEFT JOIN t_test t1 ON t1.id=t.test_id
	where t.test_id = #{data.ctlId}
 ) a 
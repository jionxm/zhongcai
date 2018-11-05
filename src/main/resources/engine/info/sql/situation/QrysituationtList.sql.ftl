select * from (
	select 
	t.id as id,
	t.name as name,
	t.test_id as testid,
	t.dimension as dimension,
	n.number as number,
	n.pro_group_id as groupid
	from t_tester t
	left join t_tester_number n on t.id=n.dimension
	
 ) a 
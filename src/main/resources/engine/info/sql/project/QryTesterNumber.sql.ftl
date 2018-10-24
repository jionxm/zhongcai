select * from (
	select 
	n.number
	from t_tester_number n
	where n.dimension = #{data.testerId} and n.pro_group_id = #{data.progroupId}
 ) a 
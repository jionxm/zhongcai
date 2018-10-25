select * from (
	select 
	id as id,
	title as title 
	from t_test
	where state="start" and group_id = #{data.id}
	union 
	select 
	id as id,
	title as title 
	from t_test
	where state="start" and group_id = 0
 ) a 
select * from (
	select 
	id as id,
	title as title 
	from t_test
	where state="start" and group_id = #{model.ctlGroup} and type = #{model.ctlType }
	union 
	select 
	id as id,
	title as title 
	from t_test
	where state="start" and group_id = 0 and type = #{model.ctlType }
 ) a 
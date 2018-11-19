select * from (
	select 
	name
	from t_project
	where id = #{data.projId }
 ) a 
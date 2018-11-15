select * from (
	select
	count(emp_id) as allnumber
	from t_group_emp
	where group_id in (select group_id from t_project_group where project_id = #{data.projId })
 ) a 
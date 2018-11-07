select * from (
	select
	q.question as question
	from t_project_group p
	left join t_questions q on q.test_id = p.test_id
	where p.project_id = #{data.projId}
 ) a 
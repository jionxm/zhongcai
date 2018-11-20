select * from (
	select
	p1.name as groupName,
	q.question as question
	from t_project_group p
	left join t_questions q on q.test_id = p.test_id
	left join t_group p1 on p1.id = p.group_id
	where p.project_id = #{data.projId}
 ) a 
select * from (
	select
		q.question as question,
		q.dimension as dimension,
		g.project_id
		from t_test t
		LEFT JOIN t_questions q on q.test_id = t.id
		LEFT JOIN  t_project_group g on g.test_id = t.id
	where g.project_id = 109
 ) a 
select * from (
	select
	GROUP_CONCAT(DISTINCT
    CONCAT(
      'MAX(CASE q.question when ''',
      q.question ,
      ''' THEN t.result ELSE 0 END)  ''',
      q.question, ''''
    )
  ) as questionsql
	from t_project_group p
	left join t_questions q on q.test_id = p.test_id
	where p.project_id = #{data.projId}
 ) a 
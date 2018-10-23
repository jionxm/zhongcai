select * from (
	select 
		t.number as questionNumber,
		t.question as questionName,
		q.id as QRId
	from t_questions t
	
		LEFT JOIN t_project_group g ON g.test_id = t.test_id
		LEFT JOIN t_QR q ON q.proj_group_id = g.id
		where q.tester_id =#{data.ctlTesterId} and q.project_id =#{data.ctlProjectId} and q.proj_group_id =#{data.ctlProjGroupId}
 ) a 
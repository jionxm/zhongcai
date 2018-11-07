select DISTINCT * from (
		SELECT 
			q.id as questionId,
			ta.ztqz_id as ztqzId,
			g.group_id as groupId,
			g.test_id as testId,
			g.project_id as projectId,
			g.id as projectGroupId,
			ge.emp_id as empId,
			e.name as empName,
			qr.id as QRId,	
			t.type			
		from t_questions q
			LEFT JOIN t_question_authorize ta on ta.question_id = q.id
			LEFT JOIN t_project_group g on g.test_id = q.test_id
			LEFT JOIN t_group_emp ge on ge.group_id = g.group_id
			LEFT JOIN t_employee e on ge.emp_id = e.id
			LEFT JOIN t_qr qr on g.project_id = qr.project_id
			LEFT JOIN t_test t on g.test_id = t.id
) b
		where b.projectId=#{data.ctlProjectId } and b.testId = #{data.ctlTestId } and b.ztqzId=#{data.ctlTesterId } and b.QRId=#{data.ctlId } and b.projectGroupId=#{data.ctlProjGroupId }

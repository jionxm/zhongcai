select * from (
	SELECT
		b.questionNumber,
		b.questionName,
		qr.id as QRId,
		b.testId,
		b.type as testType,
		b.projectId,
		b.ztqzId,
		b.projectGroupId,
		g.group_id,
		ge.emp_id as empId,
		e.name as empName
	from (
		SELECT
			g.test_id as testId,
			g.project_id as projectId,
			g.id as projectGroupId,
			ta.ztqz_id as ztqzId,
			t.type,
			q.number as questionNumber,
			q.question as questionName
		from t_questions q
			LEFT JOIN t_project_group g on g.test_id = q.test_id
			LEFT JOIN t_question_authorize ta on ta.question_id = q.id
			LEFT JOIN t_test t on g.test_id = t.id
) b
		LEFT JOIN t_qr qr on b.projectId = qr.project_id
		LEFT JOIN t_project_group g on g.id = b.projectGroupId
		LEFT JOIN t_group_emp ge on ge.group_id = g.group_id
		LEFT JOIN t_employee e on e.id = ge.emp_id
		where b.projectId=#{data.ctlProjectId } and b.testId = #{data.ctlTestId } and b.ztqzId=#{data.ctlTesterId } and qr.id=#{data.ctlId } and b.projectGroupId=#{data.ctlProjGroupId }
 ) a 
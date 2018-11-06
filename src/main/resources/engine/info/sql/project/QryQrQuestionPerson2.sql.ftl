select DISTINCT * from (
		SELECT 
			q.id as questionId,
			q.number as questionNumber,
			q.question as questionName,
			q.dimension as dimension,
			q.type as chooseType,			
			q.test_id as testId,
			ta.ztqz_id as ztqzId,
			g.project_id as projectId,
			g.id as projectGroupId,
			qr.id as QRId,	
			t.type			
		from t_questions q
			LEFT JOIN t_question_authorize ta on ta.question_id = q.id
			LEFT JOIN t_project_group g on g.test_id = q.test_id
			LEFT JOIN t_qr qr on g.project_id = qr.project_id
			LEFT JOIN t_test t on g.test_id = t.id
) b
		where b.projectId=#{data.ctlProjectId } and b.testId = #{data.ctlTestId } and b.ztqzId=#{data.ctlTesterId } and b.QRId=#{data.ctlId } and b.projectGroupId=#{data.ctlProjGroupId }
